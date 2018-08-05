package edu.bu.met.cs665.server;

import edu.bu.met.cs665.game.Game;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Primay Game server
public class GameServer {

    private ServerSocketChannel serverChannel; //Server Socket Channel to host game
    private Selector selector; //Selector
    private SelectionKey serverKey; //Key for this server
    private Game game; //instance of the game
    //keep track of player connections
    private boolean player1Started; //keep track of player connections
    //String to help us identify Player 1 session
    private String player1Name;

    private static final int GAME_LOOP_SPEED = 500; //how often to check in milliseconds for new data and act on it

    /**
     * Server for out telnet based game
     *
     * @param listenAddress -port to listen on
     * @param game          - instance of the game
     * @throws IOException - exception if we have issues with the network
     */
    public GameServer(InetSocketAddress listenAddress, Game game) throws IOException {
        this.game = game;
        player1Name = game.getPlayer1().getName();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverKey = serverChannel.register(selector = Selector.open(), SelectionKey.OP_ACCEPT);
        serverChannel.bind(listenAddress);
        //start out listener gameLoop
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                gameLoop();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, 0, GAME_LOOP_SPEED, TimeUnit.MILLISECONDS);
    }

    static HashMap<SelectionKey, PlayerConnection> clientMap = new HashMap<>(); //hash map to keep tie players to sessions

    private void gameLoop() throws IOException {
        selector.selectNow(); //check for available sessions
        //Loop through all ready sessions
        for (SelectionKey key : selector.selectedKeys()) {
            try {
                if (!key.isValid())
                    continue;

                if (key == serverKey) {
                    SocketChannel acceptedChannel = serverChannel.accept();

                    if (acceptedChannel == null)
                        continue;

                    acceptedChannel.configureBlocking(false); //have to configure to not block since we are using multiple sessions
                    //we want to be able to both read and write to the session
                    SelectionKey readKey = acceptedChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    //If we don't have a player 1 yet assign this connection to player one
                    if (game.getPlayer1().isUnassigned()) {
                        game.getPlayer1().setAssigned(true);
                        //add to the client map
                        clientMap.put(readKey, new PlayerConnection(readKey, acceptedChannel, game.getPlayer1()));
                        //Show player their board
                        acceptedChannel.write(ByteBuffer.wrap(("Welcome " + game.getPlayer1().getName() + "\r\n" + "Here are your battlegrounds:\r\n").getBytes(StandardCharsets.UTF_8)));
                        acceptedChannel.write(ByteBuffer.wrap(game.getPlayer1().showBoard().getBytes(StandardCharsets.UTF_8)));
                        acceptedChannel.write(ByteBuffer.wrap("Waiting for Player 2.\r\n".getBytes(StandardCharsets.UTF_8))); //can't start the game until player two connects

                    }
                    //assign the next connection to player 2
                    else if (game.getPlayer2().isUnassigned()) {
                        game.getPlayer2().setAssigned(true);
                        //add to the client map
                        clientMap.put(readKey, new PlayerConnection(readKey, acceptedChannel, game.getPlayer2()));
                        //Welcome the player and tell them to take a shot
                        acceptedChannel.write(ByteBuffer.wrap(("Welcome " + game.getPlayer2().getName() + "\r\n" + "Here are your battlegrounds:\r\n").getBytes(StandardCharsets.UTF_8)));
                        acceptedChannel.write(ByteBuffer.wrap(game.getPlayer2().showBoard().getBytes(StandardCharsets.UTF_8)));
                        acceptedChannel.write(ByteBuffer.wrap("Take your shot: (X,Y)\r\n".getBytes(StandardCharsets.UTF_8)));
                    } else
                        //we have both players and will now pass a null value which tells the player connection to display an error and drop
                        clientMap.put(readKey, new PlayerConnection(readKey, acceptedChannel, null));

                    System.out.println("New client ip=" + acceptedChannel.getRemoteAddress() + ", total clients=" + GameServer.clientMap.size());
                }
                //Check if we can write to the channel
                if (key.isWritable()) {

                    PlayerConnection session = clientMap.get(key);
                    if (session == null || !session.chan.isOpen())
                        continue;
                    //check if we are player 1
                    if (session.thisPlayer.getName().equals(player1Name)) {
                        //if we still don't have a player 2 send a "." to indicate we are waiting
                        if (game.getPlayer2().isUnassigned()) {
                            session.chan.write((ByteBuffer.wrap(".".getBytes(StandardCharsets.UTF_8))));
                        }
                        //if player1 hasn't started playing yet but we have a player 2 send message
                        else if (!player1Started) {
                            session.chan.write((ByteBuffer.wrap("Player 2 has joined.\r\nTake your shot(X,Y):\r\n".getBytes(StandardCharsets.UTF_8))));
                            player1Started = true;
                        }
                    }
                    //send notification to loser that the game has ended
                    if (session.thisPlayer.getMyBoard().isGameOver()) {
                        session.chan.write((ByteBuffer.wrap("You Lose!\r\n".getBytes(StandardCharsets.UTF_8))));
                        if (session.chan.isOpen()) session.chan.close(); //close this channel
                        resetGame(); //reset the game so new connections can play another game without restarting the server
                    }

                }
                if (key.isReadable()) {
                    PlayerConnection session = clientMap.get(key);

                    if (session == null)
                        continue;
                    //read the players input and act on it (take a shot)
                    session.read();

                }


            } catch (IOException ex) {
                System.out.println("IO error: " + ex.getLocalizedMessage());
            } catch (CancelledKeyException ex) {
                System.out.println("Session was terminated");  //we get this error when we force the disconnection of the loser, just output to console
            }

        }

        selector.selectedKeys().clear();
    }

    //Reset the game so we can start a new one.
    private void resetGame() {
        player1Started = false;
        game.newStandardRandomGame();
    }

}


