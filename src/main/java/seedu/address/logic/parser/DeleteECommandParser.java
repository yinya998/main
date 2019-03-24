package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteECommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteECommand object
 */
public class DeleteECommandParser implements Parser<DeleteECommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteECommand
     * and returns an DeleteECommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteECommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtilForEvent.parseIndex(args);
            return new DeleteECommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteECommand.MESSAGE_USAGE), pe);
        }
    }

}
