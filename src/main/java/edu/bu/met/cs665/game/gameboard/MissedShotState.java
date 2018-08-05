package edu.bu.met.cs665.game.gameboard;

//State to display a shot has been missed
public class MissedShotState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.missedShot;
    }
}
