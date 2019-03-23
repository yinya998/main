package seedu.address.model.reminder;

/**
 * Signals that the given data is not found in the reminder list, aka has some errors.
 */
public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
