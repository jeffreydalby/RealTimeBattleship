package edu.bu.met.cs665.server;

import edu.bu.met.cs665.game.Game;
import edu.bu.met.cs665.game.Player;
import edu.bu.met.cs665.game.gameboard.PlayersBoard;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class PlayerConnection {

    Player thisPlayer;

    SelectionKey selectionKey;
    SocketChannel chan;
    ByteBuffer buf;
    String msg = "";
    Game game = Game.getInstance();

    PlayerConnection(SelectionKey selectionKey, SocketChannel chan, Player player) throws IOException {

        if (player == null) disconnect(true);

        this.thisPlayer = player;
        this.selectionKey = selectionKey;
        this.chan = (SocketChannel) chan.configureBlocking(false); // asynchronous/non-blocking
        buf = ByteBuffer.allocate(1024); // 64 byte capacity
    }

    void disconnect(boolean gameFull) {
        GameServer.clientMap.remove(selectionKey);
        try {
            if (selectionKey != null)
                selectionKey.cancel();

            if (chan == null)
                return;

            System.out.println("Disconnected " + (InetSocketAddress) chan.getRemoteAddress());
            if (gameFull)
                chan.write(ByteBuffer.wrap("Sorry this game is full".getBytes("UTF-8")));
            chan.close();
        } catch (IOException t) { /** quietly ignore  */}
    }

    void read() {
        try {
            ///because this loop can start whenever we need to catch this here as well
            if (thisPlayer == null) disconnect(true);
            if (((PlayersBoard)thisPlayer.getMyBoard()).isGameOver())
            {
                chan.write(ByteBuffer.wrap(thisPlayer.showBoard().getBytes("UTF-8")));
                chan.write(ByteBuffer.wrap(("You loose!!!\r\n").getBytes("UTF-8")));
                disconnect(false);
            }

            int code = 0;
            try {
                while ((code = chan.read(buf)) > 0) {
                    byte b[] = new byte[buf.position()];
                    buf.flip();
                    buf.get(b);

                    msg += new String(b, "UTF-8");
                }
                if (code == -1) {
                    disconnect(false);
                } else
                    buf.clear();

            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
            //we listened until we got a carriage return  we have to do it this
            if (msg.contains("\n")) {
                Point shot;
                msg = msg.replaceAll("\\r|\\n", "");
                String[] input = msg.split(",");
                try {
                    if (!game.getPlayer2().isAssigned())
                        chan.write(ByteBuffer.wrap(("Can't take shot unless both players have joined\r\n").getBytes("UTF-8")));
                    else {
                        shot = new Point(Integer.decode(input[0]), Integer.decode(input[1]));
                        chan.write(ByteBuffer.wrap(("Taking a shot at " + "(" + input[0] + "," + input[1] + ")\r\n").getBytes("UTF-8")));
                        if (thisPlayer.takeShot(shot))
                            chan.write(ByteBuffer.wrap(("Hit!!!\r\n").getBytes("UTF-8")));
                        else
                            chan.write(ByteBuffer.wrap(("Miss\r\n").getBytes("UTF-8")));
                        chan.write(ByteBuffer.wrap(thisPlayer.showBoard().getBytes("UTF-8")));
                        if (((PlayersBoard) thisPlayer.getOpponentsBoard()).isGameOver()) {
                            chan.write(ByteBuffer.wrap(("You win!!!\r\n").getBytes("UTF-8")));
                            disconnect(false);
                        } else
                            chan.write(ByteBuffer.wrap(("Take your shot!\r\n").getBytes("UTF-8")));
                    }
                    msg = "";
                    buf.clear();
                } catch (Exception ex) {
                    chan.write(ByteBuffer.wrap(("Please enter the location in the format of X,Y...for example 4,8\r\n").getBytes("UTF-8")));
                    msg = "";
                    buf.clear();
                }


                //chan.write(ByteBuffer.wrap(thisPlayer.showBoard().getBytes("UTF-8")))
            }


//            int amount_read = -1;
//            //keep reading until we get a carriage return
//            while (true) {
//                try {
//                    amount_read = chan.read((CharBuffer) buf.clear());
//                }
//                catch (Throwable t) {
//                }
//
//                if (amount_read == -1) {
//                    disconnect(false);
//                    break;
//                }
//
//                if (amount_read < 1) {
//                    return; // if zero
//                }
//                buf.flip();
//
//                System.out.println("Buffer is:" + buf.toString());
//                if(buf.asCharBuffer().toString().contains("\n")){
//                    System.out.println("Found it!");
//                    break;
//                }
//                if (new String(buf.array()).contains("\n")){
//                    System.out.println("Found a carriage return");
//                    break;
//                }

            //}
            //buf.flip();
            //System.out.println("sending back " + buf.position() + " bytes");
            // chan.write(ByteBuffer.wrap(thisPlayer.showBoard().getBytes("UTF-8")));


        } catch (Throwable t) {
            disconnect(false);
            t.printStackTrace();
        }
    }
}
