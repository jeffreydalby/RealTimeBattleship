package edu.bu.met.cs665.game.gameboard;

public class GridItem {

    public boolean isHasShip() {
        return hasShip;
    }

    public void setHasShip(boolean hasShip) {
        this.hasShip = hasShip;
    }

    private boolean hasShip;
    GridItemState gridItemState;


    public GridItem(){
        //regardless of if there is a ship here we display as open water on base board
        gridItemState = new OpenWaterState();
    }


    @Override
    public String toString() {
        return (gridItemState.displayMe()).toString();
    }
}
