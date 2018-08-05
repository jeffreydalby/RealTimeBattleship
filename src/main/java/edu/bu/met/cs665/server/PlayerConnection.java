package edu.bu.met.cs665.server;

import edu.bu.met.cs665.game.Game;
import edu.bu.met.cs665.game.Player;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

//Players connection to the server
class PlayerConnection {

    Player thisPlayer;

    private SelectionKey selectionKey;
    SocketChannel chan;
    private ByteBuffer buf;
    private String msg = "";
    private Game game = Game.getInstance();

    PlayerConnection(SelectionKey selectionKey, SocketChannel chan, Player player) throws IOException {

        if (player == null) disconnect(true);

        this.thisPlayer = player;
        this.selectionKey = selectionKey;
        this.chan = (SocketChannel) chan.configureBlocking(false); // asynchronous/non-blocking
        buf = ByteBuffer.allocate(1024); // 64 byte capacity
    }

    /**
     * Disconnect the user, display a message if the game is already full (ie. 3rd person attempts to connect)
     *
     * @param gameFull - flag if this is a game full error so we can display alternate message to user
     */
    private void disconnect(boolean gameFull) {
        GameServer.clientMap.remove(selectionKey); //remove from our client map
        try {
            if (selectionKey != null)
                selectionKey.cancel();

            if (chan == null)
                return;
            //output to console that we disconnected
            System.out.println("Disconnected " + chan.getRemoteAddress());
            //let connection know the game is already full
            if (gameFull)
                chan.write(ByteBuffer.wrap("Sorry this game is full".getBytes(StandardCharsets.UTF_8)));
            chan.close();
        } catch (IOException ex) { // quietly ignore
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Read input from the player
     */
    void read() {
        try {
            ///because this gameLoop can start whenever we need to catch this here as well
            if (thisPlayer == null) disconnect(true);
            //if the game is over and we've made it to this portion of the loop disconnect
            //because this only runs when we have data to read, we also have a notification and disconnect in the
            //primary game server which can notify without the read.
            //this handles the situation if the player started typing before they lost
            if (thisPlayer.getMyBoard().isGameOver()) {
                chan.write(ByteBuffer.wrap(thisPlayer.showBoard().getBytes(StandardCharsets.UTF_8)));
                chan.write(ByteBuffer.wrap(("You loose!!!\r\n").getBytes(StandardCharsets.UTF_8)));
                disconnect(false);
            }
            //Stupid windows telnet sends one character at a time so we have to loop through this buffer until we get a carriage return
            //this is the beginning of the loop that read individual characters
            int code;
            try {
                while ((code = chan.read(buf)) > 0) {
                    byte b[] = new byte[buf.position()];
                    buf.flip();
                    buf.get(b);

                    msg += new String(b, StandardCharsets.UTF_8);
                }
                if (code == -1) {
                    disconnect(false);
                } else
                    buf.clear();

            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
            //we listened until we finally got a carriage return
            if (msg.contains("\n")) {
                Point shot;
                msg = msg.replaceAll("\\r|\\n", ""); //filter out the coordinates for the shot
                String[] input = msg.split(",|\\s");
                try {
                    //make sure we have both players
                    if (game.getPlayer2().isUnassigned())
                        chan.write(ByteBuffer.wrap(("Can't take shot unless both players have joined\r\n").getBytes(StandardCharsets.UTF_8)));
                    else {
                        //convert alphanumeric to int and create the point for the shot
                        shot = new Point(Character.toUpperCase(input[0].charAt(0)) - 'A', Integer.decode(input[1]) - 1);
                        chan.write(ByteBuffer.wrap(("Taking a shot at " + "(" + input[1] + "," + input[0] + ")\r\n").getBytes(StandardCharsets.UTF_8)));
                        //take the shot and notify player of the result
                        if (thisPlayer.takeShot(shot))
                            chan.write(ByteBuffer.wrap(("Hit!!!\r\n").getBytes(StandardCharsets.UTF_8)));
                        else
                            chan.write(ByteBuffer.wrap(("Miss\r\n").getBytes(StandardCharsets.UTF_8)));
                        chan.write(ByteBuffer.wrap(thisPlayer.showBoard().getBytes(StandardCharsets.UTF_8)));
                        if ((thisPlayer.getOpponentsBoard()).isGameOver()) {
                            chan.write(ByteBuffer.wrap(("You win!!!\r\n").getBytes(StandardCharsets.UTF_8)));
                            disconnect(false);
                        } else
                            chan.write(ByteBuffer.wrap(("Take your shot!\r\n").getBytes(StandardCharsets.UTF_8)));
                    }
                    //clear things out to ready the game for the next shot
                    msg = "";
                    buf.clear();
                }

                //catch all the ways a person can screw up taking the shot.
                catch (Exception ex) {
                    chan.write(ByteBuffer.wrap(("Please enter the location in the format of X,Y...for example A,8\r\n").getBytes(StandardCharsets.UTF_8)));
                    msg = "";
                    buf.clear();
                }

            }


        } catch (Exception ex) {
            disconnect(false);
            ex.printStackTrace();
        }
    }
}
