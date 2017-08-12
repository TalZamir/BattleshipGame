package ui;

import logic.TheGame;
import logic.exceptions.XmlContentException;
import ui.enums.MenuItem;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UiApp {

    private static final String INVALID_INPUT = "Invalid input! Please try again";
    private TheGame theGame;
    private Assistant assistant;

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
                assistant.printPlayerName(theGame.getCurrentPlayerName());
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
        switch (readUserInput()) {
            case LOAD_XML:
                menuLoadXml();
                break;
            case START_GAME:
                menuStartGame();
                break;
            case STATUS:
                System.out.println("Look! a Status...");
                break;
            case PLAY_MOVE:
                System.out.println("Playing...");
                break;
            case STATISTICS:
                System.out.println("Ho, Statistics...");
                break;
            case QUIT:
                System.out.println("Quiting...");
        }
    }

    // **************************************************** //
    // Menu item: Load XML file
    // **************************************************** //
    private void menuLoadXml() throws XmlContentException {
        if(theGame.isGameOn()) {
            System.out.println("You can't load a XML file while a game is already running.");
        } else {
            Scanner reader = new Scanner(System.in);
            theGame.loadFile(reader.nextLine());
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
            theGame.init();
            assistant.printBoard(theGame.getCurrentPlayerShipBoard());
        }
    }

    // **************************************************** //
    // Reads input from user
    // **************************************************** //
    private MenuItem readUserInput() {
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
