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
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reminder.DuplicateReminderException;
import seedu.address.model.reminder.Reminder;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.ui.WindowViewState;


/**
 * Import new contacts from the specified file path into the current address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_SUCCESS = "New contacts successfully imported!";
    protected static final String MESSAGE_INVALID_FILE = "Please input a valid file path";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports contacts using a path to a .json file.\n"
            + "Parameters: PATH\n"
            + "Example: " + COMMAND_WORD + " data/contacts.json";

    private Path filePath;
    private AddressBookStorage addressBookStorage;
    private AddressBook addressBookImported;

    public ImportCommand(Path importPath) {
        requireNonNull(importPath);
        this.filePath = importPath;

        addressBookStorage = new JsonAddressBookStorage(filePath);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState)
            throws CommandException {
        try {
            if (addressBookStorage.readAddressBook(filePath).isPresent()) {

                this.addressBookImported = new AddressBook(addressBookStorage.readAddressBook().get());
                ObservableList<Person> people = addressBookImported.getPersonList();
                ObservableList<Event> events = addressBookImported.getEventList();
                ObservableList<Reminder> reminders = addressBookImported.getReminderList();

                for (int i = 0; i < people.size(); i++) {
                    try {
                        model.addPerson(people.get(i));
                    } catch (DuplicatePersonException e) {
                        // if duplicate, do nothing, continue on with next contact
                        continue;
                    }
                }

                for (int i = 0; i < events.size(); i++) {
                    try {
                        model.addEvent(events.get(i));
                    } catch (DuplicateEventException e) {
                        // if duplicate, do nothing, continue on with next event
                        continue;
                    }
                }

                for (int i = 0; i < reminders.size(); i++) {
                    try {
                        model.addReminder(reminders.get(i));
                    } catch (DuplicateReminderException e) {
                        // if duplicate, do nothing, continue on with next reminder
                        continue;
                    }
                }

            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_FILE));
            }
        } catch (DataConversionException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE));
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE));
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filePath.equals(((ImportCommand) other).filePath));
    }
}
