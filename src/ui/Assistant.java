package ui;

/**
 * Created by xozh4v on 8/10/2017.
 */
class Assistant {

    private static final String SPACE = "   ";
    private static final int SEPARATOR = 0;
    private static final int MENU_START = 1;
    private static final int MENU_END = 6;

    void printPlayMoveMassage(int boardSize) {
        System.out.print(String.format("Enter row and column index.\n" +
                                               "Row should be an integer type between %d to %d\n" +
                                               "and column should be an character type between %s to %s:\n",
                                       1,
                                       boardSize,
                                       "A",
                                       String.valueOf(boardSize + 'A'))
                               .toUpperCase());
    }

    // **************************************************** //
    // Prints main menu
    // **************************************************** //
    void printMenu() {
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
    void printBoard(char[][] board) {
        // Columns row
        printRowIndex(SEPARATOR);
        for (int i = 1; i < board.length; i++) {
            printWithSpace(i);
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
        printNewLine();
    }

    int getMenuStart() {
        return MENU_START;
    }

    int getMenuEnd() {
        return MENU_END;
    }

    void printPlayerName(String currentPlayerName) {
        System.out.println(currentPlayerName);
        printNewLine();
    }

    void printYourBoardText() {
        System.out.println("Your Board:");
    }

    void printOpponentBoardText() {
        System.out.println("Opponent Board:");
    }

    // **************************************************** //
    // Prints sign with space
    // **************************************************** //
    private void printWithSpace(Object sign) {
        System.out.print(sign + SPACE);
    }

    // **************************************************** //
    // Prints row index with proper alignment
    // **************************************************** //
    private void printRowIndex(int row) {
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

    private void printNewLine() {
        System.out.println();
    }
}
