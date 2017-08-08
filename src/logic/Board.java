package logic;

import logic.enums.CellStatus;

import java.util.LinkedList;

public class Board {

    CellStatus[][] board;
    LinkedList<Battleship> battleships;

    public Board(int boardSize) {
        board = new CellStatus[boardSize][boardSize];
    }

    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = CellStatus.INITIAL;
            }
        }
    }

    public void printAsAllie() {

    }

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
