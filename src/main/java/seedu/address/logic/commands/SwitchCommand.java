package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.WindowViewState;

/**
 * Switches the view from Person view to Events view, and vice versa.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches to the next view.\n"
            + "Example: " + COMMAND_WORD
            + " will switch to Events view from Person view, and vice versa";

    public static final String SHOWING_SWITCH_MESSAGE = "Switched view.";

    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState) {
        return new CommandResult(SHOWING_SWITCH_MESSAGE, false, false, true);
    }
}

