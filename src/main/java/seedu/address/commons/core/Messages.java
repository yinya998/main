package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX = "The reminder index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1d persons listed:\n Exact Search:"
            + "\n %s\n Fuzzy Search:\n%s\nWildcard Search:\n%s";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed";
    public static final String MESSAGE_WRONG_VIEW = "Window is in wrong view";
    public static final String MESSAGE_RETRY_IN_PERSONS_VIEW = "Bringing you back to persons view."
            + " Please re-enter command";
    public static final String MESSAGE_RETRY_IN_EVENTS_VIEW = "Bringing you back to events view. "
            + "Please re-enter command";
}
