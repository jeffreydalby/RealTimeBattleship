package edu.bu.met.cs665;

import edu.bu.met.cs665.game.Game;
import edu.bu.met.cs665.server.GameServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

  /**
   * A main method to run examples. 
   * @param args not used 
   */
  public static void main(String[] args) {
    //Setup a random game
    Game.getInstance().newStandardRandomGame();

    try {new GameServer(new InetSocketAddress("localhost", 5000), Game.getInstance());}
    catch (IOException ex){
      System.out.println("IO Exception on server!");
    }

  }

}
