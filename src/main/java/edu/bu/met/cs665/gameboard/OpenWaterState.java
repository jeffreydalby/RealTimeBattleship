package edu.bu.met.cs665.gameboard;

public class OpenWaterState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.openWater;
    }
}
