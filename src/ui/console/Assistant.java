package ui.console;

import logic.TheGame;

/**
 * Created by xozh4v on 8/10/2017.
 */
class Assistant {

    private static final String SPACE = "   ";
    private static final int SEPARATOR = 0;
    private static final int MENU_START = 1;
    private static final int MENU_END = 8;

    // **************************************************** //
    // Prints game boards
    // **************************************************** //
    public String printBoard(char[][] board) {
        String result = "";
        // Columns row
        result += printRowIndex(SEPARATOR);
        for (int i = 1; i < board.length; i++) {
            result += printWithSpace(i);
        }

        result += (System.getProperty("line.separator"));
        // Actual board
        for (int row = 1; row < board.length; row++) {
            result += printRowIndex(row);
            for (int col = 1; col < board.length; col++) {
                result += printWithSpace(board[row][col]);
            }
            result += (System.getProperty("line.separator"));
        }
        result += (System.getProperty("line.separator"));
        return result;
    }

    void printPlayMoveMassage(int boardSize) {
        System.out.print(String.format("Enter row and column index.\n" +
                        "Row should be an integer type between %d to %d\n" +
                        "and column should be an character type between %s to %s\n\n",
                1,
                boardSize - 1,
                "A",
                (char) (boardSize - 2 + 'A')));
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
        System.out.println("7. Place a Mine");
        System.out.println("8. Exit Game");
        System.out.println("*************************************");
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

    void printTurnIntro(TheGame theGame, String intro) {
        if (intro == null) {
            printTurnMsg(theGame);
        } else {
            System.out.println(intro);
        }
        System.out.println("Personal Board:");
        printBoard(theGame.getCurrentPlayerBoardToPrint());
        System.out.println("Tracking Board:");
        printBoard(theGame.getOpponentBoardToPrint());
    }

    void printTurnMsg(TheGame theGame) {
        System.out.println("~~~~  " + theGame.getCurrentPlayerName() + " Turn (Score: " + theGame
                .getCurrentPlayerScore() + ")  ~~~~~");
    }

    // **************************************************** //
    // Prints sign with space
    // **************************************************** //
    private String printWithSpace(Object sign) {
        return (sign + SPACE);
    }

    // **************************************************** //
    // Prints row index with proper alignment
    // **************************************************** //
    private String printRowIndex(int row) {
        String space = SPACE;
        if (row < 10) {
            space += " ";
        }
        if (row != SEPARATOR) {
            return (row + space);
        } else {
            return ('-' + space);
        }
    }

    private void printNewLine() {
        System.out.println();
    }
}
