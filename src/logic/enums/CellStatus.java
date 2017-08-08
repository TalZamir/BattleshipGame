package logic.enums;

public enum CellStatus {

    HIT('$'),
    MISS('#'),
    SHIP('@'),
    INITIAL('*');

    private char sign;

    CellStatus(char sign) {
        this.sign = sign;
    }

    public char sign() {
        return sign;
    }
}
