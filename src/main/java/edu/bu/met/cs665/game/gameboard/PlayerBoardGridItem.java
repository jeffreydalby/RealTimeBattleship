package edu.bu.met.cs665.game.gameboard;

public class PlayerBoardGridItem extends GridItem{
    GridItem displayItem;

    public boolean isHasBoat() {
        return displayItem.isHasShip();
    }
    //once we set has boat on a player's board we want to make sure it displays it with the boat
    public void setHasBoat(boolean hasBoat) {
        this.displayItem.setHasShip(hasBoat);
        this.gridItemState = new MyBoatState();
    }


    public PlayerBoardGridItem(){
        super();
        this.displayItem = new GridItem();
    }

    /**
     * Let's the player take a shot and set the state based on if there is a ship here
     */
    public boolean takeShot(){
        if (this.displayItem.isHasShip()) {
            this.gridItemState = new HitState();
            this.displayItem.gridItemState = new HitState();
            return true;}
        else {
            this.gridItemState = new MissedShotState();
            this.displayItem.gridItemState = new MissedShotState();
        }
        return false;
    }


}
