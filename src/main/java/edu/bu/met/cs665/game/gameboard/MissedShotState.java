package edu.bu.met.cs665.game.gameboard;

public class MissedShotState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.missedShot;
    }
}
