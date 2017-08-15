package ui.verifiers;

import ui.UserMoveInput;

/**
 * Created by barakm on 15/08/2017
 */
public class UserMoveInputVerifier {

    public boolean isUserInputOk(UserMoveInput userMoveInput, int boardSize, ErrorCollector errorCollector) {
        if (userMoveInput.getUserColInput().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
