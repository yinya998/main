package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddECommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddECommandParser implements Parser<AddECommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddECommand
     * and returns an AddECommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddECommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_START_TIME,
                        PREFIX_END_TIME, PREFIX_LABEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_START_TIME,
                PREFIX_END_TIME, PREFIX_LABEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddECommand.MESSAGE_USAGE));
        }

        Name name = ParserUtilForEvent.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Description description = ParserUtilForEvent.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Venue venue = ParserUtilForEvent.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());
        DateTime startTime = ParserUtilForEvent.parseDateTime(argMultimap.getValue(PREFIX_START_TIME).get());
        DateTime endTime = ParserUtilForEvent.parseDateTime(argMultimap.getValue(PREFIX_END_TIME).get());
        Label label = ParserUtilForEvent.parseLabel(argMultimap.getValue(PREFIX_LABEL).get());

        if (!startTime.isBefore(endTime)) {
            throw new ParseException("End time should not be earlier than start time");
        }
        Event event = new Event(name, description, venue, startTime, endTime, label);

        return new AddECommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
