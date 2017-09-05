package logic;

import logic.enums.CellStatus;

public class GameStep {

    private final CellStatus cellStatus;
    private final String playerName;
    private final int row;
    private final int col;


    public GameStep(CellStatus cellStatus, String playerName, int row, int col) {
        this.cellStatus = cellStatus;
        this.playerName = playerName;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        String cell = ("(" + row + "," + col + ")");
        String result = (playerName + " attacked " + cell + " and ");
        switch (cellStatus) {
            case REGULAR:
                result += "miss.";
                break;
            case SHIP:
                result += "hit a battleship.";
                break;
            case SHIP_DOWN:
                result += "destroyed a battleship.";
                break;
            case MINE:
                result += "hit a mine.";
                break;
            case MINE_PLACED:
                result = (playerName + " placed a mine at " + cell);
                break;
            default:
                result += "blaaaaa";
                break;
        }
        return result;
    }
}
