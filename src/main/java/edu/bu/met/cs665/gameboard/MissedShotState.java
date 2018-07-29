package edu.bu.met.cs665.gameboard;

public class MissedShotState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.missedShot;
    }
}
