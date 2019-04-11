package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddRCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Interval;
import seedu.address.model.reminder.Unit;


/**
 * Parses input arguments and creates a new AddRCommand object
 */
public class AddRCommandParser implements Parser<AddRCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRCommand
     * and returns an AddRCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_INDEX, PREFIX_INTERVAL, PREFIX_UNIT);

        Index index;
        Unit unit;
        Interval interval;
        try {
            index = ParserUtilForReminder.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRCommand.MESSAGE_USAGE), pe);
        }

        try {
            unit = ParserUtilForReminder.parseUnit(argMultimap.getValue(PREFIX_UNIT).get());
        } catch (java.util.NoSuchElementException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRCommand.MESSAGE_USAGE), pe);
        }

        try {
            interval = ParserUtilForReminder.parseIntervalAndUnit(argMultimap.getValue(PREFIX_INTERVAL).get(),
                    argMultimap.getValue(PREFIX_UNIT).get());
        } catch (java.util.NoSuchElementException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRCommand.MESSAGE_USAGE), pe);
        }

        return new AddRCommand(index, interval, unit);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
