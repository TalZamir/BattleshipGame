package logic;

import logic.enums.CellStatus;

public class GameStep {

    private final CellStatus cellStatus;
    private final int playerId;
    private final String playerName;
    private final int row;
    private final int col;
    private final char[][] personalBoard;
    private final char[][] traceBoard;


    public GameStep(CellStatus cellStatus, int row, int col, Player player, Player opponent) {
        this.cellStatus = cellStatus;
        this.row = row;
        this.col = col;

        personalBoard = copyBoard(player.getBoard().getAllieMode());
        traceBoard = copyBoard(opponent.getBoard().getAdversaryMode());

        this.playerId = player.getUserId();
        this.playerName = "Player" + (playerId + 1);
    }

    private char[][] copyBoard(char[][] boardToCopy) {
        char[][] actualBoard = new char[boardToCopy.length][boardToCopy.length];
        for (int i = 1; i < boardToCopy.length; i++) {
            for (int j = 1; j < boardToCopy.length; j++) {
                actualBoard[i][j] = boardToCopy[i][j];
            }
        }
        return actualBoard;
    }

    public ReplayInfo getReplayInfo() {
        System.out.println("bla");
        return new ReplayInfo(personalBoard, traceBoard);
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

    public char[][] getPersonalBoard() {
        return personalBoard;
    }

    public char[][] getTraceBoard() {
        return traceBoard;
    }
}
