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
    SHOW_ALL('^'), // symbol has no meaning
    MINE_HIT_REGULAR('^'), // symbol has no meaning
    MINE_HIT_ALREADY('^'), // symbol has no meaning
    MINE_HIT_MINE('^'), // symbol has no meaning
    MINE_HIT_SHIP('^'), // symbol has no meaning
    MINE_HIT_DESTROYED('^'); // symbol has no meaning


    private char sign;

    CellStatus(char sign) {
        this.sign = sign;
    }

    public char sign() {
        return sign;
    }
}
