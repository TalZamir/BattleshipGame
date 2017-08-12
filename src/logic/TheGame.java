package logic;

import logic.builders.BattleshipBuilder;
import logic.enums.ExceptionsMeassage;
import logic.enums.GameType;
import logic.exceptions.XmlContentException;
import logic.interfaces.ISerializer;
import logic.serializers.XmlSerializer;
import module.BattleShipGameType;
import module.BoardType;

import java.util.List;

public class TheGame {

    private GameType gameType;
    private Player[] players;
    private int turns;
    private boolean isActive;
    private boolean isFileLoaded;
    private boolean isGameOn;
    private BattleShipGameType xmlContent;

    public TheGame() {
        isActive = true;
        isFileLoaded = false;
        isGameOn = false;
        players = new Player[2];
        turns = 0;
    }

    public void init() throws XmlContentException {
        isGameOn = true;
        initGameComponents();

        if (xmlContent.getGameType().equals(GameType.BASIC.getGameTypeValue())) {
            gameType = GameType.BASIC;
        } else {
            gameType = GameType.ADVANCE;
        }
    }

    // **************************************************** //
    // Loads a XML file
    // **************************************************** //
    public void loadFile(String path) throws XmlContentException {
        try {
            ISerializer serializer = new XmlSerializer(path);
            xmlContent = serializer.getBattleShipGameType();
            isFileLoaded = true;
        } catch (Exception exception) {
            isFileLoaded = false; // if the last file was fine and the current one is not
            throw new XmlContentException(ExceptionsMeassage.INVALID_XML); // XML reading error
        }
    }

    // **************************************************** //
    // Returns the current player ships board
    // **************************************************** //
    public char[][] getCurrentPlayerShipBoard() {
        int currentPlayerIndex = turns % 2;
        return players[currentPlayerIndex].getBoard().getAllieMode();
    }

    // **************************************************** //
    // Indicates if the user still wants to play
    // **************************************************** //
    public boolean isActive() {
        return isActive;
    }

    // **************************************************** //
    // Indicates if a game is on
    // **************************************************** //
    public boolean isGameOn() {
        return isGameOn;
    }

    // **************************************************** //
    // Indicates if a game is on
    // **************************************************** //
    public boolean isFileLoaded() {
        return isFileLoaded;
    }

    //TODO now we are creating only human users. Need to add computer user.
    private void initGameComponents() throws XmlContentException {
        BattleshipBuilder battleshipBuilder = new BattleshipBuilder(xmlContent.getShipTypes().getShipType());
        setBoards(Integer.parseInt(xmlContent.getBoardSize()), xmlContent.getBoards().getBoard(), battleshipBuilder);
    }

    // **************************************************** //
    // Defines players' boards
    // **************************************************** //
    private void setBoards(int boardSize, List<BoardType> boards, BattleshipBuilder battleshipBuilder) throws XmlContentException {
        int playIndex = 0;
        for (BoardType boardType : boards) {
            List<Battleship> battleships = battleshipBuilder.buildUserBattleships(boardType.getShip()); // Builds player battleships
            Board board = new Board(boardSize, battleships); // Builds player board
            Player player = new Player(board); // Sets player board
            players[playIndex] = player; // Inserts player to players array
        }
    }
}
