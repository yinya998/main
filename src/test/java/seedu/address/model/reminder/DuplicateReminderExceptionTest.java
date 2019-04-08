package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DuplicateReminderExceptionTest {
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void createException_getMessage_checkCorrectness() throws Exception {
        thrown.expect(NotFoundException.class);
        Exception exception = new DuplicateReminderException();
        assertEquals("Operation would result in duplicate reminders", exception.getMessage());
    }
}
