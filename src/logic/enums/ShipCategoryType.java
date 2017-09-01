package logic.enums;

/**
 * Created by barakm on 01/09/2017
 */
public enum ShipCategoryType {
    REGULAR("REGULAR"),
    L_SHIP("L_SHIP");

    private final String value;

    ShipCategoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
