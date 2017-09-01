package logic.enums;

/**
 * Created by xozh4v on 8/9/2017.
 */
public enum ErrorMessages {

    XML_CONTENT("There are some problems with the XML content! Please check the XML file and try again."),
    DUPLICATE_SHIP_ID("The battleship ID name must be unique!"),
    UNKNOWN_SHIP_ID("There is a problem with the battleships ID!"),
    SHIP_MISSMATCH("The amount of the placed battleships doesn't match to the definition!"),
    ILLEGAL_POSITION("The battleships' positions are illegal! Please check your boards."),
    XML_NOT_EXISTS("XML file does not exists!"),
    EMPTY_FILE_NAME("File name can not be empty"),
    INVALID_XML("Invalid XML file"),
    BOARD_SIZE("Board size must be between 5 to 20"),
    GAME_IS_ALREADY_ON("Sorry, but you can't load XML while game is on."),
    START_WITHOUT_XML("You can't start a game without loading a valid XML file!"),
    XML_PREFIX("You must enter an xml file!"),
    SHIP_LENGTH("Battleship length is illegal!"),
    MINE_ERROR("The position for the mine is illegal!"),
    MINE_LIMIT("You already used all of your mines!"),
    INVALID_GAME_TYPE("Invalid game type"),
    INVALID_BATTLESHIP_DIRECTION("Invalid battleship direction"),
    INVALID_GAME_TYPE_OR_SHIP_CATEGORY("Invalid game type and ship type");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}