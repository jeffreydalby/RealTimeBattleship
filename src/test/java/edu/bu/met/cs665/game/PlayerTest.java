package edu.bu.met.cs665.game;

import edu.bu.met.cs665.game.gameboard.PlayersBoard;
import org.junit.Test;

import java.awt.*;

public class PlayerTest {

    private PlayersBoard myBoard;
    private PlayersBoard opponentsBoard;
    private Player player;
    @Test
    public void showBoard() {
        myBoard = new PlayersBoard();
        myBoard.setupBoard(10);
        myBoard.addRandomBoat(5);
        myBoard.addRandomBoat(4);
        myBoard.addRandomBoat(3);
        myBoard.addRandomBoat(3);
        myBoard.addRandomBoat(2);
        opponentsBoard = new PlayersBoard();
        opponentsBoard.setupBoard(10);
        opponentsBoard.addRandomBoat(5);
        opponentsBoard.addRandomBoat(4);
        opponentsBoard.addRandomBoat(3);
        opponentsBoard.addRandomBoat(3);
        opponentsBoard.addRandomBoat(2);
        opponentsBoard.takeShot(new Point(0,0));

        player = new Player("Player 1", myBoard,opponentsBoard);
        System.out.println(player.showBoard());


    }
}