package ui.verifiers;

import ui.UserMoveInput;

/**
 * Created by barakm on 15/08/2017
 */
public class UserMoveInputVerifier {

    public boolean isUserInputOk(UserMoveInput userMoveInput, int boardSize, ErrorCollector errorCollector) {
        boolean isOK = true;

        if (userMoveInput.getUserRowInput() < 1 || userMoveInput.getUserRowInput() > boardSize) {
            errorCollector.addMessage("Row must be in range");
            isOK = false;
        }

        if (userMoveInput.getUserColInput().equals("")) {
            errorCollector.addMessage("Input cannot be empty");
            isOK = false;
        } else if (userMoveInput.getUserColInput().length() > 1) {
            isOK = false;
            errorCollector.addMessage("Column must be a character");
        } else if (userMoveInput.getUserColInput().charAt(0) - 'A' < 1
                || userMoveInput.getUserColInput().charAt(0) - 'A' > boardSize) {
            errorCollector.addMessage("Column must be in range");
            isOK = false;
        }

        return isOK;
    }
}
