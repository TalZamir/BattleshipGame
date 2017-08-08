package ui;

import logic.TheGame;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public final class BattleshipApp {

    private BattleshipApp() {
    }

    public static void main(String[] args) throws JAXBException, FileNotFoundException {





        TheGame gameManager = new TheGame();
        printMenu();
    }

    // **************************************************** //
    // Prints main menu
    // **************************************************** //
    private static void printMenu() {
        System.out.println("************* MAIN MENU *************");
        System.out.println("1. Load XML File");
    }
}
