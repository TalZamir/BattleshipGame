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
        boolean actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(1, "B"), 5, errorCollector);

        assertEquals(true, actual);
    }

    @Test
    public void rowIsOutOfRange() {
        boolean actual = userMoveInputVerifier.isUserInputOk(new UserMoveInput(6, "B"), 5, errorCollector);

        assertEquals(false, actual);
    }
}
