package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ConnectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ConnectCommand object
 */
public class ConnectCommandParser implements Parser<ConnectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ConnectCommand
     * and returns an ConnectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConnectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONTACT_INDEX, PREFIX_EVENT_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_CONTACT_INDEX, PREFIX_EVENT_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConnectCommand.MESSAGE_USAGE));
        }

        Index contactIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CONTACT_INDEX).get());
        Index eventIndex = ParserUtilForEvent.parseIndex(argMultimap.getValue(PREFIX_EVENT_INDEX).get());

        return new ConnectCommand(contactIndex, eventIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
