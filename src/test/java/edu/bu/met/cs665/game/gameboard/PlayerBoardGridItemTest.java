package edu.bu.met.cs665.game.gameboard;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerBoardGridItemTest {

    @Test
    public void takeShot() {
        PlayerBoardGridItem testGridItem = new PlayerBoardGridItem();
        testGridItem.setHasBoat(true);
        Assert.assertTrue(testGridItem.takeShot());
    }
}