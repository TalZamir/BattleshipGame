package logic;

public class ReplayInfo {

    private char[][] personalBoard = null;
    private char[][] traceBoard = null;

    public ReplayInfo(char[][] personal, char[][] trace) {
        this.personalBoard = personal;
        this.traceBoard = trace;
    }

    public char[][] getPersonalBoard() {
        return personalBoard;
    }

    public char[][] getTraceBoard() {
        return traceBoard;
    }
}
