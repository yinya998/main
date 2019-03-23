package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;


/**
 * Import new contacts from the specified file path into the current address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_SUCCESS = "Contacts successfully exported!";
    public static final String MESSAGE_NOT_IMPLEMENTED = "New contacts successfully exporteded!";
    protected static final String MESSAGE_INVALID_FILE = "Please input a valid file path";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports contacts using a path to a .json file.\n"
            + "Parameters: PATH\n"
            + "Example: " + COMMAND_WORD + " data/exported.json";

    private Path filePath;
    private AddressBookStorage addressBookStorage;
    private AddressBook addressBookExported;

    public ExportCommand(Path exportPath) {
        requireNonNull(exportPath);
        this.filePath = exportPath;
        addressBookStorage = new JsonAddressBookStorage(filePath);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED);
    }
}
