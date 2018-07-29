package edu.bu.met.cs665;

import edu.bu.met.cs665.gameboard.PlayersBoard;

public class Main {

  /**
   * A main method to run examples. 
   * @param args not used 
   */
  public static void main(String[] args) {
    PlayersBoard testBoard = new PlayersBoard();
    testBoard.setupBoard(10);
    System.out.println(testBoard.displayBoard());

  }

}
