package ui;

import logic.TheGame;
import logic.exceptions.XmlContentException;
import ui.enums.MenuItem;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UIApp {

    private final String INVALID_INPUT = "Invalid input! Please try again";
    private TheGame theGame;
    private UIPrinter uiPrinter;

    public void start() throws JAXBException, FileNotFoundException {
        uiPrinter = new UIPrinter();
        theGame = new TheGame();
        while (theGame.isActive()) {
            try {
                theGame.init();
                uiPrinter.printMenu();
                doIteration();
                uiPrinter.printBoard(theGame.getCurrentPlayerShipBoard());
            } catch (XmlContentException exception) {
                System.out.println("GAME ERROR: " + exception.getMessage());
            }
        }
    }

    // **************************************************** //
    // Performs game iteration
    // **************************************************** //
    private void doIteration() {
        switch (readUserInput()) {
            case LOAD_XML:
                System.out.println("Loading...");
                break;
            case START_GAME:
                System.out.println("Starting...");
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
    // Reads input from user
    // **************************************************** //
    private MenuItem readUserInput() {
        MenuItem input = MenuItem.UNINITIALIZED;
        while (input == MenuItem.UNINITIALIZED) {
            try {
                Scanner reader = new Scanner(System.in);  // Reading from System.in
                int userInput = reader.nextInt();
                if (userInput >= uiPrinter.getMenuStart() && userInput <= uiPrinter.getMenuEnd()) {
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
