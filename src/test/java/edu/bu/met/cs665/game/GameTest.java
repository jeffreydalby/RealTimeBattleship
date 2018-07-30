package edu.bu.met.cs665.game;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GameTest {

    Game newGame = Game.getInstance();

    @Test
    public void newStandardRandomGame() {
        newGame.newStandardRandomGame();
        newGame.getPlayer1().takeShot(new Point(0,0));

        System.out.println(newGame.getPlayer1().showBoard());
        System.out.println(newGame.getPlayer2().showBoard());

    }

    @Test
    public void newGame() {
    }
}