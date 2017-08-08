package Logic;

public class Coordinate {
    private final int row;
    private final char column;

    public Coordinate(int row, char column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }
}

