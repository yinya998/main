package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_RELATED_EVENT_INDEX;
import static seedu.address.logic.parser.DeleteRState.EVNET_INDEX;
import static seedu.address.logic.parser.DeleteRState.REMINDER_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteRCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteECommand object
 */
public class DeleteRCommandParser implements Parser<DeleteRCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteECommand
     * and returns an DeleteECommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            //System.out.println("parser checking, "+ trimmedArgs);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_RELATED_EVENT_INDEX, PREFIX_REMINDER_INDEX);

        Index eventIndex;
        Index reminderIndex;

        if (argMultimap.getValue(PREFIX_REMINDER_RELATED_EVENT_INDEX).isPresent()) {
            eventIndex = ParserUtilForReminder.parseIndex
                    (argMultimap.getValue(PREFIX_REMINDER_RELATED_EVENT_INDEX).get());
            return new DeleteRCommand(eventIndex, EVNET_INDEX);
        }

        if (argMultimap.getValue(PREFIX_REMINDER_INDEX).isPresent()) {
            reminderIndex = ParserUtilForReminder.parseIndex(argMultimap.getValue(PREFIX_REMINDER_INDEX).get());
            return new DeleteRCommand(reminderIndex, REMINDER_INDEX);
        }
        throw new ParseException("DeleteR field is not correct. Must fill one.");
    }



}
