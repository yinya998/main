package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@code Command}.
 */
public class WrongViewException extends Exception {

    public WrongViewException(String message) {
        super(message);
    }

}
