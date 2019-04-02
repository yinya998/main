package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import seedu.address.logic.commands.MeetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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

        // remove leading and trailing white space
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE));
        }

        Set<Integer> indices = new TreeSet<>();
        try {
            int[] splitArgs = Stream.of(trimmedArgs.split(" ")).mapToInt(x -> Integer.parseInt(x)).toArray();
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

        return new MeetCommand(indices);
    }

}
