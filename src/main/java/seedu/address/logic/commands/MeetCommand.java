package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Event;
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
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState)
            throws CommandException {
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
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<Event> listOfEvents = model.getFilteredEventList();
        listOfEvents = listOfEvents.stream().filter(e -> {
            for (Person p : personsOperatedOn) {
                if (e.hasPerson(p)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        Collections.sort(listOfEvents, new EventComparator());

        boolean shouldSwitch = windowViewState != WindowViewState.EVENTS;
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED);
    }

    private LocalDateTime toDateTime(DateTime d) {
        return LocalDateTime.parse(d.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    static class EventComparator implements Comparator<Event> {
        public int compare(Event e1, Event e2) {
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
            return LocalDateTime.parse(e1.getStartDateTime().toString(),pattern).compareTo(
                    LocalDateTime.parse(e2.getStartDateTime().toString(),pattern));
        }
    }
}
