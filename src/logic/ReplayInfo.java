package logic;

public class ReplayInfo {

    private final String description;
    private char[][] personalBoard = null;
    private char[][] traceBoard = null;

    public ReplayInfo(char[][] personal, char[][] trace, String description) {
        this.description = description;
        this.personalBoard = personal;
        this.traceBoard = trace;
    }

    public char[][] getPersonalBoard() {
        return personalBoard;
    }

    @Override
    public String toString() {
        return description;
    }

    public char[][] getTraceBoard() {
        return traceBoard;
    }
}
