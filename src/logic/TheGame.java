package logic;

import logic.builders.BattleshipBuilder;
import logic.enums.ErrorMessages;
import logic.enums.GameType;
import logic.exceptions.XmlContentException;
import logic.interfaces.ISerializer;
import logic.serializers.XmlSerializer;
import module.BattleShipGameType;
import module.BoardType;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.XmlFileVerifier;

import java.util.List;

public class TheGame {

    private GameType gameType;
    private Player[] players;
    private int turns;
    private boolean isActive;
    private boolean isFileLoaded;
    private boolean isGameOn;
    private BattleShipGameType xmlContent;
    private String currentPlayerName;

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

        if (xmlContent.getGameType().equalsIgnoreCase(GameType.BASIC.getGameTypeValue())) {
            gameType = GameType.BASIC;
        } else {
            gameType = GameType.ADVANCE;
        }
    }

    // **************************************************** //
    // Loads a XML file
    // **************************************************** //
    public boolean loadFile(String path, ErrorCollector errorCollector) throws XmlContentException {
        if (!isGameOn) {
            IInputVerifier inputVerifier = new XmlFileVerifier();
            try {
                ISerializer serializer = new XmlSerializer(path);
                xmlContent = serializer.getBattleShipGameType();
                if (inputVerifier.isContentOK(xmlContent, errorCollector)) {
                    isFileLoaded = true;
                } else {
                    isFileLoaded = false;
                    return false;
                }
            } catch (Exception exception) {
                isFileLoaded = false; // if the last file was fine and the current one is not
                throw new XmlContentException(ErrorMessages.INVALID_XML); // XML reading error
            }
        } else {
            return false;
        }

        return true;
    }

    // **************************************************** //
    // Returns the current player ships board
    // **************************************************** //
    public char[][] getCurrentPlayerShipBoard() {
        int currentPlayerIndex = turns % 2;
        currentPlayerName = players[currentPlayerIndex].getName();
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

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public char[][] getOpponentBoard() {
        int opponentPlayerIndex = turns / 2;
        return players[opponentPlayerIndex].getBoard().getAdversaryMode();
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
        int playerIndex = 0;
        for (BoardType boardType : boards) {
            List<Battleship> battleships = battleshipBuilder.buildUserBattleships(boardType.getShip()); // Builds player battleships
            Board board = new Board(boardSize, battleships); // Builds player board
            Player player = new Player(String.format("Player%d", playerIndex + 1), board); // Sets player board
            players[playerIndex] = player; // Inserts player to players array
            playerIndex++;
        }
    }
}
