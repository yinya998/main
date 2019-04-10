package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditECommand.
 */
public class ConnectCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_unfilteredListFirstEvent_success() {
        Event eventToUpdated = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ConnectCommand connectCommand = new ConnectCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);

        Event updatedEvent = new EventBuilder(eventToUpdated).build();
        updatedEvent = ConnectCommand.addContactToEvent(personToAdd, updatedEvent);

        String expectedMessage = String.format(ConnectCommand.MESSAGE_CONNECT_SUCCESS, personToAdd, eventToUpdated);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(eventToUpdated, updatedEvent);
        expectedModel.setSelectedEvent(null);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.setSelectedEvent(updatedEvent);
        expectedModel.commitAddressBook();
        assertEventCommandSuccess(connectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListLastEvent_success() {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        Event lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());
        Person personToAdd = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        ConnectCommand connectCommand = new ConnectCommand(INDEX_SECOND_PERSON, indexLastEvent);

        Event updatedEvent = new EventBuilder(lastEvent).build();
        updatedEvent = ConnectCommand.addContactToEvent(personToAdd, updatedEvent);

        String expectedMessage = String.format(ConnectCommand.MESSAGE_CONNECT_SUCCESS, personToAdd, lastEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(lastEvent, updatedEvent);
        expectedModel.setSelectedEvent(null);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.setSelectedEvent(updatedEvent);
        expectedModel.commitAddressBook();
        assertEventCommandSuccess(connectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        ConnectCommand connectCommand = new ConnectCommand(INDEX_SECOND_PERSON, outOfBoundIndex);

        assertEventCommandFailure(connectCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ConnectCommand connectCommand = new ConnectCommand(outOfBoundIndex, INDEX_FIRST_EVENT);

        assertEventCommandFailure(connectCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ConnectCommand standardCommand1 = new ConnectCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        final ConnectCommand standardCommand2 = new ConnectCommand(INDEX_SECOND_PERSON, INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(standardCommand1.equals(standardCommand1));

        // same values -> returns true
        ConnectCommand standardCommand1Copy = new ConnectCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertTrue(standardCommand1.equals(standardCommand1Copy));

        // different types -> returns false
        assertFalse(standardCommand1.equals(1));

        // null -> returns false
        assertFalse(standardCommand1.equals(null));

        // different event -> returns false
        assertFalse(standardCommand1.equals(standardCommand2));

    }

}
