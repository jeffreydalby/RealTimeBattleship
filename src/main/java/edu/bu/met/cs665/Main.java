package edu.bu.met.cs665;

import edu.bu.met.cs665.game.Game;
import edu.bu.met.cs665.server.GameServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {


    private static final int GAME_LISTEN_PORT = 5000;
    private static final String GAME_IP_ADDRESS = "localhost";
    private static Game game = Game.getInstance();

    /**
     * A main method to run game
     *
     * @param args not used
     */
    public static void main(String[] args) {
        //Setup a random game
        game.newStandardRandomGame();
        //start the game server
        System.out.println("Starting server on: " + GAME_IP_ADDRESS +":" + GAME_LISTEN_PORT);
        try {
            new GameServer(new InetSocketAddress(GAME_IP_ADDRESS, GAME_LISTEN_PORT), Game.getInstance());
        } catch (IOException ex) {
            System.out.println("IO Exception on server!");
        }

    }

}
