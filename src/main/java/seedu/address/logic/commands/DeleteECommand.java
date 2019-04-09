package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.WrongViewException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.ui.WindowViewState;


/**
 * Deletes an event identified using it's displayed index from the address book.
 */
public class DeleteECommand extends Command {

    public static final String COMMAND_WORD = "deleteE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    private final Index targetIndex;

    public DeleteECommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState)
            throws CommandException, WrongViewException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();
        //List<Reminder> lastShownReminderList = model.getFilteredReminderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (windowViewState != WindowViewState.EVENTS) {
            throw new WrongViewException(Messages.MESSAGE_WRONG_VIEW + ". " + Messages.MESSAGE_RETRY_IN_EVENTS_VIEW);
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());

        //model.deleteReminder(eventToDelete);
        model.deleteEvent(eventToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteECommand // instanceof handles nulls
                && targetIndex.equals(((DeleteECommand) other).targetIndex)); // state check
    }
}
