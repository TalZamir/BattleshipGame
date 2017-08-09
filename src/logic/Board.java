package logic;

import logic.enums.CellStatus;

import java.util.LinkedList;
import java.util.List;

public class Board {

    CellStatus[][] board;
    List<Battleship> battleships;

    public Board(int boardSize, List<Battleship> battleships) {
        this.board = new CellStatus[boardSize][boardSize];
        this.battleships = battleships;
        buildBoard();
    }

    // **************************************************** //
    // Builds the game board
    // **************************************************** //
    private void buildBoard() {
        initBoard();
        for(Battleship currentBattleship : battleships) {
            Coordinate position = currentBattleship.getPosition();
            board[position.getRow()][position.getColumn()] = CellStatus.SHIP;
        }

    }

    // **************************************************** //
    // Initialize game board
    // **************************************************** //
    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = CellStatus.REGULAR;
            }
        }
    }

    // **************************************************** //
    // Returns the board without MISS cells
    // **************************************************** //
    public char[][] getAllieMode() {
        return getModifiedBoard(CellStatus.MISS);
    }

    // **************************************************** //
    // Returns the board without SHIP cells
    // **************************************************** //
    public char[][] getAdversaryMode() {
        return getModifiedBoard(CellStatus.SHIP);
    }

    // **************************************************** //
    // Returns the board without MISS cells
    // **************************************************** //
    private char[][] getModifiedBoard(CellStatus statusToIgnore) {
        char[][] allieBoard = new char[board.length][board.length];
        char currentStatus;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == statusToIgnore) {
                    currentStatus = CellStatus.REGULAR.sign();
                } else {
                    currentStatus = board[i][j].sign();
                }
                allieBoard[i][j] = currentStatus;
            }
        }
        return allieBoard;
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

    public List<Battleship> getBattleships() {
        return battleships;
    }

    public void setBattleships(LinkedList<Battleship> battleships) {
        this.battleships = battleships;
    }
}
