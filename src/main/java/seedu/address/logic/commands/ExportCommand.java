package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;


/**
 * Import new contacts from the specified file path into the current address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Contacts successfully exported!";
    public static final String MESSAGE_FAIL = "Export command has run into a problem.";
    protected static final String MESSAGE_INVALID_FILEPATH = "Please input a valid file path";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports contacts using a path to a .json file.\n"
            + "Parameters: PATH\n"
            + "Example: " + COMMAND_WORD + " data/exported.json";

    private Path filePath;
    private String fileName;
    private Tag tag;
    private AddressBookStorage addressBookStorage;
    private AddressBook addressBookExported;
    private Path eventListFilePath = Paths.get("data", "eventlist.json");

    public ExportCommand(String fileName, Path exportPath, Tag tagExport) {
        requireNonNull(exportPath);
        this.filePath = exportPath;
        this.fileName = fileName;
        this.tag = tagExport;
        this.addressBookExported = new AddressBook();
        addressBookStorage = new JsonAddressBookStorage(filePath, eventListFilePath);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        // modify addressBookExported here
        exportAddressBook(tag, model);

        Path path = Paths.get(filePath + "/" + fileName + ".json");
        addressBookStorage = new JsonAddressBookStorage(path, eventListFilePath);

        try {
            addressBookStorage.saveAddressBook(addressBookExported);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FAIL);
        }
        return new CommandResult(MESSAGE_SUCCESS);

    }

    /**
     * Saves all people with tag matching the tag param into the to be exported address book
     *
     * @param tag
     * @param model
     * @throws DuplicatePersonException
     */
    private void exportAddressBook(Tag tag, Model model) throws DuplicatePersonException {
        ObservableList<Person> exportPeople = model.getFilteredPersonList();
        if (tag.equals(new Tag("shouldnotbethistag"))) {
            addressBookExported.setPersons(exportPeople);
        } else {
            ArrayList<Person> exportAddition = new ArrayList<Person>();
            for (int i = 0; i < exportPeople.size(); i++) {
                if (exportPeople.get(i).getTags().contains(tag)) {
                    exportAddition.add(exportPeople.get(i));
                }
            }
            addressBookExported.setPersons(exportAddition);
        }
    }

}
