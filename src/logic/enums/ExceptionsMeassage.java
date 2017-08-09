package logic.enums;

/**
 * Created by xozh4v on 8/9/2017.
 */
public enum ExceptionsMeassage {

    DuplicateShipId("Duplicate battleship IDs"),
    UnknownShipId("The given battleship's ID is unknown"),
    ShipMissmatch("The number of the filled battleships doesn't match to the total amount"),
    IllegalPosition("The battleships' positions are illegal");

    private String message;

    ExceptionsMeassage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}