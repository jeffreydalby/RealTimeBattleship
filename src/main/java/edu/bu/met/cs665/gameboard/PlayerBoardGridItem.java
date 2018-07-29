package edu.bu.met.cs665.gameboard;

public class PlayerBoardGridItem extends GridItem{
    GridItem baseItem;

    public boolean isHasBoat() {
        return hasBoat;
    }
    //once we set has boat on a player's board we want to make sure it displays it with the boat
    public void setHasBoat(boolean hasBoat) {
        this.hasBoat = hasBoat;
        this.gridItemState = new MyBoatState();
    }

    private boolean hasBoat;

    public PlayerBoardGridItem(){
        super();
    }

}
