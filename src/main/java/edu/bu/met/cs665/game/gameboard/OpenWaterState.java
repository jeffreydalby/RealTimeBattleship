package edu.bu.met.cs665.game.gameboard;

//State to show nothing is in this spot.
public class OpenWaterState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.openWater;
    }
}
