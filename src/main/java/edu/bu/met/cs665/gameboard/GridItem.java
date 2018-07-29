package edu.bu.met.cs665.gameboard;

public class GridItem {

    private boolean hasShip;
    GridItemState gridItemState;


    public GridItem(){
        //regardless of if there is a ship here we display as open water on base board
        gridItemState = new OpenWaterState();
    }

    /**
     * Let's the player take a shot and set the state based on if there is a ship here
     */
    public void takeShot(){
        if (hasShip) gridItemState = new HitState();
        else gridItemState = new MissedShotState();
    }

    @Override
    public String toString() {
        return (gridItemState.displayMe()).toString();
    }
}
