package edu.bu.met.cs665.game.gameboard;

import java.awt.*;
import java.util.Random;

public class PlayersBoard implements Board {

    public PlayerBoardGridItem[][] getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return gameOver;
    }


    private int countDown = 0;
    private boolean gameOver = false;


    PlayerBoardGridItem[][] board = new PlayerBoardGridItem[5][5]; //base is 5x5;

    /**
     * initialize the board to an square based on gridSize
     *
     * @param gridSize - of length and width of grid
     */
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


    public String displayMyBoard() {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("My board:\r\n");

        //build out the outline for the board
        //add top left corner
        outputBuilder.append(" /");
        //add top bar
        for (int i = 0; i < board.length; i++) {
            outputBuilder.append(i);
            outputBuilder.append("_");
        }
        //remove the extra _
        outputBuilder.deleteCharAt(outputBuilder.length() - 1);
        //add upper right corner
        outputBuilder.append("\\\r\n");


        for (int i = 0; i < board.length; i++) {
            //add left bar
            outputBuilder.append(i);
            outputBuilder.append("|");
            for (int j = 0; j < board[i].length; j++) {
                //add the grid item
                outputBuilder.append(board[i][j].toString());
                //add the separator bar
                outputBuilder.append("|");

            }
            //add carriage return so we can start next row
            outputBuilder.append("\r\n");
        }
        //add bottom left corner
        outputBuilder.append(" \\");
        //add bottom bar
        for (int i = 0; i < board.length - 1; i++) {
            outputBuilder.append("==");
        }
        //add bottom right corner
        outputBuilder.append("=/\r\n");

        return outputBuilder.toString();
    }

    @Override
    public String displayBoard() {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("Shots Taken:\r\n");

        //build out the outline for the board
        //add top left corner
        outputBuilder.append(" /");
        //add top bar
        for (int i = 0; i < board.length; i++) {
            outputBuilder.append(i);
            outputBuilder.append("_");
        }
        //remove the extra _
        outputBuilder.deleteCharAt(outputBuilder.length() - 1);
        //add upper right corner
        outputBuilder.append("\\\r\n");


        for (int i = 0; i < board.length; i++) {
            //add left bar
            outputBuilder.append(i);
            outputBuilder.append("|");
            for (int j = 0; j < board[i].length; j++) {
                //add the grid item
                outputBuilder.append(board[i][j].displayItem.toString());
                //add the separator bar

                outputBuilder.append("|");

            }
            //add carriage return so we can start next row
            outputBuilder.append("\r\n");
        }
        //add bottom left corner
        outputBuilder.append(" \\");
        //add bottom bar
        for (int i = 0; i < board.length - 1; i++) {
            outputBuilder.append("==");
        }
        //add bottom right corner
        outputBuilder.append("=/\r\n");

        return outputBuilder.toString();
    }


    /**
     * Add a random boat of specific size to the players board
     *
     * @param boatSize - size of boat
     */
    public void addRandomBoat(int boatSize) {
        Random rnd = new Random();
        boolean isHoriztonal = rnd.nextBoolean();
        Point randomPlacement = new Point(0, 0);

        while (true) {
            //find a random position taking into account boat size
            if (isHoriztonal)
                randomPlacement = new Point(rnd.nextInt(this.board.length + 1 - boatSize), rnd.nextInt(this.board.length));
            else
                randomPlacement = new Point(rnd.nextInt(this.board.length), rnd.nextInt(this.board.length + 1 - boatSize));
            //if the boat fits place it and exit the loop, otherwise wrap around and try again.
            if (checkPosition(randomPlacement, boatSize, isHoriztonal)) {
                addPlacedBoat(randomPlacement, boatSize, isHoriztonal);
                break;
            }
        }
    }

    /**
     * Add a boat to the board
     *
     * @param startingPosition - grid coordinate for starting point of boat
     * @param size             - size of boat
     * @param isHorizontal     - is the boat horizontal or vertical
     * @throws IllegalArgumentException
     */

    public void addPlacedBoat(Point startingPosition, int size, boolean isHorizontal) throws IllegalArgumentException {
        if (!checkPosition(startingPosition, size, isHorizontal))
            throw new IllegalArgumentException("Can't add to this location");
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                board[startingPosition.x][startingPosition.y + i].setHasBoat(true);
            }
        } else {
            for (int i = 0; i < size; i++) {
                board[startingPosition.x + i][startingPosition.y].setHasBoat(true);
            }
        }
        this.countDown += size;
    }

    /**
     * Verify nothing is in the desire boat position
     *
     * @param startingPosition - starting location for boat
     * @param size             - length of boat
     * @param isHorizontal     - is boat horizontal or vertical
     * @return - boolean true if placement is acceptable
     */

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

    @Override
    public boolean takeShot(Point shot) {
        boolean isHit = this.board[shot.x][shot.y].takeShot();
        if (isHit) this.countDown--;
        if (this.countDown <= 0) gameOver = true;
        return isHit;

    }


}
