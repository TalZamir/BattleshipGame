package ui.console;

import logic.TheGame;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;
import ui.UserMoveInput;
import ui.enums.MenuItem;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.UserMoveInputVerifier;
import ui.verifiers.XmlFileVerifier;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static ui.enums.MenuItem.MINE;
import static ui.enums.MenuItem.PLAY_MOVE;

public class UiApp {

    private static final String INVALID_INPUT = "Invalid input! Please try again";
    private TheGame theGame;
    private Assistant assistant;
    private static final String USER_INTRO = null;

    // **************************************************** //
    // Starts Battleship Game UI
    // **************************************************** //
    public void start() throws JAXBException, FileNotFoundException {
        assistant = new Assistant();
        theGame = new TheGame();
        while (theGame.isActive()) {
            try {
                assistant.printMenu();
                doIteration();
            } catch (XmlContentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    // **************************************************** //
    // Performs game iteration
    // **************************************************** //
    private void doIteration() throws XmlContentException {
        switch (readUserMenuInput()) {
            case LOAD_XML:
                menuLoadXml();
                break;
            case START_GAME:
                menuStartGame();
                break;
            case STATUS:
                menuDisplayStatus();
                break;
            case PLAY_MOVE:
                menuPlayMove(PLAY_MOVE);
                break;
            case STATISTICS:
                menuShowStatistics();
                break;
            case QUIT:
                menuQuit();
                break;
            case MINE:
                menuPlaceMine();
                break;
            case EXIT:
                menuExitGame();
                break;
            case UNINITIALIZED:
                System.out.println("Please enter one of the following menu number");
                break;
        }
    }

    // **************************************************** //
    // Menu item: plays player move
    // **************************************************** //
    private void menuPlayMove(MenuItem moveType) throws XmlContentException {
        if (theGame.isGameOn()) {
            ErrorCollector errorCollector = new ErrorCollector();
            UserMoveInputVerifier userMoveInputVerifier = new UserMoveInputVerifier();
            int boardSize = theGame.getCurrentPlayerBoardToPrint().length;
            // assistant.printPlayMoveMassage(boardSize);
            printIntro("Please choose your desired location:");
            UserMoveInput userMoveInput = readUserMoveInput();

            System.out.println(theGame.playMove(userMoveInput, moveType == PLAY_MOVE));

            if (theGame.isPlayerWon()) {
                finishMatch();
            } else {
                assistant.printTurnMsg(theGame); // Indicates who's turn is it
            }
        } else {
            System.out.println("You must start a new game before performing a move.");
        }
    }

    private UserMoveInput readUserMoveInput() {
        Scanner reader = new Scanner(System.in);
        String userRowInput, userColInput;
        String userMsg = "Please enter a row number: ";
        int boardSize = theGame.getCurrentPlayerBoardToPrint().length;
        UserMoveInputVerifier userMoveInputVerifier = new UserMoveInputVerifier();
        do {
            System.out.println(userMsg);
            userRowInput = reader.next();
        }
        while ((userMsg = userMoveInputVerifier.checkInput(userRowInput, boardSize)) != null);

        userMsg = "Please enter a column number: ";

        do {
            System.out.println(userMsg);
            userColInput = reader.next();
        }
        while ((userMsg = userMoveInputVerifier.checkInput(userColInput, boardSize)) != null);

        return new UserMoveInput(Integer.parseInt(userRowInput), Integer.parseInt(userColInput));
    }

    // **************************************************** //
    // Menu item: Load XML file
    // **************************************************** //
    private void menuLoadXml() {
        if (!theGame.isGameOn()) {
            System.out.println("Please enter a path of an XML file:");
            ErrorCollector errorCollector = new ErrorCollector();
            Scanner reader = new Scanner(System.in);
            String filePath = reader.nextLine();
            IInputVerifier inputVerifier = new XmlFileVerifier();

            try {
                if (!inputVerifier.isFileOk(filePath, errorCollector) || !theGame.loadFile(filePath, errorCollector)) {
                    errorCollector.getMessages().forEach(System.out::println);
                } else {
                    System.out.println("File has loaded. You are ready to play :)");
                }
            } catch (XmlContentException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            System.out.println(ErrorMessages.GAME_IS_ALREADY_ON.message());
        }
    }

    // **************************************************** //
    // Menu item: Start game
    // **************************************************** //
    private void menuStartGame() throws XmlContentException {
        if (!theGame.isFileLoaded()) {
            // No XML file
            System.out.println("You must load a XML file in order to start a game.");
        } else if (theGame.isGameOn()) {
            // The game is already started
            System.out.println("A game is already running.");
        } else {
            // XML loaded and game is not began yet
            theGame.startGame();
            System.out.println("The game has been started successfully!");
            printIntro(USER_INTRO);
        }
    }

    // **************************************************** //
    // Reads input from user
    // **************************************************** //
    private MenuItem readUserMenuInput() {
        MenuItem input = MenuItem.UNINITIALIZED;
        while (input == MenuItem.UNINITIALIZED) {
            try {
                Scanner reader = new Scanner(System.in);
                int userInput = reader.nextInt();
                if (userInput >= assistant.getMenuStart() && userInput <= assistant.getMenuEnd()) {
                    input = MenuItem.values()[userInput - 1];
                } else {
                    System.out.println(INVALID_INPUT);
                }
            } catch (Exception exception) {
                System.out.println(INVALID_INPUT);
            }
        }

        return input;
    }

    // **************************************************** //
    // Menu item: displays game status
    // **************************************************** //
    private void menuDisplayStatus() {
        if (theGame.isGameOn()) {
            assistant.printTurnIntro(theGame, USER_INTRO);
        } else {
            System.out.println("You must start a game in order to display a status.");
        }
    }

    // **************************************************** //
    // Menu item: shows game statistics
    // **************************************************** //
    private void menuShowStatistics() {
        if (theGame.isGameOn()) {
            System.out.println(theGame.getStatistics());
        } else {
            System.out.println("You must start a game in order to show game statistics.");
        }
    }

    // **************************************************** //
    // Menu item: Quits from playing
    // **************************************************** //
    private void menuQuit() throws XmlContentException {
        if (theGame.isGameOn()) {
            System.out.println(theGame.quitMatch());
            finishMatch();
        } else {
            System.out.println("You must start a game in order to quit.");
        }
    }

    // **************************************************** //
    // Menu item: Load XML file
    // **************************************************** //
    private void menuPlaceMine() throws XmlContentException {
        if (theGame.isGameOn()) {
            if (theGame.hasMines()) {
                System.out.println("Please choose a location to place a mine:");
                menuPlayMove(MINE);
            } else {
                System.out.println("You ran out of mines!");
            }
        } else {
            System.out.println("You must start a game before placing a mine.");
        }
    }

    // **************************************************** //
    // Menu item: Exit game
    // **************************************************** //
    private void menuExitGame() {
        if (!theGame.isGameOn()) {
            theGame.exitGame();
            System.out.println("Thank you for playing! Exiting...");
        } else {
            System.out.println("You must finish the current match before exiting the game.");
        }
    }

    // **************************************************** //
    // Prints turn intro text
    // **************************************************** //
    private void printIntro(String intro) {
        assistant.printTurnIntro(theGame, intro);
    }

    private void finishMatch() throws XmlContentException {
        System.out.println(theGame.getStatistics());
        System.out.println("Player1 Board: ");
        assistant.printBoard(theGame.getBoardById(0));
        System.out.println("Player2 Board: ");
        assistant.printBoard(theGame.getBoardById(1));
        System.out.println("-  The game will restart now...");
        System.out.println("---------------------------------------------------");
        theGame.resetGame();
    }
}
