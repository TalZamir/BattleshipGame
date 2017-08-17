package logic;

import logic.builders.BattleshipBuilder;
import logic.enums.CellStatus;
import logic.enums.ErrorMessages;
import logic.enums.GameType;
import logic.exceptions.XmlContentException;
import logic.interfaces.ISerializer;
import logic.serializers.XmlSerializer;
import module.BattleShipGameType;
import module.BoardType;
import ui.UserMoveInput;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.XmlFileVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TheGame {

    private GameType gameType;
    private Player[] players;
    private boolean isActive;
    private boolean isFileLoaded;
    private boolean isGameOn;
    private BattleShipGameType xmlContent;
    private String currentPlayerName;
    private int currentPlayerIndex;
    private int opponentPlayerIndex;
    private boolean isPlayerWon;
    private long startingTime;

    public TheGame() {
        isActive = true;
        isFileLoaded = false;
        isGameOn = false;
        players = new Player[2];
        isPlayerWon = false;
        currentPlayerIndex = 0;
        opponentPlayerIndex = 1;
    }

    // **************************************************** //
    // Initialize game logic
    // **************************************************** //
    public void init() throws XmlContentException {
        initGameComponents();

        if (xmlContent.getGameType().equalsIgnoreCase(GameType.BASIC.getGameTypeValue())) {
            gameType = GameType.BASIC;
        } else {
            gameType = GameType.ADVANCE;
        }
    }

    // **************************************************** //
    // Starts a game
    // **************************************************** //
    public void startGame() throws XmlContentException {
        if (isFileLoaded) {
            isGameOn = true;
            startingTime = System.currentTimeMillis();
            for (Player player : players) {
                player.setTurnStartTime(startingTime);
            }
        } else {
            throw new XmlContentException(ErrorMessages.START_WITHOUT_XML); // XML not loaded yet
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
                    init();
                } else {
                    isFileLoaded = false;
                    return false;
                }
            } catch (XmlContentException exception) {
                isFileLoaded = false; // if the last file was fine and the current one is not
                throw exception; // XML content exception
            } catch (Exception exception) {
                isFileLoaded = false; // if the last file was fine and the current one is not
                throw new XmlContentException(ErrorMessages.INVALID_XML); // XML reading error
            }
        } else

        {
            return false;
        }

        return true;
    }

    // **************************************************** //
    // Returns the current player ships board
    // **************************************************** //
    public char[][] getCurrentPlayerBoardToPrint() {
        return getCurrentPlayerLogicBoard().getAllieMode();
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

    // **************************************************** //
    // Returns the current player name
    // **************************************************** //
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    // **************************************************** //
    // Returns the opponent board (partial mode)
    // **************************************************** //
    public char[][] getOpponentBoardToPrint() {
        return getOpponentLogicBoard().getAdversaryMode();
    }

    // **************************************************** //
    // Plays a game move
    // **************************************************** //
    public String playMove(UserMoveInput userMoveInput) {
        int row = userMoveInput.getUserRowInput();
        int col = userMoveInput.getUserColInput();
        String messageToReturn = null;

        CellStatus cellStatus = getOpponentLogicBoard().playMove(row, col);

        cellStatus = checkIfWin(cellStatus);

        switch (cellStatus) {
            case WIN:
                isPlayerWon = true;
                messageToReturn = "-------------------- GAME OVER --------------------" + System.lineSeparator() +
                        "~~~~~~~~~~~~~~ " + currentPlayerName + " WON THE GAME! ~~~~~~~~~~~~~~";
                break;
            case SHIP:
                messageToReturn = currentPlayerName + ": A HIT! You have another turn!";
                players[currentPlayerIndex].increaseHit();
                break;
            case HIT:
            case MISS:
                messageToReturn = currentPlayerName + ": you already attacked this cell, please choose a different one.";
                break;
            case REGULAR:
                messageToReturn = currentPlayerName + ": You missed...";
                players[currentPlayerIndex].increaseMiss();
                players[currentPlayerIndex].updateTime();
                switchTurn();
                break;
        }

        return messageToReturn;
    }

    // **************************************************** //
    // Returns game statistics
    // **************************************************** //
    public String getStatistics() {
        long millis = System.currentTimeMillis() - startingTime;
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        return ("Total Played Turns: " + (players[0].getTurns() + players[1].getTurns()) + System.lineSeparator() +
                "Total Time: " + time + System.lineSeparator() +
                players[0].toString() + System.lineSeparator() + players[1].toString());
    }

    public boolean isPlayerWon() {
        return isPlayerWon;
    }

    public void setPlayerWon(boolean playerWon) {
        this.isPlayerWon = playerWon;
    }

    public void resetGame() {
        isGameOn = false;
    }

    private void switchTurn() {
        if (currentPlayerIndex == 0) {
            currentPlayerName = players[1].getName();
            players[1].setTurnStartTime(System.currentTimeMillis());
            currentPlayerIndex = 1;
            opponentPlayerIndex = 0;
        } else {
            currentPlayerName = players[0].getName();
            players[0].setTurnStartTime(System.currentTimeMillis());
            currentPlayerIndex = 0;
            opponentPlayerIndex = 1;
        }
    }

    private CellStatus checkIfWin(CellStatus cellStatus) {
        if (!getOpponentLogicBoard().isThereAliveShip()) {
            return CellStatus.WIN;
        }

        return cellStatus;
    }

    private Board getCurrentPlayerLogicBoard() {
        return players[currentPlayerIndex].getBoard();
    }

    private Board getOpponentLogicBoard() {
        return players[opponentPlayerIndex].getBoard();
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

        if (boards.size() != 2) {
            throw new XmlContentException(ErrorMessages.XML_CONTENT); // XML reading error
        }

        for (BoardType boardType : boards) {
            List<Battleship> battleships = battleshipBuilder.buildUserBattleships(boardType.getShip()); // Builds player battleships
            Board board = new Board(boardSize, battleships); // Builds player board
            Player player = new Player(String.format("Player%d", playerIndex + 1), board); // Sets player board
            players[playerIndex] = player; // Inserts player to players array
            playerIndex++;
        }
        currentPlayerName = players[0].getName();
    }

    public String quitMatch() {
        isGameOn = false;
        String messageToReturn = "-------------------- GAME OVER --------------------" + System.lineSeparator() +
                "~~~~ " + players[currentPlayerIndex].getName() + " quit... " + players[opponentPlayerIndex].getName() +
                " WON THE GAME! ~~~~";
        return messageToReturn;
    }


    // **************************************************** //
    // Indicates that the user ask to shut down the game
    // **************************************************** //
    public void exitGame() {
        isActive = false;
    }
}
