package edu.bu.met.cs665.game;

import edu.bu.met.cs665.game.gameboard.PlayersBoard;
import edu.bu.met.cs665.game.gameboard.Ships;

import java.util.stream.Stream;

public class Game {


    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private static Game gameInstance;
    private PlayersBoard player1Board;
    private PlayersBoard player2Board;
    private Player player1;
    private Player player2;


    private void Game() {
    }


    public static Game getInstance() {
        if (gameInstance == null) gameInstance = new Game();
        return gameInstance;
    }


    public void newStandardRandomGame() {
        //create new boards in a 10 x 10 array
        this.player1Board = new PlayersBoard();
        player1Board.setupBoard(10);
        this.player2Board = new PlayersBoard();
        player2Board.setupBoard(10);
        //add standard ships to boards
        Stream.of(Ships.values()).forEach(shipType -> player1Board.addRandomBoat(shipType.getLength()));
        Stream.of(Ships.values()).forEach(shipType -> player2Board.addRandomBoat(shipType.getLength()));
        //configure the players
        this.player1 = new Player("Player 1", player1Board, player2Board);
        this.player2 = new Player("Player 2", player2Board, player1Board);

    }


    //create new game with given board size without ship placement
    public void newGame(int boardSize) {
        this.player1Board = new PlayersBoard();
        player1Board.setupBoard(boardSize);
        this.player2Board = new PlayersBoard();
        player2Board.setupBoard(boardSize);

        this.player1 = new Player("Player 1", player1Board, player2Board);
        this.player1 = new Player("Player 2", player2Board, player1Board);


    }


}
