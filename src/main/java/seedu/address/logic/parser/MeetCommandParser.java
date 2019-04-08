package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import seedu.address.logic.commands.MeetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;

/**
 * Parses input arguments and creates a new MeetCommand object.
 * @author yonggqiii
 */
public class MeetCommandParser implements Parser<MeetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MeetCommand
     * and returns an MeetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public MeetCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_START_TIME,
                        PREFIX_END_TIME, PREFIX_LABEL);

        // remove leading and trailing white space
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtilForEvent.parseName(argMultimap.getValue(PREFIX_NAME).orElse("New meeting"));
        Description description = ParserUtilForEvent.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)
                .orElse("Meeting!"));
        Venue venue = ParserUtilForEvent.parseVenue(argMultimap.getValue(PREFIX_VENUE).orElse("NUS"));
        DateTime startTime = ParserUtilForEvent.parseDateTime(argMultimap.getValue(PREFIX_START_TIME)
                .orElse("0001-01-01 00:00:00"));
        DateTime endTime = ParserUtilForEvent.parseDateTime(argMultimap.getValue(PREFIX_END_TIME)
                .orElse("0001-01-01 00:00:00"));
        Label label = ParserUtilForEvent.parseLabel(argMultimap.getValue(PREFIX_LABEL).orElse("meeting"));

        Set<Integer> indices = new TreeSet<>();
        try {
            int[] splitArgs = Stream.of(argMultimap.getPreamble().split(" "))
                    .mapToInt(x -> Integer.parseInt(x)).toArray();
            for (int i = 0; i < splitArgs.length; ++i) {
                if (splitArgs[i] < 1) {
                    throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                indices.add(splitArgs[i]);
            }
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE));
        }

        return new MeetCommand(indices, name, description, venue, startTime, endTime, label);
    }

}
