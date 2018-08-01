package edu.bu.met.cs665.server;

import edu.bu.met.cs665.game.Game;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameServer {

    ServerSocketChannel serverChannel;
    Selector selector;
    SelectionKey serverKey;
    Game game;

    public GameServer(InetSocketAddress listenAddress, Game game) throws IOException {
        this.game = game;
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverKey = serverChannel.register(selector = Selector.open(), SelectionKey.OP_ACCEPT);
        serverChannel.bind(listenAddress);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                loop();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    static HashMap<SelectionKey, PlayerConnection> clientMap = new HashMap<SelectionKey, PlayerConnection>();

    void loop() throws IOException {
        selector.selectNow();

        for (SelectionKey key : selector.selectedKeys()) {
            try {
                if (!key.isValid())
                    continue;

                if (key == serverKey) {
                    SocketChannel acceptedChannel = serverChannel.accept();

                    if (acceptedChannel == null)
                        continue;

                    acceptedChannel.configureBlocking(false);
                    SelectionKey readKey = acceptedChannel.register(selector, SelectionKey.OP_READ);
                    if (!game.getPlayer1().isAssigned()) {
                        game.getPlayer1().setAssigned(true);
                        clientMap.put(readKey, new PlayerConnection(readKey, acceptedChannel, game.getPlayer1()));
                        acceptedChannel.write(ByteBuffer.wrap(("Welcome " + game.getPlayer1().getName() + "\r\n" + "Here are your battlegrounds:\r\n").getBytes("UTF-8")));
                        acceptedChannel.write(ByteBuffer.wrap(game.getPlayer1().showBoard().getBytes("UTF-8")));
                        acceptedChannel.write(ByteBuffer.wrap("Take your shot: (X,Y)\r\n".getBytes("UTF-8")));

                    } else if (!game.getPlayer2().isAssigned()) {
                        game.getPlayer2().setAssigned(true);
                        clientMap.put(readKey, new PlayerConnection(readKey, acceptedChannel, game.getPlayer2()));
                        acceptedChannel.write(ByteBuffer.wrap(("Welcome " + game.getPlayer2().getName() + "\r\n" + "Here are your battlegrounds:\r\n").getBytes("UTF-8")));
                        acceptedChannel.write(ByteBuffer.wrap(game.getPlayer2().showBoard().getBytes("UTF-8")));
                        acceptedChannel.write(ByteBuffer.wrap("Take your shot: (X,Y)\r\n".getBytes("UTF-8")));
                    } else
                        //we have both players and will now pass a null value which tells the player connection to display an error and drop
                        clientMap.put(readKey, new PlayerConnection(readKey, acceptedChannel, null));

                    System.out.println("New client ip=" + acceptedChannel.getRemoteAddress() + ", total clients=" + GameServer.clientMap.size());
                }

                if (key.isReadable()) {
                        PlayerConnection sesh = clientMap.get(key);

                        if (sesh == null)
                            continue;

                        sesh.read();

                }


            } catch (IOException ex) {
                System.out.println("IO error: " + ex.getLocalizedMessage());
            }
        }

        selector.selectedKeys().clear();
    }

}


