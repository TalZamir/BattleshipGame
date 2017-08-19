package ui.verifiers;

/**
 * Created by barakm on 15/08/2017
 */
public class UserMoveInputVerifier {

    public String checkInput(String input, int boardSize) {
        int userInput = -1;
        try {
            userInput = Integer.parseInt(input);
        } catch (Exception exeption) {
            return "Invalid input!";
        }

        if (userInput < 1 || userInput >= boardSize) {
            return "The given input is out of range!";
        }
        return null;
    }

/*
        public boolean isUserInputOk(UserMoveInput userMoveInput, int boardSize, ErrorCollector errorCollector) {
        boolean isOK = true;

        if (userMoveInput.getUserRowInput() < 1 || userMoveInput.getUserRowInput() > boardSize) {
            errorCollector.addMessage("Row must be in range");
            isOK = false;
        }

        //        if (userMoveInput.getUserColInput().equals("")) {
        //            errorCollector.addMessage("Input cannot be empty");
        //            isOK = false;
        //        } else
        //            if (userMoveInput.getUserColInput().length() > 1) {
        //            isOK = false;
        //            errorCollector.addMessage("Column must be a character");
        //        } else if (userMoveInput.getUserColInput().charAt(0) - 'A' + 1 < 1
        //                || userMoveInput.getUserColInput().charAt(0) - 'A' > boardSize - 1) {
        //            errorCollector.addMessage("Column must be in range");
        //            isOK = false;
        //        }

        if (userMoveInput.getUserColInput() < 1
                || userMoveInput.getUserColInput() > boardSize) {
            errorCollector.addMessage("Column must be in range");
            isOK = false;
        }

        return isOK;
    }
*/
}
