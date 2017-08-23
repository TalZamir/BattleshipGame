package logic.enums;

public enum CellStatus {

    HIT('*'),
    MISS('O'),
    SHIP('@'),
    REGULAR('~'),
    MINE('+'),
    TEMP('&'),
    SHIP_DOWN('^'), // symbol has no meaning
    MINE_PLACED('^'), // symbol has no meaning
    SHOW_ALL('^'); // symbol has no meaning

    private char sign;

    CellStatus(char sign) {
        this.sign = sign;
    }

    public char sign() {
        return sign;
    }
}
