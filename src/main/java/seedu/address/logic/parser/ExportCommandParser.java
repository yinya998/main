package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_EXPORT;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {

        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_TAG_EXPORT, PREFIX_PATH,
                PREFIX_FILENAME);
        String[] preambleArgs = argMultiMap.getPreamble().split(" ");

        // if all prefixes contain empty values
        if (!arePrefixesPresent(argMultiMap, PREFIX_PATH)
                || preambleArgs.length > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        String tag = argMultiMap.getValue(PREFIX_TAG_EXPORT).orElse("shouldnotbethistag");
        String filePath = argMultiMap.getValue(PREFIX_PATH).orElse("");
        String fileName = argMultiMap.getValue(PREFIX_FILENAME).orElse("");
        Path path = Paths.get(filePath);

        Tag tagExport = new Tag(tag);

        return new ExportCommand(fileName, path, tagExport);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
