package seedu.address.model.reminder;


public class DuplicateReminderException extends Exception {

    public static final String MESSAGE = "Operation would result in duplicate reminder";

    public DuplicateReminderException() {
        super(MESSAGE);
    }
}
