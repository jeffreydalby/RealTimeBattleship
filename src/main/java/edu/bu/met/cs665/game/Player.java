package edu.bu.met.cs665.game;

import edu.bu.met.cs665.game.gameboard.Board;

import java.awt.*;

//Player object holds a pointer to both own and opponents board so we can display both as needed
public class Player {
    //Player Name
    public String getName() {
        return name;
    }

    //Keep track of if player has been assigned to a socket on the server
    public boolean isUnassigned() {
        return !assigned;
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

    private Board myBoard; //This players board to show boat locations
    private Board opponentsBoard; //Opponents board to show shots taken
    private boolean assigned; //keep track of a socket being assigned to this player


    Player(String name, Board myBoard, Board opponentsBoard) {
        this.name = name;
        this.myBoard = myBoard;
        this.opponentsBoard = opponentsBoard;
    }

    /**
     * We want to show our board which has its boats and then other players shot,
     * and the opponents display board which won't show boat position but will show
     * our hits and misses.
     *
     * @return = string containing both boards
     */
    public String showBoard() {
        return name + "\r\n" + myBoard.displayMyBoard() + "\r\n" + opponentsBoard.displayShotsTaken();
    }

    /**
     * Player always takes a shot against the opponents board
     *
     * @param shot - point on the board to shot
     * @return - did it hit or not?
     */
    public boolean takeShot(Point shot) {
        return opponentsBoard.takeShot(shot);
    }
}
