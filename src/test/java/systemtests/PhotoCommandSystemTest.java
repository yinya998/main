package systemtests;

import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonUtil;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalPersons.AMY;
//import static seedu.address.testutil.TypicalPersons.AMY_PHOTO_CHANGE;

/**public class PhotoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added

        Person toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  "
                + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   "
                + PHOTO_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        int index = 1;
        String photoCommand;
        String path;

        // change photo image
        path = "docs/images/test6.png";
        photoCommand = PhotoCommand.COMMAND_WORD + " " + index + " " + path;
        getMainWindowHandle().getCommandBox().run(photoCommand);
        assertEquals("Added photo to person: src/main/resources/images/userPhoto/test6.png",
                getMainWindowHandle().getResultDisplay().getText());

        // input invalid photo image path
        path = "docs/images/test6.pn1";
        photoCommand = PhotoCommand.COMMAND_WORD + " " + index + " " + path;
        getMainWindowHandle().getCommandBox().run(photoCommand);
        assertEquals("The path of the photo is invalid",
                getMainWindowHandle().getResultDisplay().getText());

        // delete photo
        photoCommand = PhotoCommand.COMMAND_WORD + " " + index + " clear";
        getMainWindowHandle().getCommandBox().run(photoCommand);
        assertEquals(PhotoCommand.MESSAGE_CLEAR_PHOTO_SUCCESS,
                getMainWindowHandle().getResultDisplay().getText());

    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)

    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.

    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPerson(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)

    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}*/
