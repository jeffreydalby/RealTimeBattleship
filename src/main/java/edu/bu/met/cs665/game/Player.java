package edu.bu.met.cs665.game;

import edu.bu.met.cs665.game.gameboard.Board;

import java.awt.*;

public class Player {
    public String getName() {
        return name;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public Board getOpponentsBoard() {
        return opponentsBoard;
    }

    private String name;

    public Board getMyBoard() {
        return myBoard;
    }

    private Board myBoard;
    private Board opponentsBoard;
    private boolean assigned;



    public Player(String name, Board myBoard, Board opponentsBoard){
        this.name = name;
        this.myBoard = myBoard;
        this.opponentsBoard = opponentsBoard;
    }

    /**
     * We want to show our board which has its boats and then other players shot,
     *      * and the opponents display board which won't show boat position but will show
     *      * our hits and misses.
     * @return
     */
    public String showBoard(){
        return name +"\r\n" + myBoard.displayMyBoard() + "\r\n" + opponentsBoard.displayBoard();
    }

    /**
     * Player always takes a show against the opponents board
     * @param shot - point on the board to shot
     * @return - did it hit or not?
     */
    public boolean takeShot(Point shot){
        return opponentsBoard.takeShot(shot);
    }
}
