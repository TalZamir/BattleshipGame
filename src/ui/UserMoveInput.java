package ui;

/**
 * Created by barakm on 15/08/2017
 */
public class UserMoveInput {

    private final int userRowInput;
    private final String userColInput;

    public UserMoveInput(int userRowInput, String userColInput) {
        this.userRowInput = userRowInput;
        this.userColInput = userColInput.toUpperCase();
    }

    public int getUserRowInput() {
        return userRowInput;
    }

    public String getUserColInput() {
        return userColInput;
    }
}
