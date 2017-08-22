package logic;

import logic.enums.BattleshipDirectionType;
import logic.enums.CellStatus;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;

import java.util.LinkedList;
import java.util.List;

import static logic.enums.CellStatus.*;

public class Board {

    private final Cell[][] board;
    private List<Battleship> battleships;
    private int lastDestroyedScore;

    Board(int boardSize, List<Battleship> battleships) throws XmlContentException {
        board = new Cell[boardSize + 1][boardSize + 1];
        this.battleships = battleships;
        buildBoard();
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
    // Returns the board without MISS cells
    // **************************************************** //
    char[][] getAllieMode() {
        return getModifiedBoard(CellStatus.SHOW_ALL);
    }

    // **************************************************** //
    // Returns the board without SHIP cells
    // **************************************************** //
    char[][] getAdversaryMode() {
        return getModifiedBoard(CellStatus.SHIP);
    }

    // **************************************************** //
    // Draws a mine on the board (with validity checks)
    // **************************************************** //
    void drawMine(int row, int col) throws XmlContentException {
        CellStatus sign = board[row][col].getCellStatus();
        if (sign != REGULAR || !checkCoordinate(new Coordinate(row, col))) {
            throw new XmlContentException(ErrorMessages.MINE_ERROR);
        }
        board[row][col].setCellStatus(MINE);
    }

    // **************************************************** //
    // Draws a Miss sign instead of a Mine when a mine attacked a mine
    // **************************************************** //
    void drawMineAsMiss(int row, int col) {
        board[row][col].setCellStatus(MISS);
    }

    // **************************************************** //
    // Returns the score of the last destroyed battleship
    // **************************************************** //
    int getLastDestroyedScore() {
        return lastDestroyedScore;
    }

    CellStatus playMove(int row, int col) {
        CellStatus result;
        switch (board[row][col].getCellStatus()) {
            case REGULAR:
                board[row][col].setCellStatus(CellStatus.MISS);
                result = REGULAR;
                break;
            case SHIP:
                board[row][col].setCellStatus(CellStatus.HIT);
                result = performHit(board[row][col]);
                break;
            case MISS:
                result = CellStatus.MISS;
                break;
            case HIT:
                result = CellStatus.HIT;
                break;
            case MINE:
                board[row][col].setCellStatus(CellStatus.HIT);
                result = CellStatus.MINE;
                break;
            default:
                result = board[row][col].getCellStatus();
                break;
        }
        return result;
    }

    boolean isThereAliveShip() {
        for (Battleship battleship : battleships) {
            if (battleship.isAlive()) {
                return true;
            }
        }

        return false;
    }

    // **************************************************** //
    // Performs 'Hit' actions
    // **************************************************** //
    private CellStatus performHit(Cell cell) {
        CellStatus result;
        cell.getShipRef().decrementLength();
        if (cell.getShipRef().isAlive()) {
            result = SHIP;
        } else {
            result = SHIP_DOWN;
            lastDestroyedScore = cell.getShipRef().getScore();
        }
        return result;
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
                board[position.getRow()][position.getColumn()].setCellStatus(CellStatus.TEMP);
                board[position.getRow()][position.getColumn()].setPoint(position);
                board[position.getRow()][position.getColumn()].setShipRef(currentBattleship);
                incrementCoordinate(position, currentBattleship.getDirection());
            }
            changeTempSigns();
        }
    }

    // **************************************************** //
    // Get the next battleship cell position by its type
    // **************************************************** //
    private void incrementCoordinate(Coordinate coordinate, BattleshipDirectionType shipType) {
        if (shipType == BattleshipDirectionType.COLUMN) {
            coordinate.setRow(coordinate.getRow() + 1);
        } else {
            coordinate.setColumn(coordinate.getColumn() + 1);
        }
    }

    // **************************************************** //
    // Check if the position of the coordinate is valid
    // **************************************************** //
    private boolean checkCoordinate(Coordinate coordinate) {
        boolean result = true;
        int row = coordinate.getRow();
        int col = coordinate.getColumn();

        if (row >= board.length || col >= board.length || row < 1 || col < 1 || signCompare(board[row][col].getCellStatus())) {
            result = false;
        } else if (row - 1 != 0 && signCompare(board[row - 1][col].getCellStatus())) {
            result = false;
        } else if (row + 1 < board.length && signCompare(board[row + 1][col].getCellStatus())) {
            result = false;
        } else if (col - 1 != 0 && signCompare(board[row][col - 1].getCellStatus())) {
            result = false;
        } else if (col + 1 < board.length && signCompare(board[row][col + 1].getCellStatus())) {
            result = false;
        } else if (row - 1 != 0 && col - 1 != 0 && signCompare(board[row - 1][col - 1].getCellStatus())) {
            result = false;
        } else if (row + 1 < board.length && col + 1 < board.length && signCompare(board[row + 1][col + 1].getCellStatus())) {
            result = false;
        } else if (row - 1 != 0 && col + 1 < board.length && signCompare(board[row - 1][col + 1].getCellStatus())) {
            result = false;
        } else if (row + 1 < board.length && col - 1 != 0 && signCompare(board[row + 1][col - 1].getCellStatus())) {
            result = false;
        }
        return result;
    }

    // **************************************************** //
    // Sign compare
    // **************************************************** //
    private boolean signCompare(CellStatus sign) {
        return sign == SHIP || sign == MINE || sign == HIT;
    }

    // **************************************************** //
    // Change TEMP signs to SHIP signs
    // **************************************************** //
    private void changeTempSigns() {
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board.length; j++) {
                if (board[i][j].getCellStatus() == CellStatus.TEMP) {
                    board[i][j].setCellStatus(CellStatus.SHIP);
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
                board[i][j] = new Cell();
                board[i][j].setCellStatus(REGULAR);
            }
        }
    }

    // **************************************************** //
    // Returns the board without the given status cells
    // **************************************************** //
    private char[][] getModifiedBoard(CellStatus statusToIgnore) {
        char[][] allieBoard = new char[board.length][board.length];
        char currentStatus;
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board.length; j++) {
                if (board[i][j].getCellStatus() == statusToIgnore || statusToIgnore == SHIP && board[i][j].getCellStatus() == MINE) {
                    currentStatus = REGULAR.sign();
                } else {
                    currentStatus = board[i][j].getCellStatus().sign();
                }
                allieBoard[i][j] = currentStatus;
            }
        }
        return allieBoard;
    }
}
