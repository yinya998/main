package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NotFoundExceptionTest {
    @Test
    public void createException_toString_checkCorrectness() throws Exception {
        Exception exception = new NotFoundException();
        assertEquals("Reminder not found", exception.getMessage());
    }
}
