package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.ui.WindowViewState;


/**
 * Adds a person to the address book.
 */
public class AddECommand extends Command {

    public static final String COMMAND_WORD = "addE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME "
            + PREFIX_LABEL + "LABEL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " "
            + PREFIX_DESCRIPTION + "CS2103 project meeting "
            + PREFIX_VENUE + "com1 level2 "
            + PREFIX_START_TIME + "2019-01-31 14:00:00 "
            + PREFIX_END_TIME + "2019-01-31 16:00:00 "
            + PREFIX_LABEL + "URGENT";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final Event toAdd;

    /**
     * Creates an AddECommand to add the specified {@code Event}
     */
    public AddECommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState)
            throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);


        //Reminder r = new Reminder(toAdd, "Reminder: You have an Event!");
        //if (model.hasReminder(r)) {
        //    throw new CommandException("Duplicate Reminder");
        //}
        //model.addReminder(r);

        model.commitAddressBook();

        boolean shouldSwitch = windowViewState == WindowViewState.PERSONS;
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, shouldSwitch);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddECommand // instanceof handles nulls
                && toAdd.equals(((AddECommand) other).toAdd));
    }
}
