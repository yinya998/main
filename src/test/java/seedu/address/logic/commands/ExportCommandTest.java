package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;


public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Tag testingTag = new Tag("testingTag");
    private final Path testingPath = Paths.get("./test/data/JsonAddressBookStorageTest");
    private final Path alternativeTestingPath = Paths.get("./test/data/alternativeDirectory");
    private final String name = "testingName";
    private CommandHistory history = new CommandHistory();

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ExportCommand(name, null, testingTag);
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ExportCommand(null, testingPath, testingTag);
    }


    @Test
    public void execute_successfulExport_showsNoMessageError() {
        ExportCommand exportCommand = new ExportCommand(name, testingPath, testingTag);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(exportCommand, model, history, String.format(exportCommand.MESSAGE_SUCCESS), model);
    }

    @Test
    public void execute_whenTagIsSupposedlyNotGiven_showsNoMessageError() {
        ExportCommand exportCommand = new ExportCommand(name, testingPath, new Tag("shouldnotbethistag"));
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(exportCommand, model, history, String.format(exportCommand.MESSAGE_SUCCESS), model);
    }


    @Test
    public void equals() {
        final ExportCommand comparableCommand = new ExportCommand(name, testingPath, testingTag);

        // same values -> returns true
        ExportCommand comparedToCommand = new ExportCommand(name, testingPath, testingTag);
        assertTrue(comparableCommand.equals(comparedToCommand));

        // same object -> returns true
        assertTrue(comparableCommand.equals(comparableCommand));

        // null -> returns false
        assertFalse(comparableCommand.equals(null));

        // different path -> returns false
        assertFalse(comparableCommand.equals(new ExportCommand(name, alternativeTestingPath, testingTag)));
    }
}
