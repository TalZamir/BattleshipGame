package ui;

import logic.Board;
import logic.TheGame;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public final class BattleshipApp {

    private BattleshipApp() {
    }

    private static final String SPACE = "   ";
    private static final int SEPARATOR = 0;

    public static void main(String[] args) throws JAXBException, FileNotFoundException {

        TheGame gameManager = new TheGame();
        Board board = new Board(12);
        printBoard(board.getAllieMode());
    }

    // **************************************************** //
    // Prints main menu
    // **************************************************** //
    private static void printMenu() {
        System.out.println("************* MAIN MENU *************");
        System.out.println("1. Load XML File");
    }

    private static void printBoard(char[][] board) {
        // Columns row
        printRowIndex(SEPARATOR);
        char letter = 'A';
        for (int i = 0; i < board.length; i++, letter++) {
            printWithSpace(letter);
        }
        System.out.print(System.getProperty("line.separator"));
        // Actual board
        int rowIndex = 1;
        for (char[] row : board) {
            printRowIndex(rowIndex);
            for (char sign : row) {
                printWithSpace(sign);
            }
            System.out.print(System.getProperty("line.separator"));
            rowIndex++;
        }
    }

    // **************************************************** //
    // Prints sign with space
    // **************************************************** //
    private static void printWithSpace(Object sign) {
        System.out.print(sign + SPACE);
    }

    // **************************************************** //
    // Prints row index with proper alignment
    // **************************************************** //
    private static void printRowIndex(int row) {
        String space = SPACE;
        if (row < 10) {
            space += " ";
        }
        if (row != SEPARATOR) {
            System.out.print(row + space);
        } else {
            System.out.print('-' + space);
        }
    }
}
