package seedu.address.model.reminder;

/**
 * Signals that the operation will result in duplicate reminder in reminder list.
 * Reminders are considered duplicates if they have the same identity.
 */
public class DuplicateReminderException extends Exception {

    public static final String MESSAGE = "Operation would result in duplicate reminder";

    public DuplicateReminderException() {
        super(MESSAGE);
    }
}
