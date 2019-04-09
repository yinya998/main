package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Block;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
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
            + " Change parameters and run command again, or consider adding the contacts to\n"
            + "%s at %s.";
    public static final String MESSAGE_CANNOT_FIND_MEETING_EVENT = "Cannot find a suitabe timeslot.\n"
            + "Please suggest different parameters, perhaps a later end time.";
    public static final String MESSAGE_NO_PERSONS_MATCH_TAGS_PROVIDED = "No persons match the tags provided.\n"
            + "Please include valid tags and/or indices of people you wish to add.";
    public static final String MESSAGE_BLOCK_BOUNDS_TOO_TIGHT = "No possible event can be created with these "
            + "block bounds. Consider expanding the time restrictions.";
    private Set<Index> indices;
    private Set<Tag> tags;
    private Name name;
    private Description description;
    private Venue venue;
    private DateTime start;
    private DateTime end;
    private Label label;
    private Duration duration;
    private Block block;

    /**
     * Creates a MeetCommand using a Set of integers based on the one-based index.
     * @param indices The set of integers to be processed.
     */
    public MeetCommand(Set<Integer> indices, Name name, Description description, Venue venue, DateTime start,
                       DateTime end, Label label, Duration d, Set<Tag> tags, Block block) {
        requireNonNull(indices);
        this.indices = new HashSet<>();
        for (Integer i : indices) {
            this.indices.add(Index.fromOneBased(i));
        }
        this.tags = tags;
        this.block = block;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.start = start;
        this.end = end;
        this.label = label;
        this.duration = d;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState)
            throws CommandException {

        // Model must exist.
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

        // Get people who have the tags entered and add them to the list of participants.
        listOfPeopleShown.stream()
                .filter(x -> {
                    Set<Tag> combined = new HashSet<>();
                    combined.addAll(x.getTags());
                    combined.addAll(tags);
                    if (combined.size() == tags.size() + x.getTags().size()) {
                        return false;
                    }
                    return true; })
                .forEach(x -> personsOperatedOn.add(x));

        // If there are no people to meet in the end, then it must mean the tags provided
        // are invalid, because indices checks were done in the MeetCommandParser.
        if (personsOperatedOn.size() < 1) {
            throw new CommandException(MESSAGE_NO_PERSONS_MATCH_TAGS_PROVIDED);
        }

        /*
         * Check if the user requested for a possible start date that is before the current time.
         * If the date time entered is before the current time, set the earliest event time to be
         * the current time instead.
         */
        if (toDateTime(start).isBefore(LocalDateTime.now())) {
            start = new DateTime(LocalDateTime.now().withNano(0).format(DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        // Create the earliest possible meeting given the start time.
        Event meeting = new Event(name, description, venue,
                start,
                new DateTime(toDateTime(start).plus(duration).format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss"))),
                label);

        // Reduce meetingEvent to get the earliest event given other potentially clashing events.
        Event meetingEvent;
        try {
            meetingEvent = model.getFilteredEventList()
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
                    .reduce(meeting, (ExceptionalBinaryOperator<Event>) (x, y) -> {
                        x = transformEventToFitBlock(x);
                        LocalDateTime xEnd = toDateTime(x.getEndDateTime());
                        LocalDateTime yStart = toDateTime(y.getStartDateTime());
                        LocalDateTime yEnd = toDateTime(y.getEndDateTime());
                        if (toDateTime(x.getStartDateTime()).isAfter(yEnd)
                                || !xEnd.isAfter(yStart)) {
                            return x;

                        }
                        LocalDateTime start = yEnd;
                        if (xEnd.isAfter(yEnd)) {
                            start = yEnd;
                        }
                        return transformEventToFitBlock(new Event(name, description, venue,
                                new DateTime(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                                new DateTime(start.plus(duration).format(DateTimeFormatter
                                        .ofPattern("yyyy-MM-dd HH:mm:ss"))), label));
                    });
        } catch (RuntimeException e) {
            throw new CommandException(e.getMessage());
        }

        // Transform the event such that it fits the block. If the event does not fit the block
        // despite transformation with no other events hindering it, then the block bounds are too tight.
        meetingEvent = transformEventToFitBlock(meetingEvent);
        if (!doesEventFallWithinBlock(meetingEvent)) {
            throw new CommandException(MESSAGE_BLOCK_BOUNDS_TOO_TIGHT);
        }

        // If the meeting event is after the specified end point, then no possible event
        // can be created.
        if (toDateTime(meetingEvent.getEndDateTime()).isAfter(toDateTime(end))) {
            throw new CommandException(MESSAGE_CANNOT_FIND_MEETING_EVENT);
        }

        // Add people to the meeting event.
        meetingEvent.addPerson(personsOperatedOn.toArray(new Person[0]));
        model.updateFilteredEventList(x -> true);

        // Check for duplicate events.
        for (Event e : model.getFilteredEventList()) {
            if (e.isSameEvent(meetingEvent)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_EVENT, meetingEvent.getName(),
                        meetingEvent.getStartDateTime()));
            }
        }

        // Add, select and commit event.
        model.addEvent(meetingEvent);
        model.setSelectedEvent(meetingEvent);
        model.commitAddressBook();

        // Switch view to show the new event.
        boolean shouldSwitch = windowViewState != WindowViewState.EVENTS;
        return new CommandResult(MESSAGE_SUCCESS + " " + meetingEvent.getName(), false, false,
                shouldSwitch);
    }

    /**
     * Change DateTime to LocalDateTime class.
     * @param d The DateTime to change.
     * @return The LocalDateTime equivalent.
     */
    private LocalDateTime toDateTime(DateTime d) {
        return LocalDateTime.parse(d.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Transforms an {@code event} such that the start time fits the earliest point of the block.
     * A copy of the {@code event} is return if it is transformed, to maintain its state.
     *
     * @param x The event to transform.
     * @return The resulting event.
     */
    private Event transformEventToFitBlock(Event x) {
        if (doesEventFallWithinBlock(x)) {
            return x;
        }
        LocalDateTime start = toDateTime(x.getStartDateTime());
        LocalDateTime tester = start.with(block.getFirst());
        if (start.isBefore(tester) || start.equals(tester)) {
            start = tester;
        } else {
            start = tester.plusDays(1);
        }
        return new Event(x.getName(), x.getDescription(), x.getVenue(),
                new DateTime(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                new DateTime(start.plus(duration).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                x.getLabel());
    }

    /**
     * Checks if the event falls within the provided block.
     * @param e The event to check.
     * @return True if the event falls within the block, false otherwise.
     */
    private boolean doesEventFallWithinBlock(Event e) {

        LocalDateTime start = toDateTime(e.getStartDateTime());
        LocalDateTime end = toDateTime(e.getEndDateTime());

        if (block.isWithinBlock(start, end)) {
            return true;
        }

        return false;

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

    /**
     * Functional interface to allow lambda expressions to throw exceptions.
     * @param <T> The type that the BinaryOperator will apply on.
     */
    @FunctionalInterface
    interface ExceptionalBinaryOperator<T> extends BinaryOperator<T> {

        /**
         * Applies the function on two objects of type T.
         * @param t The first object.
         * @param u The second object.
         * @return The resulting object.
         */
        default T apply(T t, T u) {
            try {
                return applyThrows(t, u);
            } catch (final CommandException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /**
         * Apply method to be overriden by lambda expression that allows
         * throwing of a CommandException.
         * @param t The first operand.
         * @param u The second operand.
         * @return The resulting object.
         * @throws CommandException If the lambda expression explicitly throws an exception.
         */
        T applyThrows(T t, T u) throws CommandException;
    }
}
