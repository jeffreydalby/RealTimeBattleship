package edu.bu.met.cs665.gameboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PlayersBoardTest {

    PlayersBoard playersBoard = new PlayersBoard();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void setupBoard() {
        playersBoard.setupBoard(10);
        //board that is setup has the same length
        Assert.assertEquals(10,playersBoard.board.length);
        //board that is setup has the same height
        Assert.assertEquals(10,playersBoard.board[0].length);
    }

    @Test
    public void displayBoard() {
        playersBoard.setupBoard(10);
        System.out.printf(playersBoard.displayBoard());
    }

    @Test
    public void addPlacedBoatHorizontal() {
        playersBoard.setupBoard(10);
        playersBoard.addPlacedBoat(new Point(0,0),4,true);
        System.out.println(playersBoard.displayBoard());
        assertTrue(playersBoard.board[0][3].isHasBoat());

    }

    @Test
    public void addPlacedBoatVertical() {
        playersBoard.setupBoard(10);
        playersBoard.addPlacedBoat(new Point(0,0),4,false);
        System.out.println(playersBoard.displayBoard());
        assertTrue(playersBoard.board[3][0].isHasBoat());

    }

    @Test
    public void addRandomBoat() {
        playersBoard.setupBoard(10);
        playersBoard.addRandomBoat(4);
        playersBoard.addRandomBoat(2);
        playersBoard.addRandomBoat(5);
        playersBoard.addRandomBoat(1);
        //since things are being randomly generated can't automate this.
        System.out.println(playersBoard.displayBoard());
    }

    @Test
    public void checkPositionHoriztonal(){
        playersBoard.setupBoard(10);
        Assert.assertTrue(playersBoard.checkPosition(new Point(0,0),5,true));
    }

    @Test
    public void checkPositionVertical(){
        playersBoard.setupBoard(10);
        Assert.assertTrue(playersBoard.checkPosition(new Point(0,0),5,false));
    }

    @Test
    public void checkPositionOutofBounds(){
        playersBoard.setupBoard(10);
        Assert.assertFalse(playersBoard.checkPosition(new Point(11,0),5,false));
    }
}
