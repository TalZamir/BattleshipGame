package logic.enums;

public enum BattleshipType {
    VERTICAL("row"),
    HORIZONTAL("column");

    private String shipType;

    BattleshipType(String shipType) {
        this.shipType = shipType;
    }

    public String shipType() {
        return shipType;
    }
}
