package logic;

import logic.enums.BattleshipType;
import logic.enums.CellStatus;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;

import java.util.LinkedList;
import java.util.List;

import static logic.enums.CellStatus.REGULAR;

public class Board {

    private final Cell[][] board;
    private List<Battleship> battleships;

    public Board(int boardSize, List<Battleship> battleships) throws XmlContentException {
        board = new Cell[boardSize + 1][boardSize + 1];
        this.battleships = battleships;
        buildBoard();
    }

    // **************************************************** //
    // Returns the board without MISS cells
    // **************************************************** //
    public char[][] getAllieMode() {
        return getModifiedBoard(CellStatus.SHOW_ALL);
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

    CellStatus playMove(int row, int col) {

        switch (board[row][col].getCellStatus()) {
            case REGULAR:
                board[row][col].setCellStatus(CellStatus.MISS);
                return REGULAR;
            case SHIP:
                updateShipHit(board[row][col].getShipId());
                board[row][col].setCellStatus(CellStatus.HIT);
                return CellStatus.SHIP;
            case MISS:
                return CellStatus.MISS;
            case HIT:
                return CellStatus.HIT;
            case MINE:
                return CellStatus.MINE;
            case TEMP:
            case WIN:
            default:
                return board[row][col].getCellStatus();
        }
    }

    //TODO: I think if there are more then one ship with the same id this method won't work
    private void updateShipHit(String shipId) {
        for (Battleship battleship : battleships) {
            if (battleship.getId().equals(shipId)) {
                battleship.decrementLength();
                return;
            }
        }
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
                board[position.getRow()][position.getColumn()].setShipId(currentBattleship.getId());
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

        if (row >= board.length || col >= board.length || row < 1 || col < 1 || !signCompare(board[row][col].getCellStatus())) {
            result = false;
        } else if (row - 1 != 0 && !signCompare(board[row - 1][col].getCellStatus())) {
            result = false;
        } else if (row + 1 < board.length && !signCompare(board[row + 1][col].getCellStatus())) {
            result = false;
        } else if (col - 1 != 0 && !signCompare(board[row][col - 1].getCellStatus())) {
            result = false;
        } else if (col + 1 < board.length && !signCompare(board[row][col + 1].getCellStatus())) {
            result = false;
        } else if (row - 1 != 0 && col - 1 != 0 && !signCompare(board[row - 1][col - 1].getCellStatus())) {
            result = false;
        } else if (row + 1 < board.length && col + 1 < board.length && !signCompare(board[row + 1][col + 1].getCellStatus())) {
            result = false;
        } else if (row - 1 != 0 && col + 1 < board.length && !signCompare(board[row - 1][col + 1].getCellStatus())) {
            result = false;
        } else if (row + 1 < board.length && col - 1 != 0 && !signCompare(board[row + 1][col - 1].getCellStatus())) {
            result = false;
        }
        return result;
    }

    // **************************************************** //
    // Sign compare
    // **************************************************** //
    private boolean signCompare(CellStatus sign) {
        return sign == REGULAR || sign == CellStatus.TEMP;
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
                if (board[i][j].getCellStatus() == statusToIgnore) {
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
