package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditECommand.
 */
public class DisconnectCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_unfilteredListFirstEvent_success() {
        Event eventToUpdate = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event updatedEvent = new EventBuilder(eventToUpdate).build();
        if (!updatedEvent.hasPerson(personToAdd)) {
            updatedEvent = ConnectCommand.addContactToEvent(personToAdd, updatedEvent);
        }
        model.setEvent(eventToUpdate, updatedEvent);
        model.commitAddressBook();

        String expectedMessage = String.format(DisconnectCommand.MESSAGE_DISCONNECT_SUCCESS, personToAdd, updatedEvent);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setEvent(updatedEvent, eventToUpdate);
        expectedModel.commitAddressBook();
        DisconnectCommand disconnectCommand = new DisconnectCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertEventCommandSuccess(disconnectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListLastEvent_success() {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        Event lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());
        Person personToAdd = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event updatedEvent = new EventBuilder(lastEvent).build();
        if (!updatedEvent.hasPerson(personToAdd)) {
            updatedEvent = ConnectCommand.addContactToEvent(personToAdd, updatedEvent);
        }
        model.setEvent(lastEvent, updatedEvent);
        model.commitAddressBook();

        String expectedMessage = String.format(DisconnectCommand.MESSAGE_DISCONNECT_SUCCESS, personToAdd, updatedEvent);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setEvent(updatedEvent, lastEvent);
        expectedModel.commitAddressBook();
        DisconnectCommand disconnectCommand = new DisconnectCommand(INDEX_SECOND_PERSON, indexLastEvent);
        assertEventCommandSuccess(disconnectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DisconnectCommand disconnectCommand = new DisconnectCommand(INDEX_SECOND_PERSON, outOfBoundIndex);

        assertEventCommandFailure(disconnectCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisconnectCommand disconnectCommand = new DisconnectCommand(outOfBoundIndex, INDEX_FIRST_EVENT);

        assertEventCommandFailure(disconnectCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DisconnectCommand standardCommand1 = new DisconnectCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        final DisconnectCommand standardCommand2 = new DisconnectCommand(INDEX_SECOND_PERSON, INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(standardCommand1.equals(standardCommand1));

        // same values -> returns true
        DisconnectCommand standardCommand1Copy = new DisconnectCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertTrue(standardCommand1.equals(standardCommand1Copy));

        // different types -> returns false
        assertFalse(standardCommand1.equals(1));

        // null -> returns false
        assertFalse(standardCommand1.equals(null));

        // different event -> returns false
        assertFalse(standardCommand1.equals(standardCommand2));

    }

}
