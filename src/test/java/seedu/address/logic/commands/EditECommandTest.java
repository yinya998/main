package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATETIME_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATETIME_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.ui.WindowViewState;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditECommand.
 */
public class EditECommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Event editedEvent = new EventBuilder().build();
        EditECommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditECommand editECommand = new EditECommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditECommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);

        expectedModel.commitAddressBook();
        assertEventCommandSuccess(editECommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        Event lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList.withName(VALID_NAME_EVENT2).withVenue(VALID_VENUE_EVENT2)
                .withStartDateTime(VALID_STARTDATETIME_EVENT2)
                .withEndDateTime((VALID_ENDDATETIME_EVENT2)).build();

        EditECommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT2)
                .withVenue(VALID_VENUE_EVENT2).withStartDateTime(VALID_STARTDATETIME_EVENT2)
                .withEndDateTime((VALID_ENDDATETIME_EVENT2)).build();
        EditECommand editECommand = new EditECommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditECommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(lastEvent, editedEvent);
        expectedModel.commitAddressBook();

        assertEventCommandSuccess(editECommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditECommand editECommand = new EditECommand(INDEX_FIRST_EVENT, new EditECommand.EditEventDescriptor());
        Event editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(EditECommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertEventCommandSuccess(editECommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Event eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withName(VALID_NAME_EVENT2).build();
        EditECommand editECommand = new EditECommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT2).build());

        String expectedMessage = String.format(EditECommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);
        expectedModel.commitAddressBook();

        assertEventCommandSuccess(editECommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        EditECommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EditECommand editECommand = new EditECommand(INDEX_SECOND_EVENT, descriptor);

        assertEventCommandFailure(editECommand, model, commandHistory, EditECommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Event eventInList = model.getAddressBook().getEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        EditECommand editECommand = new EditECommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder(eventInList).build());

        assertEventCommandFailure(editECommand, model, commandHistory, EditECommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditECommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_NAME_EVENT2).build();
        EditECommand editECommand = new EditECommand(outOfBoundIndex, descriptor);

        assertEventCommandFailure(editECommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EditECommand editECommand = new EditECommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT2).build());

        assertEventCommandFailure(editECommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Event editedEvent = new EventBuilder().build();
        Event eventToEdit = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        EditECommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditECommand editECommand = new EditECommand(INDEX_FIRST_EVENT, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(eventToEdit, editedEvent);
        expectedModel.commitAddressBook();

        // edit -> first event edited
        editECommand.execute(model, commandHistory, WindowViewState.EVENTS);

        // undo -> reverts addressbook back to previous state and filtered event list to show all events
        expectedModel.undoAddressBook();
        assertEventCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first event edited again
        expectedModel.redoAddressBook();
        assertEventCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditECommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_NAME_EVENT2).build();
        EditECommand editECommand = new EditECommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertEventCommandFailure(editECommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertEventCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertEventCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Event} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited event in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the event object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameEventEdited() throws Exception {
        Event editedEvent = new EventBuilder().build();
        EditECommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditECommand editECommand = new EditECommand(INDEX_FIRST_EVENT, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showEventAtIndex(model, INDEX_SECOND_EVENT);
        Event eventToEdit = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.setEvent(eventToEdit, editedEvent);
        expectedModel.commitAddressBook();

        // edit -> edits second event in unfiltered event list / first event in filtered event list
        editECommand.execute(model, commandHistory, WindowViewState.EVENTS);

        // undo -> reverts addressbook back to previous state and filtered event list to show all events
        expectedModel.undoAddressBook();
        assertEventCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), eventToEdit);
        // redo -> edits same second event in unfiltered event list
        expectedModel.redoAddressBook();
        assertEventCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditECommand standardCommand = new EditECommand(INDEX_FIRST_EVENT, DESC_EVENT1);

        // same values -> returns true
        EditECommand.EditEventDescriptor copyDescriptor = new EditECommand.EditEventDescriptor(DESC_EVENT1);
        EditECommand commandWithSameValues = new EditECommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditECommand(INDEX_SECOND_EVENT, DESC_EVENT1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditECommand(INDEX_FIRST_EVENT, DESC_EVENT2)));
    }

}
