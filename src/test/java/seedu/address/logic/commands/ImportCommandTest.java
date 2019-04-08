package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class ImportCommandTest {

    private static final Path INVALID_FILE_LOCATION = Paths.get("./data/nonexistentfile.json");
    private static final Path VALID_FILE_LOCATION =
            Paths.get("src/test/data/JsonAddressBookStorageTest/sample.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory history = new CommandHistory();

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    @Test
    public void execute_importFailure_throwsException() {
        ImportCommand command = new ImportCommand(INVALID_FILE_LOCATION);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandFailure(command, model, history, String.format(command.MESSAGE_INVALID_FILE));
    }

    @Test
    public void execute_acceptedSuccess_successfulImport() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        ImportCommand command = new ImportCommand(VALID_FILE_LOCATION);

        assertCommandSuccess(command, model, history, String.format (command.MESSAGE_SUCCESS, 7, 0, 0, 0, 0, 0), model);
    }

    @Test
    public void execute_duplicateClassesAndStudents_successfulImport() throws DuplicatePersonException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person studentSample = new PersonBuilder().withName("David Li").withPhone("91031282")
                .withEmail("lidavid@example.com").withAddress("Blk 436 Serangoon Gardens Street 26, #16-43")
                .withTags("family").build();
        model.addPerson(studentSample);
        ImportCommand command = new ImportCommand(VALID_FILE_LOCATION);
        assertCommandSuccess(command, model, history, String.format (command.MESSAGE_SUCCESS, 0, 0, 7, 0, 2, 0), model);
    }

    @Test
    public void equals() {
        final ImportCommand comparableCommand = new ImportCommand(VALID_FILE_LOCATION);

        // same values -> returns true
        ImportCommand comparedToCommand = new ImportCommand(VALID_FILE_LOCATION);
        assertTrue(comparableCommand.equals(comparedToCommand));

        // same object -> returns true
        assertTrue(comparableCommand.equals(comparableCommand));

        // null -> returns false
        assertFalse(comparableCommand.equals(null));

        // different types -> returns false
        assertFalse(comparableCommand.equals(new ClearCommand()));

        // different range -> returns false
        assertFalse(comparableCommand.equals(new ImportCommand(Paths.get("./data/sampleimportfile.xml"))));
    }
}
