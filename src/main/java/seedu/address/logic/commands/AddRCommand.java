package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.reminder.Interval;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Unit;
import seedu.address.ui.WindowViewState;


/**
 * Adds a event to the address book.
 */
public class AddRCommand extends Command {

    public static final String COMMAND_WORD = "addR";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an reminder to the address book. "
            + "Parameters: EVENT_LIST_INDEX (must be a positive integer) "
            + PREFIX_INTERVAL + "INTERVAL "
            + PREFIX_UNIT + "UNIT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INTERVAL + "3 "
            + PREFIX_UNIT + "MIN";

    public static final String MESSAGE_SUCCESS = "New reminder added!";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in the address book";
    public static final String MESSAGE_PASSED_REMINDER = "This reminder's time already passed";


    private final Index index;
    private final Unit unit;
    private final Interval interval;


    /**
     * Creates an AddRCommand to add the specified {@code index, interval, unit}
     */
    public AddRCommand(Index index, Interval interval, Unit unit) {
        requireNonNull(index);
        requireNonNull(interval);
        requireNonNull(unit);
        this.index = index;
        this.unit = unit;
        this.interval = interval;
    }



    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState)
            throws CommandException {
        requireNonNull(model);
        List<Event> lastShownEventList = model.getFilteredEventList();
        if (index.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToAdd = lastShownEventList.get(index.getZeroBased());
        Reminder reminderToAdd = new Reminder(eventToAdd, interval, "Reminder: You have an Event!");
        //System.out.println(reminderToAdd.toString());
        if (model.hasReminder(reminderToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        if (model.isReminderPassed(reminderToAdd)) {
            throw new CommandException(MESSAGE_PASSED_REMINDER);
        }
        model.addReminder(reminderToAdd);
        model.commitAddressBook();

        boolean shouldSwitch = windowViewState == WindowViewState.PERSONS;
        boolean showFullReminder = true;
        return new CommandResult(String.format(MESSAGE_SUCCESS, reminderToAdd),
                false, false, shouldSwitch, showFullReminder);
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRCommand)) {
            return false;
        }

        // state check
        AddRCommand e = (AddRCommand) other;
        return index.equals(e.index)
                && unit.equals(e.unit)
                && interval == e.interval;
    }
}
