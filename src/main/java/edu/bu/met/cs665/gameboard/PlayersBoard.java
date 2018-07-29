package edu.bu.met.cs665.gameboard;

import java.awt.*;
import java.util.Random;

public class PlayersBoard implements Board {

    public PlayerBoardGridItem[][] getBoard() {
        return board;
    }

    PlayerBoardGridItem[][] board = new PlayerBoardGridItem[5][5]; //base is 5x5;

    @Override
    public void setupBoard(int gridSize) {
        board = new PlayerBoardGridItem[gridSize][gridSize];
        //create all the new objects for the board;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new PlayerBoardGridItem();
            }
        }
    }

    @Override
    public String displayBoard() {
        StringBuilder outputBuilder = new StringBuilder();
        //build out the outline for the board
        //add top left corner
        outputBuilder.append("/");
        //add top bar
        for (int i = 0; i < board.length - 1; i++) {
            outputBuilder.append("==");
        }
        //add upper right corner
        outputBuilder.append("=\\\n");


        for (int i = 0; i < board.length; i++) {
            //add left bar
            outputBuilder.append("|");
            for (int j = 0; j < board[i].length; j++) {
                //add the grid item
                outputBuilder.append(board[i][j].toString());
                //add the separator bar
                outputBuilder.append("|");

            }
            //add carriage return so we can start next row
            outputBuilder.append("\n");
//            if (i < board.length - 1){
//            //add left intersector
//            outputBuilder.append("|");
//            //add middle splitter
//
//            for (int k = 0; k < board.length - 1; k++) {
//                outputBuilder.append("__");
//            }
//            //add middle right side piece
//            outputBuilder.append("_|\n");
//            }

        }
        //add bottom left corner
        outputBuilder.append("\\");
        //add bottom bar
        for (int i = 0; i < board.length - 1; i++) {
            outputBuilder.append("==");
        }
        //add bottom right corner
        outputBuilder.append("=/\n");

        return outputBuilder.toString();
    }

    @Override
    public void addRandomBoat(int boatSize) {
        Random rnd = new Random();
        boolean isHoriztonal = rnd.nextBoolean();
        Point randomPlacement = new Point(0,0);

        while (true) {
            //find a random position taking into account boat size
            if(isHoriztonal) randomPlacement = new Point(rnd.nextInt(this.board.length+1 - boatSize),rnd.nextInt(this.board.length));
            else randomPlacement = new Point(rnd.nextInt(this.board.length),rnd.nextInt(this.board.length+1 - boatSize));
            //if the boat fits place it and exit the loop, otherwise wrap around and try again.
            if(checkPosition(randomPlacement,boatSize,isHoriztonal))
            {addPlacedBoat(randomPlacement,boatSize,isHoriztonal);
            break;}
        }
    }

    @Override
    public void addPlacedBoat(Point startingPosition, int size, boolean isHorizontal) throws IllegalArgumentException {
        if (!checkPosition(startingPosition, size, isHorizontal))
            throw new IllegalArgumentException("Can't add to this location");
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                board[startingPosition.x][startingPosition.y + i].setHasBoat(true);
            }
        }
        else{
            for (int i = 0; i < size; i++) {
                board[startingPosition.x+i][startingPosition.y].setHasBoat(true);
            }
        }
    }

    @Override
    public boolean checkPosition(Point startingPosition, int size, boolean isHorizontal) {

        //if we get an index out of bounds exception it can't fit
        try {
            //boats are either horizontal or vertical, vertical boats have the start point at the top
            //horizontal boats have the start point in the left
            if (isHorizontal) {
                for (int i = 0; i < size; i++) {
                    //check if there is already a boat in this position
                    if (board[startingPosition.x][startingPosition.y + i].isHasBoat()) return false;
                }

            } else {
                for (int i = 0; i < size; i++) {
                    if (board[startingPosition.x + i][startingPosition.y].isHasBoat()) return false;
                }
            }


        }
        //if we are out of bounds on the array we obviously can't fit
        catch (IndexOutOfBoundsException ex) {
            return false;
        }


        return true;
    }
}
