package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.DeleteRState.EVNET_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.WrongViewException;
import seedu.address.logic.parser.DeleteRState;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.reminder.Reminder;
import seedu.address.ui.WindowViewState;


/**
 * Deletes an event identified using it's displayed index from the address book.
 */
public class DeleteRCommand extends Command {

    public static final String COMMAND_WORD = "deleteR";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminders related to event with identified index number."
            + "Or Deletes the reminders with identified index number.\n"
            + "Parameters: [n/EventIndex] \n"
            + "Example1: " + COMMAND_WORD + " n/1\n"
            + "Parameters: [r/ReminderIndex] \n"
            + "Example2: " + COMMAND_WORD + " r/1";

    public static final String MESSAGE_DELETE_RELATED_REMINDER_SUCCESS = "Deleted Reminder related with event: %1$s";
    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";
    public static final String MESSAGE_RELATED_REMINDER_NOT_FOUND = "No reminders are related with this event!";

    private final Index targetIndex;
    private final DeleteRState version;

    public DeleteRCommand(Index targetIndex, DeleteRState version) {
        this.targetIndex = targetIndex;
        this.version = version;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState)
            throws CommandException, WrongViewException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();
        List<Reminder> lastShownReminderList = model.getFilteredReminderList();

        if (version == EVNET_INDEX) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }

            Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());

            if (!model.isRemove(eventToDelete)) {
                throw new CommandException(MESSAGE_RELATED_REMINDER_NOT_FOUND);
            }
            model.deleteReminder(eventToDelete);
            model.commitAddressBook();
            boolean shouldSwitch = windowViewState != WindowViewState.EVENTS;
            boolean showFullReminder = true;
            return new CommandResult(String.format(MESSAGE_DELETE_RELATED_REMINDER_SUCCESS, eventToDelete),
                    false, false, shouldSwitch, showFullReminder);
        } else {
            if (targetIndex.getZeroBased() >= lastShownReminderList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
            }

            Reminder reminderToDelete = lastShownReminderList.get(targetIndex.getZeroBased());
            model.deleteReminder(reminderToDelete);
            model.commitAddressBook();
            boolean shouldSwitch = windowViewState != WindowViewState.EVENTS;
            boolean showFullReminder = true;
            return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete),
                    false, false, shouldSwitch, showFullReminder);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRCommand) other).targetIndex)); // state check
    }
}
