package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHOTO_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class PhotoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addPhoto() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added*/

        Person toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  "
                + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   "
                + PHOTO_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        int index = 1;
        String photoCommand;
        String path;

        // change photo image
        path = "docs/images/user_photo.png";
        photoCommand = PhotoCommand.COMMAND_WORD + " " + index + " " + path;
        getMainWindowHandle().getCommandBox().run(photoCommand);
        assertEquals("Added photo to person: data/user_photo.png",
                getMainWindowHandle().getResultDisplay().getText());

        // input invalid photo image path
        path = "docs/images/test6.pn1";
        photoCommand = PhotoCommand.COMMAND_WORD + " " + index + " " + path;
        getMainWindowHandle().getCommandBox().run(photoCommand);
        assertEquals("The path of the photo is invalid",
                getMainWindowHandle().getResultDisplay().getText());

        // input invalid photo image path
        path = "docs/diagrams/ArchitectureDiagram.pptx";
        photoCommand = PhotoCommand.COMMAND_WORD + " " + index + " " + path;
        getMainWindowHandle().getCommandBox().run(photoCommand);
        assertEquals("The file is not an image",
                getMainWindowHandle().getResultDisplay().getText());

        // delete photo
        photoCommand = PhotoCommand.COMMAND_WORD + " " + index + " clear";
        getMainWindowHandle().getCommandBox().run(photoCommand);
        assertEquals(PhotoCommand.MESSAGE_CLEAR_PHOTO_SUCCESS,
                getMainWindowHandle().getResultDisplay().getText());

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     */

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
     */

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

}





