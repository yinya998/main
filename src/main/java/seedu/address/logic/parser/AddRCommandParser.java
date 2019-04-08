package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddRCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.*;
import seedu.address.model.reminder.*;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;


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

//        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_INDEX, PREFIX_INTERVAL, PREFIX_UNIT)
//                || !argMultimap.getPreamble().isEmpty()) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRCommand.MESSAGE_USAGE));
//        }
        Index index;

        try {
            index = ParserUtilForReminder.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRCommand.MESSAGE_USAGE), pe);
        }

        Unit unit = ParserUtilForReminder.parseUnit(argMultimap.getValue(PREFIX_UNIT).get());
        Interval interval = ParserUtilForReminder.parseIntervalAndUnit(argMultimap.getValue(PREFIX_INTERVAL).get(),argMultimap.getValue(PREFIX_UNIT).get() );
        //System.out.println("index is "+ index.getOneBased()+"unit is "+unit.toString()+"interval is "+interval.toString());

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
