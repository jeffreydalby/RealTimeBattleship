package edu.bu.met.cs665.game;

import edu.bu.met.cs665.game.gameboard.PlayersBoard;
import edu.bu.met.cs665.game.gameboard.Ships;

import java.util.stream.Stream;

//Singleton instantiation of a Game of Battleship
public class Game {


    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private static Game gameInstance; //return singleton instance


    private Player player1;
    private Player player2;


    private void Game() {
    }


    public static synchronized Game getInstance() {
        if (gameInstance == null) gameInstance = new Game();
        return gameInstance;
    }

    /**
     * Generate a new game based on standard edition of battleship board size
     * and number of boats, with random placement
     */
    public void newStandardRandomGame() {
        //create new boards in a 10 x 10 array
        PlayersBoard player1Board;
        PlayersBoard player2Board;

        player1Board = new PlayersBoard();
        player1Board.setupBoard(10);
        player2Board = new PlayersBoard();
        player2Board.setupBoard(10);

        //add standard ships to boards
        Stream.of(Ships.values()).forEach(shipType -> player1Board.addRandomBoat(shipType.getLength()));
        Stream.of(Ships.values()).forEach(shipType -> player2Board.addRandomBoat(shipType.getLength()));

        //configure the players
        this.player1 = new Player("Player 1", player1Board, player2Board);
        this.player2 = new Player("Player 2", player2Board, player1Board);

    }

    /**
     * create new game with given board size without ship placement
     * This method is to allow for future development where a player can choose board size and boat placement.
     *
     * @param boardSize - int for both x/y dimensions (all boards are square
     */
    public void newGame(int boardSize) {
        //create new boards in a boardSize array
        PlayersBoard player1Board;
        PlayersBoard player2Board;
        player1Board = new PlayersBoard();
        player1Board.setupBoard(boardSize);
        player2Board = new PlayersBoard();
        player2Board.setupBoard(boardSize);

        //configure the players
        this.player1 = new Player("Player 1", player1Board, player2Board);
        this.player1 = new Player("Player 2", player2Board, player1Board);


    }


}
