package seedu.address.model.reminder;

/**
 * Signals that some given data does not fulfill some constraints.
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
