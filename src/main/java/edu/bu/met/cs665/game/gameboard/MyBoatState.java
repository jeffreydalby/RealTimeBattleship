package edu.bu.met.cs665.game.gameboard;

//State to display player has a boat on this location
public class MyBoatState implements GridItemState {
    @Override
    public GridDisplayItemText displayMe() {
        return GridDisplayItemText.myBoat;
    }
}
