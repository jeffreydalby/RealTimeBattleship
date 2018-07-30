package edu.bu.met.cs665.game.gameboard;

public enum Ships {
    battleship("Battleship",4),
    submarine("Submarine",3),
    destroyer("Destroyer",3),
    patrol("Patrol Ships",2),
    carrier("Aircraft Carrier", 5);

    public int getLength() {
        return length;
    }
    private String boatType;
    private int length;
    Ships(String boatType, int length){
        this.boatType= boatType;
        this.length = length;
    }

    @Override
    public String toString() {
        return this.boatType;
    }


}
