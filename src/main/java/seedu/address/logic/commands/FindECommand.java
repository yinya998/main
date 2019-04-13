package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.ui.WindowViewState;

/**
 * Finds events in address book
 * whose field(name, description, venue, starttime, endtime, label)
 * contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindECommand extends Command {

    public static final String COMMAND_WORD = "findE";
    public static final String COMMAND_WORD_TIME = "findE time/";
    public static final String COMMAND_WORD_DURATION = "findE duration/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": finds events whose field contain any of "
            + "the specified keywords (case-insensitive)\n"
            + "Parameters: [n/EventName] [d/Description] [v/Venue] [s/StartTime] [e/EndTime] [l/Label]...\n"
            + "Example1: " + COMMAND_WORD + " talk "
            + "  (if there's no prefix, all fields will be searched)\n"
            + "Example2: " + COMMAND_WORD + " v/library l/urgent\n";

    public static final String MESSAGE_NO_PARAMETER = "Must provide at least one parameters to find.";
    public static final String MESSAGE_FINDE_INVALID_FORMAT = "The searching format is invalid.";
    public static final String MESSAGE_INVLID_DATE = "The searching date is invalid.";
    public static final String MESSAGE_USAGE_FINDE_TIME = COMMAND_WORD_TIME
            + ": finds events whose start date before, equal or "
            + "after the searching date \n"
            + "alternatively user can use ytd, today, tmr to search for events in yesterday, today and tomorrow \n"
            + "Parameters: operatorsYYYY-MM-DD (acceptable operators are <, = and >)\n"
            + "Example1: " + COMMAND_WORD_TIME + "<2019-03-27\n"
            + "Example2: " + COMMAND_WORD_TIME + "today\n";

    public static final String MESSAGE_FINDE_ONE_KEYWORD =
            "findE command should only have one keyword with no whitespace. "
                    + "\n eg. findE time/<4 (there should be no whitespace betweek '<' and '4')";
    public static final String MESSAGE_USAGE_FINDE_DURATION = COMMAND_WORD_DURATION
            + ": finds events whose duration is smaller, equal to or larger than "
            + "the searching period \n"
            + "Parameters: operatorsN (N should be a positive integer that represent duration in hours\n"
            + "Example: " + COMMAND_WORD_DURATION + "<2\n";
    public static final String MESSAGE_FINDE_DURATION_OUTOFBOUND =
            "Duration should be an positive integer representing hours within within range [1,24]";
    private Predicate<Event> predicate;

    public FindECommand(Predicate<Event> predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);

        boolean shouldSwitch = windowViewState == WindowViewState.PERSONS;
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()),
                false, false, shouldSwitch);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindECommand // instanceof handles nulls
                && predicate.equals(((FindECommand) other).predicate)); // state check
    }

    public Predicate<Event> getPredicate() {
        return predicate;
    }
}
