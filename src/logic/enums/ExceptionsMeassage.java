package logic.enums;

/**
 * Created by xozh4v on 8/9/2017.
 */
public enum ExceptionsMeassage {

    DUPLICATE_SHIP_ID("The battleship ID name must be unique!"),
    UNKNOWN_SHIP_ID("One or more of battleships' ID's is unknown!"),
    SHIP_MISSMATCH("The amount of the placed battleships doesn't match to the definition!"),
    ILLEGAL_POSITION("The battleships' positions are illegal! Please check your boards."),
    XML_NOT_EXISTS("XML file does not exists!"),
    INVALID_XML("Invalid XML file");

    private String message;

    ExceptionsMeassage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}