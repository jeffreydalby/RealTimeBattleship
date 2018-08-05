package edu.bu.met.cs665.game.gameboard;

//Each game board is mad up of a grid item which maintains its own state
public class GridItem {
    //let's us know if a ship has been placed on this spot
    boolean isHasShip() {
        return hasShip;
    }

    void setHasShip(boolean hasShip) {
        this.hasShip = hasShip;
    }

    private boolean hasShip;
    GridItemState gridItemState; //State design pattern to make it simple to track current squares state


    GridItem() {
        //regardless of if there is a ship here we display as open water on base board
        gridItemState = new OpenWaterState();
    }

    //we use the state to control how we display to the screen
    @Override
    public String toString() {
        return (gridItemState.displayMe()).toString();
    }
}
