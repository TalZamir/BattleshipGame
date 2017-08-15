import org.junit.jupiter.api.Test;
import ui.UserMoveInput;
import ui.verifiers.ErrorCollector;
import ui.verifiers.UserMoveInputVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by barakm on 15/08/2017
 */
public class UserMoveInputTests {

    private final ErrorCollector errorCollector = new ErrorCollector();
    private final UserMoveInputVerifier userMoveInputVerifier = new UserMoveInputVerifier();

    @Test
    public void emptyInput() {
        boolean actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(1, ""), 5, errorCollector);

        assertEquals(false, actual);
    }

    @Test
    public void correctInput() {
        boolean actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(1, "E"), 5, errorCollector);

        assertEquals(true, actual);
    }

    @Test
    public void correctInputWithLowerCase() {
        boolean actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(5, "b"), 5, errorCollector);

        assertEquals(true, actual);
    }

    @Test
    public void rowIsOutOfRange() {
        boolean actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(6, "B"), 5, errorCollector);
        assertEquals(false, actual);

        actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(0, "B"), 5, errorCollector);
        assertEquals(false, actual);
    }

    @Test
    public void colIsOutOfRange() {
        boolean actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(2, "W"), 5, errorCollector);

        assertEquals(false, actual);
    }

    @Test
    public void strangeIsOutOfRange() {
        boolean actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(2, "W4"), 5, errorCollector);
        assertEquals(false, actual);

        actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(321, "eew"), 5, errorCollector);
        assertEquals(false, actual);
    }
}
