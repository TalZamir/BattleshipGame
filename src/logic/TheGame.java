package logic;

import logic.bases.BattleshipBase;
import logic.battleships.Battleship;
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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import static logic.enums.CellStatus.*;

public class TheGame {

    private static final int UNINITIALIZED = -1;

    private final Player[] players;
    private GameType gameType;
    private boolean isActive;
    private boolean isFileLoaded;
    private boolean isGameOn;
    private BattleShipGameType xmlContent;
    private String currentPlayerName;
    private int currentPlayerIndex;
    private int opponentPlayerIndex;
    private boolean isPlayerWon;
    private long startingTime;
    private List<GameStep> gameSteps;
    private int gameStepIndex;
    private CellStatus mineHandler;

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
        if (xmlContent.getGameType().equalsIgnoreCase(GameType.BASIC.getGameTypeValue())) {
            gameType = GameType.BASIC;
        } else if (xmlContent.getGameType().equalsIgnoreCase(GameType.ADVANCE.getGameTypeValue())) {
            gameType = GameType.ADVANCE;
        } else {
            throw new XmlContentException(ErrorMessages.INVALID_GAME_TYPE);
        }

        initGameComponents();
    }

    // **************************************************** //
    // Starts a game
    // **************************************************** //
    public void startGame() throws XmlContentException {
        if (isFileLoaded) {
            init();
            currentPlayerIndex = 0;
            opponentPlayerIndex = 1;
            isGameOn = true;
            gameSteps = new ArrayList<GameStep>();
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
        } else {
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
    public String playMove(UserMoveInput userMoveInput, boolean isRegularMove) throws XmlContentException {
        int row = userMoveInput.getUserRowInput();
        int col = userMoveInput.getUserColInput();
        CellStatus cellStatus;
        if (isRegularMove) {
            cellStatus = getOpponentLogicBoard().playMove(row, col);
        } else {
            placeMine(row, col);
            cellStatus = MINE_PLACED;
        }
        return handleCellStatus(cellStatus, row, col);
    }

    // **************************************************** //
    // Returns game statistics
    // **************************************************** //
    public String getGeneralStatistics() {
        long millis = System.currentTimeMillis() - startingTime;
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        int turns = players[0].getTurns() + players[1].getTurns();

        String result = ("Played Turns: " + turns + System.lineSeparator() +
                "Total Time: " + time);
        if (isPlayerWon) {
            result += System.lineSeparator() + "The Winner: " + players[currentPlayerIndex].getName();
        }
        return result;
    }

    public String getStatistics() {
        return players[currentPlayerIndex].sumUp();
    }

    public boolean isPlayerWon() {
        return isPlayerWon;
    }

    public void setPlayerWon(boolean playerWon) {
        isPlayerWon = playerWon;
    }

    public void resetGame() throws XmlContentException {
        isGameOn = false;
        isPlayerWon = false;
    }

    // **************************************************** //
    // Quit the match
    // **************************************************** //
    public String quitMatch() {
        isGameOn = false;
        players[currentPlayerIndex].setTurns(players[currentPlayerIndex].getTurns() + 1); // Quiting counts as a turn
        gameSteps.add(new GameStep(QUIT_MATCH, UNINITIALIZED, UNINITIALIZED, players[currentPlayerIndex], players[opponentPlayerIndex]));
        return getFinishGameString(" quit... ");
    }

    public String playerWonMatchMessage() {
        isGameOn = false;
        players[currentPlayerIndex].setTurns(players[currentPlayerIndex].getTurns() + 1);
        return "-------------------- GAME OVER --------------------" + System.lineSeparator() +
                getCurrentPlayerName() + " has WON the game!" + System.lineSeparator();
    }

    private String getFinishGameString(String message) {
        return "-------------------- GAME OVER --------------------" + System.lineSeparator() +
                "~~~~ " + players[currentPlayerIndex].getName() + message + players[opponentPlayerIndex].getName() +
                " WON THE GAME! ~~~~";
    }

    public String getSumUp() {
        return players[0].sumUp() + System.lineSeparator() + players[1].sumUp();
    }

    // **************************************************** //
    // Indicates that the user ask to shut down the game
    // **************************************************** //
    public void exitGame() {
        isActive = false;
    }

    // **************************************************** //
    // Indicates that the user ask to shut down the game
    // **************************************************** //
    public boolean hasMines() {
        return players[currentPlayerIndex].getMines() > 0;
    }

    // **************************************************** //
    // Returns current player score
    // **************************************************** //
    public int getCurrentPlayerScore() {
        return players[currentPlayerIndex].getScore();
    }

    public int getOpponentScore() {
        return players[opponentPlayerIndex].getScore();
    }


    public char[][] getBoardById(int id) {
        char[][] result = null; // In order to prevent bugs.
        if (id == 0) {
            result = players[0].getBoard().getAllieMode();
        } else if (id == 1) {
            result = players[1].getBoard().getAllieMode();
        }
        return result;
    }

    // **************************************************** //
    // Returns board size (original size + 1)
    // **************************************************** //
    public int getBoardSize() {
        return players[0].getBoard().getSize();
    }

    public SortedMap<Integer, Integer> getCurrentPlayerShipsTypesAndAmount() {
        return getShipTypesAndAmount(getCurrentPlayerLogicBoard().getBattleships());
    }

    public SortedMap<Integer, Integer> getOpponentPLayerShipsTypesAndAmount() {
        return getShipTypesAndAmount(getOpponentLogicBoard().getBattleships());
    }

    private SortedMap<Integer, Integer> getShipTypesAndAmount(List<Battleship> battleships) {
        SortedMap<Integer, Integer> map = new TreeMap<>();
        battleships.stream()
                .filter(BattleshipBase::isAlive)
                .forEach(ship -> {
                    Integer numOfShipType = 1;
                    if (map.containsKey(ship.getLength())) {
                        numOfShipType = map.get(ship.getLength());
                        numOfShipType++;
                    }
                    map.put(ship.getLength(), numOfShipType);
                });

        return map;
    }

    // **************************************************** //
    // Cell Status handler
    // **************************************************** //
    private String handleCellStatus(CellStatus cellStatus, int row, int col) {
        String messageToReturn = null;
        int playerHelper = currentPlayerIndex; // for the GameStep
        switch (cellStatus) {
            case SHIP:
                messageToReturn = currentPlayerName + ": A HIT! You have another turn.";
                players[currentPlayerIndex].increaseHit();
                break;
            case SHIP_DOWN:
                messageToReturn = winnerCheck();
                break;
            case MINE:
                messageToReturn = "OH NO! You hit a mine!";
                messageToReturn += System.lineSeparator() + handleMine(row, col);
                cellStatus = mineHandler;
                players[currentPlayerIndex].increaseHit();
                switchTurn();
                break;
            case MINE_PLACED:
                messageToReturn = "The mine has been placed successfully.";
                switchTurn();
                break;
            case HIT:
            case MISS:
                messageToReturn = currentPlayerName + ": you already attacked this cell, please choose a different one.";
                break;
            case REGULAR:
                messageToReturn = currentPlayerName + ": You missed...";
                players[currentPlayerIndex].increaseMiss();
                switchTurn();
                break;
        }
        // Document step
        if (cellStatus != MISS && cellStatus != HIT && isGameOn) {
            int opponentHelper = (playerHelper + 1) % 2;
            gameSteps.add(new GameStep(cellStatus, row, col, players[playerHelper], players[opponentHelper]));
        }

        return messageToReturn;
    }

    // **************************************************** //
    // Handles mine hit
    // **************************************************** //
    private String handleMine(int row, int col) {
        String messageToReturn = null;
        CellStatus cellStatus = getCurrentPlayerLogicBoard().playMove(row, col);
        switch (cellStatus) {
            case REGULAR:
                messageToReturn = "Lucky you! Your parallel cell was empty.";
                mineHandler = MINE_HIT_REGULAR;
                break;
            case MISS:
            case HIT:
                messageToReturn = "HA! The parallel cell was already attacked.";
                mineHandler = MINE_HIT_ALREADY;
                break;
            case SHIP:
                messageToReturn = "One of your battleships got hit!";
                mineHandler = MINE_HIT_SHIP;
                break;
            case SHIP_DOWN:
                messageToReturn = "One of your battleships destroyed!";
                mineHandler = MINE_HIT_DESTROYED;
                players[opponentPlayerIndex].increaseScore(players[currentPlayerIndex].getBoard().getLastDestroyedScore());
                if (!getCurrentPlayerLogicBoard().isThereAliveShip()) {
                    if (isGameOn) {
                        isPlayerWon = true;
                    }
                    messageToReturn += System.lineSeparator() + getVictoryMsg(opponentPlayerIndex);
                }
                break;
            case MINE:
                messageToReturn = "What a coincidence! You got a mine on this cell too.";
                getCurrentPlayerLogicBoard().drawMineAsMiss(row, col);
                mineHandler = MINE_HIT_MINE;
                break;
        }
        return messageToReturn;
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

    private String winnerCheck() {
        String messageToReturn;
        players[currentPlayerIndex].increaseHit();
        players[currentPlayerIndex].increaseScore(players[opponentPlayerIndex].getBoard().getLastDestroyedScore());
        if (getOpponentLogicBoard().isThereAliveShip()) {
            messageToReturn = "BOOM! You destroyed one of the opponent's ships! You have another turn.";
        } else {
            if (isGameOn) {
                isPlayerWon = true;
            }
            messageToReturn = getVictoryMsg(currentPlayerIndex);
        }
        return messageToReturn;
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
            Player player = new Player(String.format("Player%d", playerIndex + 1),
                    board,
                    Integer.parseInt(xmlContent.getMine().getAmount())); //
            // Sets
            // player board
            players[playerIndex] = player; // Inserts player to players array
            playerIndex++;
        }
        currentPlayerName = players[0].getName();
    }

    // **************************************************** //
    // Place a mine
    // **************************************************** //
    private void placeMine(int row, int col) throws XmlContentException {
        if (players[currentPlayerIndex].getMines() == 0) {
            throw new XmlContentException(ErrorMessages.MINE_LIMIT); // Mines amount over
        }
        players[currentPlayerIndex].getBoard().drawMine(row, col);
        players[currentPlayerIndex].decreaseMine();
    }

    // **************************************************** //
    // Returns victory message
    // **************************************************** //
    private String getVictoryMsg(int winnerIndex) {
        return "-------------------- GAME OVER --------------------" + System.lineSeparator() +
                "~~~~~~~~~~~~~~ " + players[winnerIndex].getName() + " WON THE GAME! ~~~~~~~~~~~~~~";
    }

    // **************************************************** //
    // Returns boards of next step
    // **************************************************** //
    public boolean shouldDisableUndo() {
        return (gameSteps.size() == 1);
    }

    // **************************************************** //
    // Returns boards of next step
    // **************************************************** //
    public ReplayInfo activateUndoRedo() {
        gameStepIndex = gameSteps.size() - 1;
        GameStep stepToShow = gameSteps.get(gameStepIndex);
        return stepToShow.getReplayInfo();
    }

    // **************************************************** //
    // Returns boards of next step
    // **************************************************** //
    public ReplayInfo getNextStep() {
        gameStepIndex++;
        GameStep stepToShow = gameSteps.get(gameStepIndex);
        return stepToShow.getReplayInfo();
    }

    // **************************************************** //
    // Returns boards of previous step
    // **************************************************** //
    public ReplayInfo getPrevStep() {
        gameStepIndex--;
        GameStep stepToShow = gameSteps.get(gameStepIndex);
        return stepToShow.getReplayInfo();
    }

    // **************************************************** //
    // Returns if the current step has a previous one
    // **************************************************** //
    public boolean hasPrevStep() {
        return (gameStepIndex != 0);
    }

    // **************************************************** //
    // Returns if the current step has a next one
    // **************************************************** //
    public boolean hasNextStep() {
        return (gameStepIndex < gameSteps.size() - 1);
    }

    // **************************************************** //
    // Returns current player number of mines
    // **************************************************** //
    public int getCurrentPlayerNumberOfMines() {
        return players[currentPlayerIndex].getMines();
    }

    public char[][] getBoardByIndex(int index) {
        return players[index].getBoard().getAllieMode();
    }

    // **************************************************** //
    // Performs board redo
    // **************************************************** //
//    private void playPrevious() {
//        int pointsToDecrease = 0;
//        GameStep stepToRedo = gameSteps.get(gameStepIndex);
//        replayArrangePlayers(stepToRedo);
//        if (stepToRedo.getCellStatus() == MINE_PLACED) {
//            players[currentPlayerIndex].getBoard().redoMove(stepToRedo); // Revert move on board
//        } else {
//            pointsToDecrease = players[opponentPlayerIndex].getBoard().redoMove(stepToRedo); // Revert move on board
//        }
//
//        switch (stepToRedo.getCellStatus()) {
//            case SHIP:
//                players[currentPlayerIndex].decreaseHit();
//                break;
//            case SHIP_DOWN:
//                players[currentPlayerIndex].decreaseHit();
//                players[opponentPlayerIndex].decreaseScore(pointsToDecrease);
//                break;
//            // Mines different cases handlers
//            case MINE_HIT_REGULAR:
//                players[currentPlayerIndex].getBoard().redoMove(new GameStep(REGULAR, UNINITIALIZED, stepToRedo.getRow(), stepToRedo.getCol()));
//                players[currentPlayerIndex].decreaseHit();
//                break;
//            case MINE_HIT_SHIP:
//                players[currentPlayerIndex].getBoard().redoMove(new GameStep(SHIP, UNINITIALIZED, stepToRedo.getRow(), stepToRedo.getCol()));
//                players[currentPlayerIndex].decreaseHit();
//                break;
//            case MINE_HIT_DESTROYED:
//                pointsToDecrease = players[opponentPlayerIndex].getBoard().redoMove(new GameStep(SHIP, UNINITIALIZED, stepToRedo.getRow(), stepToRedo.getCol()));
//                players[currentPlayerIndex].decreaseScore(pointsToDecrease);
//                players[currentPlayerIndex].decreaseHit();
//                break;
//            case MINE_HIT_ALREADY:
//                players[currentPlayerIndex].decreaseHit();
//                break;
//            case MINE_HIT_MINE:
//                players[currentPlayerIndex].getBoard().redoMove(new GameStep(MINE, UNINITIALIZED, stepToRedo.getRow(), stepToRedo.getCol()));
//                players[currentPlayerIndex].decreaseHit();
//                break;
//            // *** END of mines handlers
//            case MINE_PLACED:
//                break;
//            case REGULAR:
//                players[opponentPlayerIndex].decreaseMiss();
//                break;
//        }
//    }
//
//    private void replayArrangePlayers(GameStep gamestep) {
//        currentPlayerIndex = gamestep.getPlayerId();
//        opponentPlayerIndex = (gamestep.getPlayerId() + 1) % 2;
//    }
//
//    private void documentStep() {
//
//    }
}
