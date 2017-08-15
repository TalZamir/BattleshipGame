package logic;

import logic.enums.BattleshipType;
import logic.enums.CellStatus;
import logic.enums.ErrorMessages;
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

    // **************************************************** //
    // Builds the game board
    // **************************************************** //
    private void buildBoard() throws XmlContentException {
        initBoard();

        for (Battleship currentBattleship : battleships) {
            Coordinate position = currentBattleship.getPosition();
            for (int i = 0; i < currentBattleship.getLength(); i++) {
                if (!checkCoordinate(position)) {
                    throw new XmlContentException(ErrorMessages.ILLEGAL_POSITION);
                }
                board[position.getRow()][position.getColumn()] = CellStatus.TEMP;
                incrementCoordinate(position, currentBattleship.getDirection());
            }
            changeTempSigns();
        }
    }

    // **************************************************** //
    // Get the next battleship cell position by its type
    // **************************************************** //
    private void incrementCoordinate(Coordinate coordinate, String shipType) {
        if (shipType.toLowerCase().equals(BattleshipType.VERTICAL.shipType())) {
            coordinate.setColumn(coordinate.getColumn() + 1);
        } else {
            coordinate.setRow(coordinate.getRow() + 1);
        }
    }

    // **************************************************** //
    // Check if the position of the coordinate is valid
    // **************************************************** //
    private boolean checkCoordinate(Coordinate coordinate) {
        boolean result = true;
        int row = coordinate.getRow();
        int col = coordinate.getColumn();

        if (row >= board.length || col >= board.length || row < 1 || col < 1 || !signCompare(board[row][col])) {
            result = false;
        } else if (row - 1 != 0 && !signCompare(board[row - 1][col])) {
            result = false;
        } else if (row + 1 < board.length && !signCompare(board[row + 1][col])) {
            result = false;
        } else if (col - 1 != 0 && !signCompare(board[row][col - 1])) {
            result = false;
        } else if (col + 1 < board.length && !signCompare(board[row][col + 1])) {
            result = false;
        } else if (row - 1 != 0 && col - 1 != 0 && !signCompare(board[row - 1][col - 1])) {
            result = false;
        } else if (row + 1 < board.length && col + 1 < board.length && !signCompare(board[row + 1][col + 1])) {
            result = false;
        } else if (row - 1 != 0 && col + 1 < board.length && !signCompare(board[row - 1][col + 1])) {
            result = false;
        } else if (row + 1 < board.length && col - 1 != 0 && !signCompare(board[row + 1][col - 1])) {
            result = false;
        }
        return result;
    }

    // **************************************************** //
    // Sign compare
    // **************************************************** //
    private boolean signCompare(CellStatus sign) {
        return sign == CellStatus.REGULAR || sign == CellStatus.TEMP;
    }

    // **************************************************** //
    // Change TEMP signs to SHIP signs
    // **************************************************** //
    private void changeTempSigns() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == CellStatus.TEMP) {
                    board[i][j] = CellStatus.SHIP;
                }
            }
        }
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
}
