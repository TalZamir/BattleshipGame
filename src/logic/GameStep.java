package logic;

import logic.enums.CellStatus;

import static logic.enums.CellStatus.MINE_PLACED;
import static logic.enums.CellStatus.QUIT_MATCH;

public class GameStep {

    private final CellStatus cellStatus;
    private final int row;
    private final int col;

    private final String playerName;
    private int score;
    private final char[][] personalBoard;
    private final char[][] traceBoard;


    public GameStep(CellStatus cellStatus, int row, int col, Player player, Player opponent) {
        this.cellStatus = cellStatus;
        this.row = row;
        this.col = col;

        this.playerName = player.getName();
        this.score = player.getScore();
        personalBoard = copyBoard(player.getBoard().getAllieMode());
        traceBoard = copyBoard(opponent.getBoard().getAdversaryMode());
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
        return new ReplayInfo(personalBoard, traceBoard, this.toString());
    }

    private String toStringHelper() {
        char letterCol = (char) ('A' + (col - 1));
        String cell = ("(" + row + "," + letterCol + ")");
        String stepResult;
        if (cellStatus == MINE_PLACED) {
            stepResult = ("Move: placed a mine at " + cell + System.lineSeparator() + "Result: -");
        } else if (cellStatus == QUIT_MATCH) {
            stepResult = ("Move: quit from the match" + System.lineSeparator() + "Result: -");
        } else {
            stepResult = ("Move: attacked " + cell + System.lineSeparator() + "Result: ");
        }

        switch (cellStatus) {
            case REGULAR:
                stepResult += "miss";
                break;
            case SHIP:
                stepResult += "hit a battleship";
                break;
            case SHIP_DOWN:
                stepResult += "destroyed a battleship";
                break;
            case MINE_HIT_ALREADY:
                stepResult += "hit a mine - the parallel cell was already attacked";
                break;
            case MINE_HIT_REGULAR:
                stepResult += "hit a mine - the parallel cell was empty";
                break;
            case MINE_HIT_SHIP:
                stepResult += "hit a mine - a battleship got hit";
                break;
            case MINE_HIT_DESTROYED:
                stepResult += "hit a mine - a battleship got destroyed";
                break;
            case MINE_HIT_MINE:
                stepResult += "hit a mine - a mine got hit too";
                break;
            case MINE_PLACED:
                break;
        }
        return stepResult;
    }

    @Override
    public String toString() {
        String stepResult = toStringHelper();
        return ("Player: " + playerName + System.lineSeparator() +
                "Score: " + score + System.lineSeparator() +
                stepResult);
    }


    public CellStatus getCellStatus() {
        return cellStatus;
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
