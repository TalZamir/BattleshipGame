package ui;

/**
 * Created by barakm on 15/08/2017
 */
public class UserMoveInput {

    private final int userRowInput;
    private final int userColInput;

    public UserMoveInput(int userRowInput, int userColInput) {
        this.userRowInput = userRowInput;
        this.userColInput = userColInput;
    }

    public int getUserRowInput() {
        return userRowInput;
    }

    public int getUserColInput() {
        return userColInput;
    }

    //    public int getUserColInputForBoard() {
    //        return userColInput.charAt(0) - 'A' + 1;
    //    }

    //    public int getUserRowInputForBoard() {
    //        return getUserRowInput();
    //    }
}
