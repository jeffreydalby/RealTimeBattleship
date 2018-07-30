package edu.bu.met.cs665.game.gameboard;

public class OpenWaterState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.openWater;
    }
}
