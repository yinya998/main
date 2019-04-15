package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.KAI;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.YINYA;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        String expectedResultMessage = "3 persons listed:\n"
                + " Exact Search:\n"
                + " Benson Meier, Daniel Meier, \n"
                + " Fuzzy Search:\n"
                + "Elle Meyer, \n"
                + "Wildcard Search:\n";
        ModelHelper
                .setFilteredList(expectedModel, BENSON, ELLE, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        expectedResultMessage = "1 persons listed:\n"
                + " Exact Search:\n"
                + " Carl Kurz, \n"
                + " Fuzzy Search:\n"
                + "\n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        expectedResultMessage = "2 persons listed:\n"
                + " Exact Search:\n"
                + " Benson Meier, Daniel Meier, \n"
                + " Fuzzy Search:\n"
                + "\n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 2 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(BENSON));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL, ELLE);
        expectedResultMessage = "2 persons listed:\n"
                + " Exact Search:\n"
                + " Daniel Meier, \n"
                + " Fuzzy Search:\n"
                + "Elle Meyer, \n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book,
        keyword is same as name but of different case -> 2 person found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        expectedResultMessage = "2 persons listed:\n"
                + " Exact Search:\n"
                + " Daniel Meier, \n"
                + " Fuzzy Search:\n"
                + "Elle Meyer, \n"
                + "Wildcard Search:\n";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found*/
        command = FindCommand.COMMAND_WORD + " Mei";
        expectedResultMessage = "0 persons listed:\n"
                + " Exact Search:\n"
                + " \n"
                + " Fuzzy Search:\n"
                + "\n"
                + "Wildcard Search:\n";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: fuzzy search, name is substring of keyword -> 1 persons found*/
        command = FindCommand.COMMAND_WORD + " Meiers";
        expectedResultMessage = "1 persons listed:\n"
                + " Exact Search:\n"
                + " \n"
                + " Fuzzy Search:\n"
                + "Daniel Meier, \n"
                + "Wildcard Search:\n";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found*/
        command = FindCommand.COMMAND_WORD + " Mark";
        ModelHelper.setFilteredList(expectedModel);
        expectedResultMessage = "0 persons listed:\n"
                + " Exact Search:\n"
                + " \n"
                + " Fuzzy Search:\n"
                + "\n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        expectedResultMessage = "1 persons listed:\n"
                + " Exact Search:\n"
                + " Daniel Meier, \n"
                + " Fuzzy Search:\n"
                + "\n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        expectedResultMessage = "2 persons listed:\n"
                + " Exact Search:\n"
                + " Daniel Meier, \n"
                + " Fuzzy Search:\n"
                + "Elle Meyer, \n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 2 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, DANIEL, BENSON, ALICE);
        expectedResultMessage = "2 persons listed:\n"
                + " Exact Search:\n"
                + " Alice Pauline, Daniel Meier, \n"
                + " Fuzzy Search:\n"
                + "\n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        expectedResultMessage = "1 persons listed:\n"
                + " Exact Search:\n"
                + " Daniel Meier, \n"
                + " Fuzzy Search:\n"
                + "\n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        expectedResultMessage = "0 persons listed:\n"
                + " Exact Search:\n"
                + " \n"
                + " Fuzzy Search:\n"
                + "\n"
                + "Wildcard Search:\n";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: search keywords in all fields if there's no prefix -> 2 persons found*/
        command = FindCommand.COMMAND_WORD + " yinya PGP";
        ModelHelper.setFilteredList(expectedModel, YINYA, KAI);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search multiple keywords in same fields -> 4 persons found*/
        command = FindCommand.COMMAND_WORD + " t/friends teammate";
        ModelHelper.setFilteredList(expectedModel, DANIEL, BENSON, ALICE, YINYA);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search keywords in different fields -> 1 persons found*/
        command = FindCommand.COMMAND_WORD + " t/friends n/daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search keywords in different fields -> 0 persons found*/
        command = FindCommand.COMMAND_WORD + " a/utown t/owesMoney";
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged,
     * and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
