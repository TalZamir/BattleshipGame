package logic.enums;

public enum BattleshipType {
    VERTICAL("column"),
    HORIZONTAL("row");

    private String shipType;

    BattleshipType(String shipType) {
        this.shipType = shipType;
    }

    public String shipType() {
        return shipType;
    }
}
