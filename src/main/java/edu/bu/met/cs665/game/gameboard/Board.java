package edu.bu.met.cs665.game.gameboard;

import java.awt.*;

//Interface for our gameboard
public interface Board {

    void setupBoard(int gridSize);  //create the initial board

    String displayShotsTaken(); //return the board to print to screen showing shots taken, not boat location

    String displayMyBoard(); //return players board with ships;

    boolean takeShot(Point shot); //take a shot against this board

    boolean isGameOver(); //check if game is over.
}
