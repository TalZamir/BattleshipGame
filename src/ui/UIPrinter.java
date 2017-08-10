package ui;

import java.util.Scanner;

/**
 * Created by xozh4v on 8/10/2017.
 */
public class UIPrinter {

    private final String SPACE = "   ";
    private final int SEPARATOR = 0;
    private final int MENU_START = 1;
    private final int MENU_END = 6;


    // **************************************************** //
    // Prints main menu
    // **************************************************** //
    public void printMenu() {
        System.out.println("************* MAIN MENU *************");
        System.out.println("1. Load a XML File");
        System.out.println("2. Start New Game");
        System.out.println("3. Display Game Status");
        System.out.println("4. Play Move");
        System.out.println("5. Show Game Statistics");
        System.out.println("6. Quit Current Match");
        System.out.println("*************************************");
    }

    // **************************************************** //
    // Prints game boards
    // **************************************************** //
    public void printBoard(char[][] board) {
        // Columns row
        printRowIndex(SEPARATOR);
        char letter = 'A';
        for (int i = 1; i < board.length; i++, letter++) {
            printWithSpace(letter);
        }
        System.out.print(System.getProperty("line.separator"));
        // Actual board
        for (int row = 1; row < board.length; row++) {
            printRowIndex(row);
            for (int col = 1; col < board.length; col++) {
                printWithSpace(board[row][col]);
            }
            System.out.print(System.getProperty("line.separator"));
        }
    }

    // **************************************************** //
    // Prints sign with space
    // **************************************************** //
    public void printWithSpace(Object sign) {
        System.out.print(sign + SPACE);
    }

    // **************************************************** //
    // Prints row index with proper alignment
    // **************************************************** //
    public void printRowIndex(int row) {
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

    public int getMenuStart() {
        return MENU_START;
    }

    public int getMenuEnd() {
        return MENU_END;
    }
}
