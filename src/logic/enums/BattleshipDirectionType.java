package logic.enums;

/**
 * Created by barakm on 22/08/2017
 */
public enum BattleshipDirectionType {

    ROW("ROW"),
    COLUMN("COLUMN"),
    RIGHT_DOWN("RIGHT_DOWN"),
    RIGHT_UP("RIGHT_UP"),
    UP_RIGHT("UP_RIGHT"),
    DOWN_RIGHT("DOWN_RIGHT");

    private final String direction;

    BattleshipDirectionType(String direction) {
        this.direction = direction;
    }

    public String value() {
        return direction;
    }
}
