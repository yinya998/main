package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.ui.WindowViewState;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteECommand}.
 */
public class DeleteECommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteECommand deleteECommand = new DeleteECommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteECommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitAddressBook();

        assertEventCommandSuccess(deleteECommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteECommand deleteECommand = new DeleteECommand(outOfBoundIndex);

        assertEventCommandFailure(deleteECommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteECommand deleteECommand = new DeleteECommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteECommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitAddressBook();
        showNoEvent(expectedModel);

        assertEventCommandSuccess(deleteECommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        DeleteECommand deleteECommand = new DeleteECommand(outOfBoundIndex);

        assertEventCommandFailure(deleteECommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteECommand deleteECommand = new DeleteECommand(INDEX_FIRST_EVENT);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitAddressBook();

        // delete -> first event deleted
        deleteECommand.execute(model, commandHistory, WindowViewState.EVENTS);

        // undo -> reverts addressbook back to previous state and filtered event list to show all events
        expectedModel.undoAddressBook();
        assertEventCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first event deleted again
        expectedModel.redoAddressBook();
        assertEventCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteECommand deleteECommand = new DeleteECommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertEventCommandFailure(deleteECommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertEventCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertEventCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteECommand deleteFirstCommand = new DeleteECommand(INDEX_FIRST_EVENT);
        DeleteECommand deleteSecondCommand = new DeleteECommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteECommand deleteFirstCommandCopy = new DeleteECommand(INDEX_FIRST_EVENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
