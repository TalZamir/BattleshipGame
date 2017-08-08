package logic;

import java.util.LinkedList;

public class Board {

    Battleship[][] board;
    LinkedList<Battleship> battleships;

    public Board(int boardSize) {
        board = new Battleship[boardSize][boardSize];
    }

    public void print

    // **************************************************** //
    // Returns the status of a cell in the board
    // **************************************************** //
    public void getCellStatus(Coordinate point) {

    }

    // **************************************************** //
    // Attacks a cell on the board
    // **************************************************** //
    public void attackCell(Coordinate point) {

    }

    public LinkedList<Battleship> getBattleships() {
        return battleships;
    }

    public void setBattleships(LinkedList<Battleship> battleships) {
        this.battleships = battleships;
    }
}
