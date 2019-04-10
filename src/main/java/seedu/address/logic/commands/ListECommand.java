package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.WindowViewState;

/**
 * Lists all events in the address book to the user.
 */
public class ListECommand extends Command {

    public static final String COMMAND_WORD = "listE";

    public static final String MESSAGE_SUCCESS = "Listed all events";


    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState) {
        requireNonNull(model);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        boolean shouldSwitch = windowViewState != WindowViewState.EVENTS;
        return new CommandResult(MESSAGE_SUCCESS, false, false, shouldSwitch);
    }
}



