package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public static final String MESSAGE_NOT_ENOUGH_PERSONS_TO_FORM_MEETING = "There must be at least two valid people"
            + " to form a meeting event. Please re-enter tags and/or indices.";
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
    public MeetCommand(Set<Index> indices, Name name, Description description, Venue venue, DateTime start,
                       DateTime end, Label label, Duration d, Set<Tag> tags, Block block) {
        requireNonNull(indices);
        this.indices = indices;
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
        Set<Person> personsOperatedOn = new HashSet<>();
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

        // If there are not enough people to meet in the end, let the user know to re-enter persons.
        if (personsOperatedOn.size() < 2) {
            throw new CommandException(MESSAGE_NOT_ENOUGH_PERSONS_TO_FORM_MEETING);
        }

        /*
         * Check if the user requested for a possible start date that is before the current time.
         * If the date time entered is before the current time, set the earliest event time to be
         * the next hour from the current time instead.
         */
        if (toDateTime(start).isBefore(LocalDateTime.now())) {
            start = new DateTime(LocalDateTime.now()
                    .withNano(0)
                    .withSecond(0)
                    .withMinute(0)
                    .plusHours(1)
                    .format(DateTime.DATE_TIME_FORMATTER));
        }

        // Create the earliest possible meeting given the start time.
        // Transform the event such that it fits the block. If the event does not fit the block
        // despite transformation with no other events hindering it, then the block bounds are too tight.
        Event meeting = transformEventToFitBlock(new Event(name, description, venue, start,
                new DateTime(toDateTime(start).plus(duration).format(DateTime.DATE_TIME_FORMATTER)), label));


        if (!doesEventFallWithinBlock(meeting)) {
            throw new CommandException(MESSAGE_BLOCK_BOUNDS_TOO_TIGHT);
        }


        // Ensure that all events will be retrieved from the model.
        model.updateFilteredEventList(x -> true);

        // Reduce meetingEvent to get the earliest event given other potentially clashing events.
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
                    return transformEventToFitBlock(new Event(name, description, venue,
                            new DateTime(start.format(DateTime.DATE_TIME_FORMATTER)),
                            new DateTime(start.plus(duration).format(DateTime.DATE_TIME_FORMATTER)), label));
                });

        // If the meeting event is after the specified end point, then no possible event
        // can be created.
        if (toDateTime(meetingEvent.getEndDateTime()).isAfter(toDateTime(end))) {
            throw new CommandException(MESSAGE_CANNOT_FIND_MEETING_EVENT);
        }

        // Add people to the meeting event.
        meetingEvent.addPerson(personsOperatedOn.toArray(new Person[0]));

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
        return LocalDateTime.parse(d.toString(), DateTime.DATE_TIME_FORMATTER);
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
                new DateTime(start.format(DateTime.DATE_TIME_FORMATTER)),
                new DateTime(start.plus(duration).format(DateTime.DATE_TIME_FORMATTER)),
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MeetCommand)) {
            return false;
        }
        MeetCommand other = (MeetCommand) o;
        Set<Index> combinedIndices = new HashSet<>();
        combinedIndices.addAll(this.indices);
        combinedIndices.addAll(other.indices);
        Set<Tag> combinedTags = new HashSet<>();
        combinedTags.addAll(this.tags);
        combinedTags.addAll(other.tags);

        return combinedIndices.size() == other.indices.size()
                && combinedIndices.size() == this.indices.size()
                && combinedTags.size() == other.tags.size()
                && combinedTags.size() == this.tags.size()
                && this.block.equals(other.block)
                && this.name.equals(other.name)
                && this.description.equals(other.description)
                && this.venue.equals(other.venue)
                && this.start.equals(other.start)
                && this.end.equals(other.end)
                && this.label.equals(other.label)
                && this.duration.equals(other.duration);

    }

    /**
     * Comparator for chronological events.
     */
    static class EventComparator implements Comparator<Event> {

        @Override
        public int compare(Event e1, Event e2) {
            final DateTimeFormatter pattern = DateTime.DATE_TIME_FORMATTER;
            return LocalDateTime.parse(e1.getStartDateTime().toString(), pattern).compareTo(
                    LocalDateTime.parse(e2.getStartDateTime().toString(), pattern));
        }
    }

}
