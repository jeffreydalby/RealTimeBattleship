package edu.bu.met.cs665.gameboard;

import java.awt.*;

public interface Board {

    void setupBoard(int gridSize);  //create the initial board
    String displayBoard(); //return the board to print to screen
    void addRandomBoat(int boatSize);  //add random placed boat of size boatSize to the board
    void addPlacedBoat(Point startingPosition, int size, boolean isVertical) throws IllegalArgumentException; //and specific size, position, and direction boat
    boolean checkPosition(Point startingPosition, int size, boolean isVertical);
    }
