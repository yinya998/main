package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOCK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Block;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new MeetCommand object.
 * @author yonggqiii
 */
public class MeetCommandParser implements Parser<MeetCommand> {

    public static final String DEFAULT_NAME = "New meeting";
    public static final String DEFAULT_DESCRIPTION = "Meeting with contacts.";
    public static final String DEFAULT_VENUE = "NUS";
    public static final String DEFAULT_START_TIME = "0001-01-01 00:00:00";
    public static final String DEFAULT_END_TIME = "9999-12-31 23:59:59";
    public static final String DEFAULT_LABEL = "meeting";
    public static final String DEFAULT_DURATION = "0 2 0 0";
    public static final String DEFAULT_BLOCK = "00:00 00:00";

    /**
     * Parses the given {@code String} of arguments in the context of the MeetCommand
     * and returns an MeetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public MeetCommand parse(String args) throws ParseException {

        requireNonNull(args);

        // Split tokenize arguments into multimap.
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_START_TIME,
                        PREFIX_END_TIME, PREFIX_LABEL, PREFIX_DURATION, PREFIX_TAG, PREFIX_BLOCK);

        // User must indicate at least one person to meet, either through tags, or through indices.
        // Tag validity is checked within the MeetCommand implementation itself.
        String preamble = argMultimap.getPreamble();
        if (preamble.isEmpty() && argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE));
        }

        // Parse each argument, if not present, set a default value.
        Name name = ParserUtilForEvent.parseName(argMultimap.getValue(PREFIX_NAME).orElse(DEFAULT_NAME));
        Description description = ParserUtilForEvent.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)
                .orElse(DEFAULT_DESCRIPTION));
        Venue venue = ParserUtilForEvent.parseVenue(argMultimap.getValue(PREFIX_VENUE).orElse(DEFAULT_VENUE));
        DateTime startTime = ParserUtilForEvent.parseDateTime(argMultimap.getValue(PREFIX_START_TIME)
                .orElse(DEFAULT_START_TIME));
        DateTime endTime = ParserUtilForEvent.parseDateTime(argMultimap.getValue(PREFIX_END_TIME)
                .orElse(DEFAULT_END_TIME));
        Label label = ParserUtilForEvent.parseLabel(argMultimap.getValue(PREFIX_LABEL).orElse(DEFAULT_LABEL));
        Duration duration = ParserUtilForEvent.parseDuration(argMultimap.getValue(PREFIX_DURATION)
                .orElse(DEFAULT_DURATION));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Block blockList = ParserUtil.parseBlock(argMultimap.getValue(PREFIX_BLOCK).orElse(DEFAULT_BLOCK));
        Set<Integer> indices = new TreeSet<>();
        if (!preamble.isEmpty()) {
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
        }
        Set<Index> actualIndices = new HashSet<>();
        for (Integer i : indices) {
            actualIndices.add(Index.fromOneBased(i));
        }

        return new MeetCommand(actualIndices, name, description, venue, startTime, endTime, label, duration, tagList,
                blockList);
    }

}
