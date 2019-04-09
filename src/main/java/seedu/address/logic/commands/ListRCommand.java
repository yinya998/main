package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;
import seedu.address.ui.WindowViewState;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

/**
 * Lists all events in the address book to the user.
 */
public class ListRCommand extends Command {

    public static final String COMMAND_WORD = "listR";

    public static final String MESSAGE_SUCCESS = "Listed all reminders";


    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState) {
        requireNonNull(model);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, WindowViewState.EVENTS);
    }
}



