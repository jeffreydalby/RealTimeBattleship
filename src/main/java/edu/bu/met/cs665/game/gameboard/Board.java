package edu.bu.met.cs665.game.gameboard;

import java.awt.*;

public interface Board {

    void setupBoard(int gridSize);  //create the initial board
    String displayBoard(); //return the board to print to screen
    String displayMyBoard(); //return players board with ships;
    boolean takeShot(Point shot);
    }
