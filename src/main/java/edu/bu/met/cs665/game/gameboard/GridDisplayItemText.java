package edu.bu.met.cs665.game.gameboard;

//enum to give central location to modify string output for state
public enum GridDisplayItemText {
            openWater("~"),
            missedShot("*"),
            hit("X"),
            myBoat("B");

            private String outputString;

            GridDisplayItemText(String outputString){
                this.outputString = outputString;
            }


    @Override
    public String toString() {
        return this.outputString;
    }
}
