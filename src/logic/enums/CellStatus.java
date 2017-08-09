package logic.enums;

public enum CellStatus {

    HIT('$'),
    MISS('#'),
    SHIP('@'),
    REGULAR('*'),
    MINE('M');

    private char sign;

    CellStatus(char sign) {
        this.sign = sign;
    }

    public char sign() {
        return sign;
    }
}
