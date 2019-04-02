package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.WindowViewState;

/**
 * {@code MeetCommand} forms a meeting event with a list of persons.
 * @author yonggqiii
 */
public class MeetCommand extends Command {

    public static final String COMMAND_WORD = "meet";
    public static final String MESSAGE_NOT_IMPLEMENTED = "Meet command not implemented yet";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Plans a meeting with contacts.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 4 5";

    private List<Index> indices;
    private List<Person> listOfPeopleSelected;

    /**
     * Creates a MeetCommand using a Set of integers based on the one-based index.
     * @param indices The set of integers to be processed.
     */
    public MeetCommand(Set<Integer> indices) {
        requireNonNull(indices);
        this.indices = new ArrayList<>();
        for (Integer i : indices) {
            this.indices.add(Index.fromOneBased(i));
        }
        this.listOfPeopleSelected = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState) throws CommandException {
        // Create the command result.
        requireNonNull(model);

        // Get the people that need to be operated on.
        List<Person> listOfPeopleShown = model.getFilteredPersonList();
        List<Person> personsOperatedOn = new ArrayList<>();
        try {
            for (Index i : indices) {
                personsOperatedOn.add(listOfPeopleShown.get(i.getZeroBased()));
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_NOT_IMPLEMENTED);
        }
        //Only show people you want to meet
        model.updateFilteredPersonList(x -> personsOperatedOn.contains(x));


        boolean shouldSwitch = windowViewState != WindowViewState.EVENTS;
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED);
    }
}
