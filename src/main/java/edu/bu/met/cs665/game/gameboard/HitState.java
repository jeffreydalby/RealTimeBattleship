package edu.bu.met.cs665.game.gameboard;

//State showing square has been hit.
public class HitState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.hit;
    }
}
