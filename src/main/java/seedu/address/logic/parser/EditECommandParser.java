package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditECommand;
import seedu.address.logic.parser.exceptions.ParseException;




/**
 * Parses input arguments and creates a new EditECommand object
 */
public class EditECommandParser implements Parser<EditECommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditECommand
     * and returns an EditECommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditECommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_START_TIME,
                        PREFIX_END_TIME, PREFIX_LABEL);

        Index index;

        try {
            index = ParserUtilForEvent.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditECommand.MESSAGE_USAGE), pe);
        }

        EditECommand.EditEventDescriptor editEventDescriptor = new EditECommand.EditEventDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEventDescriptor.setName(ParserUtilForEvent.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editEventDescriptor.setDescription(ParserUtilForEvent.parseDescription(argMultimap
                    .getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            editEventDescriptor.setVenue(ParserUtilForEvent.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        }
        if (argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
            editEventDescriptor.setStartDateTime(ParserUtilForEvent.parseDateTime(argMultimap
                    .getValue(PREFIX_START_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_END_TIME).isPresent()) {
            editEventDescriptor.setEndDateTime(ParserUtilForEvent.parseDateTime(argMultimap
                    .getValue(PREFIX_END_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_LABEL).isPresent()) {
            editEventDescriptor.setLabel(ParserUtilForEvent.parseLabel(argMultimap.getValue(PREFIX_LABEL).get()));
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditECommand.MESSAGE_NOT_EDITED);
        }

        return new EditECommand(index, editEventDescriptor);
    }

}

