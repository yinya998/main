package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

	public static final String MESSAGE_PATH_PARSER_NOT_IMPLEMENTED = "Path parser not implemented yet";

	/**
	 * Parses the given {@code String} of arguments in the context of the DeleteCommand
	 * and returns an DeleteCommand object for execution.
	 * @throws ParseException if the user input does not conform the expected format
	 */
	public ImportCommand parse(String args) throws ParseException {
		throw new ParseException(MESSAGE_PATH_PARSER_NOT_IMPLEMENTED);  //TODO: implement path parser in ParserUtil

		// TODO: return an ImportCommand object here
	}

}
