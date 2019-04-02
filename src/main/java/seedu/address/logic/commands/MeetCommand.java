package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.ui.WindowViewState;

/**
 * {@code MeetCommand} forms a meeting event with a list of persons.
 * @author yonggqiii
 */
public class MeetCommand extends Command {

    public static final String COMMAND_WORD = "meet";
    public static final String MESSAGE_SUCCESS = "New meeting event successfully created";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Plans a meeting with contacts.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 4 5";
    public static final String MESSAGE_DUPLICATE_EVENT = "Operation would result in similar events."
            + " Change parameters and run command again.";

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

        // Default meeting length set as 2 hours.
        long meetingLength = 2;

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

        Name name = new Name("New meeting");
        Description description = new Description("Meeting");
        Venue venue = new Venue("NUS");
        Label label = new Label("meeting");
        Event meeting = new Event(name, description, venue,
                new DateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                new DateTime(LocalDateTime.now().plusHours(meetingLength).format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss"))),
                label);

        Event meetingEvent = model.getFilteredEventList()
                .stream()
                .filter(e -> {
                    for (Person p : personsOperatedOn) {
                        if (e.hasPerson(p)) {
                            return true;
                        }
                    }
                    return false;
                })
                .sorted(new EventComparator())
                .reduce(meeting, (x, y) -> {
                    LocalDateTime xEnd = toDateTime(x.getEndDateTime());
                    LocalDateTime yStart = toDateTime(y.getStartDateTime());
                    LocalDateTime yEnd = toDateTime(y.getEndDateTime());
                    if (toDateTime(x.getStartDateTime()).isAfter(yEnd)
                            || !xEnd.isAfter(yStart)) {
                        return x;
                    }
                    LocalDateTime start = yEnd;
                    if (xEnd.isAfter(yEnd)) {
                        start = xEnd;
                    }
                    return new Event(name, description, venue,
                            new DateTime(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                            new DateTime(start.plusHours(meetingLength).format(DateTimeFormatter
                                    .ofPattern("yyyy-MM-dd HH:mm:ss"))), label);
                });
        meetingEvent.addPerson(personsOperatedOn.toArray(new Person[0]));
        model.updateFilteredEventList(x -> true);
        for (Event e : model.getFilteredEventList()) {
            if (e.isSameEvent(meetingEvent)) {
                throw new CommandException(MESSAGE_DUPLICATE_EVENT);
            }
        }
        model.addEvent(meetingEvent);
        model.setSelectedEvent(meetingEvent);
        boolean shouldSwitch = windowViewState != WindowViewState.EVENTS;
        return new CommandResult(MESSAGE_SUCCESS + meetingEvent.toString(), false, false,
                shouldSwitch);
    }

    private LocalDateTime toDateTime(DateTime d) {
        return LocalDateTime.parse(d.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Comparator for chronological events.
     */
    static class EventComparator implements Comparator<Event> {

        @Override
        public int compare(Event e1, Event e2) {
            final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(e1.getStartDateTime().toString(), pattern).compareTo(
                    LocalDateTime.parse(e2.getStartDateTime().toString(), pattern));
        }
    }
}
