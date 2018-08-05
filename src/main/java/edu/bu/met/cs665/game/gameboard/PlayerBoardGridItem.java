package edu.bu.met.cs665.game.gameboard;

//Class for each grid on the players board
//this allows us to only show boat to the player and not the opponent.
class PlayerBoardGridItem extends GridItem {
    GridItem opponentsViewGridItem;

    boolean isHasBoat() {
        return opponentsViewGridItem.isHasShip();
    }

    //once we set has boat on a player's board we want to make sure it displays it with the boat
    void setHasBoat(boolean hasBoat) {
        this.opponentsViewGridItem.setHasShip(hasBoat);
        this.gridItemState = new MyBoatState();
    }


    PlayerBoardGridItem() {
        super();
        this.opponentsViewGridItem = new GridItem();
    }

    /**
     * Lets the player take a shot,then set the state based on if there is a ship here
     *
     * @return - true if show is a hit
     */
    boolean takeShot() {
        if (this.opponentsViewGridItem.isHasShip()) {
            this.gridItemState = new HitState();
            this.opponentsViewGridItem.gridItemState = new HitState();
            return true;
        } else {
            this.gridItemState = new MissedShotState();
            this.opponentsViewGridItem.gridItemState = new MissedShotState();
        }
        return false;
    }


}
