package logic;

import logic.enums.CellStatus;
import logic.enums.ExceptionsMeassage;
import logic.exceptions.XmlContentException;

import java.util.LinkedList;
import java.util.List;

public class Board {

    CellStatus[][] board;
    List<Battleship> battleships;

    public Board(int boardSize, List<Battleship> battleships) throws XmlContentException {
        this.board = new CellStatus[boardSize + 1][boardSize + 1];
        this.battleships = battleships;
        buildBoard();
    }

    // **************************************************** //
    // Builds the game board
    // **************************************************** //
    private void buildBoard() throws XmlContentException {
        initBoard();

        for (Battleship currentBattleship : battleships) {
            Coordinate position = currentBattleship.getPosition();
            if (!checkCoordinate(position)) {
                throw new XmlContentException(ExceptionsMeassage.IllegalPosition);
            }
            board[position.getRow()][position.getColumn()] = CellStatus.SHIP;
        }

    }

    // **************************************************** //
    // Check if the position of the coordinate is valid
    // **************************************************** //
    private boolean checkCoordinate(Coordinate coordinate) {
        boolean result = true;
        int row = coordinate.getRow();
        int col = coordinate.getColumn();

        if (row >= board.length || col >= board.length || row < 1 || col < 1 || board[row][col] != CellStatus.REGULAR) {
            result = false;
        } else if (row - 1 != 0 && board[row - 1][col] != CellStatus.REGULAR) {
            result = false;
        } else if (row + 1 < board.length && board[row + 1][col] != CellStatus.REGULAR) {
            result = false;
        } else if (col - 1 != 0 && board[row][col - 1] != CellStatus.REGULAR) {
            result = false;
        } else if (col + 1 < board.length && board[row][col + 1] != CellStatus.REGULAR) {
            result = false;
        } else if (row - 1 != 0 && col - 1 != 0 && board[row - 1][col - 1] != CellStatus.REGULAR) {
            result = false;
        } else if (row + 1 < board.length && col + 1 < board.length && board[row + 1][col + 1] != CellStatus.REGULAR) {
            result = false;
        } else if (row - 1 != 0 && col + 1 < board.length && board[row - 1][col + 1] != CellStatus.REGULAR) {
            result = false;
        } else if (row + 1 < board.length && col - 1 != 0 && board[row + 1][col - 1] != CellStatus.REGULAR) {
            result = false;
        }
        return result;
    }

    // **************************************************** //
    // Initialize game board
    // **************************************************** //
    private void initBoard() {
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board.length; j++) {
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
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board.length; j++) {
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
