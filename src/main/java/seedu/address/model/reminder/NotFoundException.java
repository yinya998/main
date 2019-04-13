package seedu.address.model.reminder;

/**
 * Signals that the given data is not found in the reminder list, aka has some errors.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Reminder not found");
    }

}

