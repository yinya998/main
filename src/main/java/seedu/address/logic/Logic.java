package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.WrongViewException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.ui.WindowViewState;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     * @throws WrongViewException If the window is in the wrong view as command was entered.
     */
    CommandResult execute(String commandText, WindowViewState windowViewState)
            throws CommandException, ParseException, WrongViewException;

    /**
     *
     * @return reminder check thread job
     */
    ReminderCheck getThreadJob();
    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<Event> getFilteredEventList();

    /** Returns an unmodifiable view of the filtered list of reminders */
    ObservableList<Reminder> getFilteredReminderList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     *
     * @see seedu.address.model.Model#selectedPersonProperty()
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Sets the selected person in the filtered person list.
     *
     * @see seedu.address.model.Model#setSelectedPerson(Person)
     */
    void setSelectedPerson(Person person);

    /**
     * Selected event in the filtered event list.
     * null if no event is selected.
     *
     * @see seedu.address.model.Model#selectedEventProperty()
     */
    ReadOnlyProperty<Event> selectedEventProperty();

    /**
     * Sets the selected event in the filtered event list.
     *
     * @see seedu.address.model.Model#setSelectedEvent(Event)
     */
    void setSelectedEvent(Event event);

    /**
     * Selected reminder in the filtered reminder list.
     * null if no reminder is selected.
     *
     * @see seedu.address.model.Model#selectedReminderProperty()
     */
    ReadOnlyProperty<Reminder> selectedReminderProperty();

    /**
     * Sets the selected reminder in the filtered reminder list.
     *
     * @see seedu.address.model.Model#setSelectedReminder(Reminder)
     */
    void setSelectedReminder(Reminder reminder);

    Person getSelectedPerson();
}
