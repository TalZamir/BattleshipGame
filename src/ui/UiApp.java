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
                menuLoadXml();
                break;
            case START_GAME:
                menuStartGame();
                break;
            case STATUS:
                menuDisplayStatus();
                break;
            case PLAY_MOVE:
                menuPlayMove();
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
    private void menuPlayMove() {
        if (theGame.isGameOn()) {
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

            if (theGame.isPlayerWon()) {
                System.out.println(theGame.getStatistics());
                System.out.println("-  The game will restart now...");
                System.out.println("---------------------------------------------------");
                theGame.resetGame();
            }
        } else {
            System.out.println("You must start a new game before performing a move.");
        }
    }

    private UserMoveInput readUserMoveInput() {
        Scanner reader = new Scanner(System.in);
        String userRowInput, userColInput;
        do {
            System.out.print("Row: ");
            userRowInput = reader.next();
        }
        while (!isInputOk(userRowInput));

        do {
            System.out.print("Column: ");
            userColInput = reader.next();
        }
        while (!isInputOk(userColInput));

        return new UserMoveInput(Integer.parseInt(userRowInput), Integer.parseInt(userColInput));
    }

    private boolean isInputOk(String input) {
        boolean isOk = true;
        if (input.equals("")) {
            isOk = false;
        }

        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                isOk = false;
                break;
            }
        }

        if (!isOk) {
            System.out.println("Invalid input. Must be a number");
        }

        return isOk;
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
    private void menuLoadXml() {
        if (!theGame.isGameOn()) {
            System.out.println("Please enter a path of a XML file:");
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
            System.out.println("Sorry, but you can't load XML while game is on.");
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
            System.out.println("~~ It's " + theGame.getCurrentPlayerName() + " turn ~~");
            System.out.println("Self Board:");
            assistant.printBoard(theGame.getCurrentPlayerBoardToPrint());
            System.out.println("Game Board:");
            assistant.printBoard(theGame.getOpponentBoardToPrint());
        } else {
            System.out.println("You must start a game in order to display a status.");
        }
    }

    // **************************************************** //
    // Menu item: shows game statistics
    // **************************************************** //
    //TODO: Implement
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
    //TODO: Implement
    private void menuQuit() {
        if (theGame.isGameOn()) {
            System.out.println(theGame.quitMatch());
            System.out.println(theGame.getStatistics());
            System.out.println("-  The game will restart now...");
            System.out.println("---------------------------------------------------");
        } else {
            System.out.println("You must start a game in order to quit.");
        }
    }

    // **************************************************** //
    // Menu item: Place a mine
    // **************************************************** //
    private void menuPlaceMine() {
        if(theGame.isGameOn()) {
            System.out.println("MINE");
        } else {
            System.out.println("You must start a game in order to place a mine.");
        }
    }

    // **************************************************** //
    // Menu item: Exit game
    // **************************************************** //
    private void menuExitGame() {
        if(!theGame.isGameOn()) {
            theGame.exitGame();
            System.out.println("Thank you for playing! Exiting...");
        } else {
            System.out.println("You must finish the current match before exiting the game.");
        }
    }
}
