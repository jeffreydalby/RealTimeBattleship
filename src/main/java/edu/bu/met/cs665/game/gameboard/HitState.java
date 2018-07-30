package edu.bu.met.cs665.game.gameboard;

public class HitState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.hit;
    }
}
