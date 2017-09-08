package logic;

import logic.enums.CellStatus;

public class GameStep {

    private final CellStatus cellStatus;
    private final int playerId;
    private final String playerName;
    private final int row;
    private final int col;


    public GameStep(CellStatus cellStatus, int playerId, int row, int col) {
        this.cellStatus = cellStatus;
        this.playerId = playerId;
        this.playerName = "Player" + (playerId + 1);
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

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
