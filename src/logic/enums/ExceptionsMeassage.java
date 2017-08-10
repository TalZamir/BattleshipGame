package logic.enums;

/**
 * Created by xozh4v on 8/9/2017.
 */
public enum ExceptionsMeassage {

    DuplicateShipId("The battleship ID name must be unique!"),
    UnknownShipId("One or more of battleships' ID's is unknown!"),
    ShipMissmatch("The amount of the placed battleships doesn't match to the definition!"),
    IllegalPosition("The battleships' positions are illegal! Please check your boards.");

    private String message;

    ExceptionsMeassage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}