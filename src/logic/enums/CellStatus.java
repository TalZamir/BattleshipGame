package logic.enums;

public enum CellStatus {

    HIT('*'),
    MISS('0'),
    SHIP('@'),
    REGULAR('~'),
    MINE('+'),
    TEMP('&'),
    // TODO: Do we still need the 'W' sign?
    WIN('W'),
    SHIP_DOWN('^'), // symbol has no meaning
    SHOW_ALL('^'); // symbol has no meaning

    private char sign;

    CellStatus(char sign) {
        this.sign = sign;
    }

    public char sign() {
        return sign;
    }
}
