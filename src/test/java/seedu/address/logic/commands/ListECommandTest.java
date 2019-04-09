package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListECommand.
 */
public class ListECommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listEIsNotFiltered_showsSameList() {
        assertEventCommandSuccess(new ListECommand(), model, commandHistory,
                ListECommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listEIsFiltered_showsEverything() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);
        assertEventCommandSuccess(new ListECommand(), model, commandHistory,
                ListECommand.MESSAGE_SUCCESS, expectedModel);
    }
}
