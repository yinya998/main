package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.WindowViewState;

/**
 * Finds and lists all persons in address book
 * whose field(name, address, email, phone) contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons whose field contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...\n"
            + "Example1: " + COMMAND_WORD + " yinya alex  "
            + "  (if there's no prefix, all fields will be searched)\n"
            + "Example2: " + COMMAND_WORD + " p/123456 t/teammate\n"
            + "Example3: " + COMMAND_WORD + " t/friends teammate\n";


    //private final NameContainsKeywordsPredicate predicate;//todo delete?
    public static final String MESSAGE_NO_PARAMETER = "Must provide at least one parameters to find.";
    private Predicate<Person> predicate;

    private final ArrayList<String> exactSearchList;
    private final ArrayList<String> fuzzySearchList;
    private final ArrayList<String> wildcardSearchList;

    public FindCommand(Predicate<Person> predicate, ArrayList<String> exactSearchList,
                       ArrayList<String> fuzzySearchList, ArrayList<String> wildcardSearchList) {
        this.predicate = predicate;
        this.exactSearchList = exactSearchList;
        this.fuzzySearchList = fuzzySearchList;
        this.wildcardSearchList = wildcardSearchList;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        boolean shouldSwitch = windowViewState != WindowViewState.PERSONS;

        StringBuilder exactResult = new StringBuilder();
        exactSearchList.forEach(name -> exactResult.append(name).append(", "));
        StringBuilder fuzzyResult = new StringBuilder();
        fuzzySearchList.forEach(name -> fuzzyResult.append(name).append(", "));
        StringBuilder wildcardResult = new StringBuilder();
        wildcardSearchList.forEach(name -> wildcardResult.append(name).append(", "));

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        exactSearchList.size() + fuzzySearchList.size() + wildcardSearchList.size(),
                        exactResult.toString(), fuzzyResult.toString(),
                        wildcardResult.toString()), false, false, shouldSwitch);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }

    public Predicate<Person> getPredicate() {
        return predicate;
    }
}

