package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_NOT_IMPLEMENTED = "Import command not implemented yet";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports contacts using a path to an .xml file.\n"
            + "Parameters: PATH\n"
            + "Example: " + COMMAND_WORD + " data/contacts.xml";


    public ImportCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED);
    }
}
