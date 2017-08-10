package ui;

import logic.TheGame;
import logic.exceptions.XmlContentException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public final class BattleshipApp {

    private BattleshipApp() {
    }

    private static final String INVALID_INPUT = "Invalid input! Please try again";
    private static TheGame theGame;
    private static UIPrinter uiPrinter;

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        uiPrinter = new UIPrinter();
        theGame = new TheGame();
        while (theGame.isActive()) {
            try {
                theGame.init();
                uiPrinter.printMenu();
                readUserInput();
                uiPrinter.printBoard(theGame.getCurrentPlayerShipBoard());
            } catch (XmlContentException exception) {
                System.out.println("GAME ERROR: " + exception.getMessage());
            }
        }
    }

    // **************************************************** //
    // Performs game iteration
    // **************************************************** //
    private static void doIteration() {

    }

    // **************************************************** //
    // Reads input from user
    // **************************************************** //
    private static int readUserInput() {
        int input = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                Scanner reader = new Scanner(System.in);  // Reading from System.in
                input = reader.nextInt();
                if (input >= uiPrinter.getMenuStart() && input <= uiPrinter.getMenuEnd()) {
                    isValid = true;
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
