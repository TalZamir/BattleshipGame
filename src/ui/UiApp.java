package ui;

import logic.TheGame;
import logic.exceptions.XmlContentException;
import ui.enums.MenuItem;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.UserMoveInputVerifier;
import ui.verifiers.XmlFileVerifier;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

class UiApp {

    private static final String INVALID_INPUT = "Invalid input! Please try again";
    private TheGame theGame;
    private Assistant assistant;

    // **************************************************** //
    // Starts Battleship Game UI
    // **************************************************** //
    void start() throws JAXBException, FileNotFoundException {
        assistant = new Assistant();
        theGame = new TheGame();
        while (theGame.isActive()) {
            try {
                assistant.printMenu();
                doIteration();
            } catch (XmlContentException exception) {
                System.out.println("GAME ERROR: " + exception.getMessage());
            }
        }
    }

    // **************************************************** //
    // Performs game iteration
    // **************************************************** //
    //TODO: TAL - I think it should appears once. doIteration should be menuStartGame method.
    private void doIteration() throws XmlContentException {
        switch (readUserMenuInput()) {
            case LOAD_XML:
                System.out.println("Enter an xml file path including the file name extension:");
                menuLoadXml();
                break;
            case START_GAME:
                menuStartGame();
                break;
            case STATUS:
                System.out.println("Look! a Status...");
                break;
            case PLAY_MOVE:
                menuPlayMove();
                break;
            case STATISTICS:
                System.out.println("Ho, Statistics...");
                break;
            case QUIT:
                System.out.println("Quiting...");
            case UNINITIALIZED:
                System.out.println("Please enter one of the following menu number");
                break;
        }

        if (theGame.isGameOn()) {
            printPlayerNameAndBoards();
        }
    }

    private void menuPlayMove() {
        ErrorCollector errorCollector = new ErrorCollector();
        UserMoveInputVerifier userMoveInputVerifier = new UserMoveInputVerifier();
        int boardSize = theGame.getCurrentPlayerBoardToPrint().length;
        assistant.printPlayMoveMassage(boardSize);
        printPlayerNameAndBoards();
        UserMoveInput userMoveInput = readUserMoveInput();

        if (!userMoveInputVerifier.isUserInputOk(userMoveInput, boardSize, errorCollector)) {
            errorCollector.getMessages().forEach(System.out::println);
        } else {
            System.out.println(theGame.playMove(userMoveInput));
        }
    }

    private UserMoveInput readUserMoveInput() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Row: ");
        int userRowInput = reader.nextInt();

        System.out.println("Column: ");
        String userColInput = reader.next();
        return new UserMoveInput(userRowInput, userColInput);
    }

    private void printPlayerNameAndBoards() {
        assistant.printPlayerName(theGame.getCurrentPlayerName() + " it's your turn");
        assistant.printYourBoardText();
        assistant.printBoard(theGame.getCurrentPlayerBoardToPrint());
        assistant.printOpponentBoardText();
        assistant.printBoard(theGame.getOpponentBoardToPrint());
    }

    // **************************************************** //
    // Menu item: Load XML file
    // **************************************************** //
    private boolean menuLoadXml() throws XmlContentException {
        ErrorCollector errorCollector = new ErrorCollector();
        Scanner reader = new Scanner(System.in);
        String filePath = reader.nextLine();
        IInputVerifier inputVerifier = new XmlFileVerifier();

        if (!inputVerifier.isFileOk(filePath, errorCollector) || !theGame.loadFile(filePath, errorCollector)) {
            errorCollector.getMessages().forEach(System.out::println);
            return false;
        }

        return true;
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
            theGame.init();
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
}
