package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.WindowViewState;

/**
 * Lists all events in the address book to the user.
 */
public class ListFrCommand extends Command {

    public static final String COMMAND_WORD = "listFr";

    public static final String MESSAGE_SUCCESS = "Listed all reminders";


    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState) {
        requireNonNull(model);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        //System.out.println("list R, window view state " + windowViewState);
        boolean shouldSwitch = windowViewState != WindowViewState.EVENTS;
        boolean showFullReminder = true;
        return new CommandResult(MESSAGE_SUCCESS, false, false, shouldSwitch, showFullReminder);
    }
}



