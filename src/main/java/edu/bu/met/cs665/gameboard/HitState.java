package edu.bu.met.cs665.gameboard;

public class HitState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.hit;
    }
}
