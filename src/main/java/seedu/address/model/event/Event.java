package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents an event in the event list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    //identity
    private final Name name;
    private final Venue venue;
    private final DateTime startDateTime;

    private final DateTime endDateTime;
    private final Description description;
    private final Label label;
    private final Set<Person> persons = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Description description, Venue venue, DateTime startDateTime, DateTime endDateTime,
                 Label label) {
        requireAllNonNull(name, description, venue, startDateTime, endDateTime, label);
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.label = label;
    }

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Description description, Venue venue, DateTime startDateTime, DateTime endDateTime,
                 Label label, Set<Person> persons) {
        requireAllNonNull(name, description, venue, startDateTime, endDateTime, label);
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.label = label;
        this.persons.addAll(persons);
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Venue getVenue() {
        return venue;
    }

    public Label getLabel() {
        return label;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns an immutable person set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getPersons() {
        return Collections.unmodifiableSet(persons);
    }

    /**
     * Returns true if a person with the same identity as {@code person} connect with this event.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person as participant to the event.
     * The person must not already exist in the list.
     */
    public void addPerson(Person toAdd) {
        requireNonNull(toAdd);
        if (persons.contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        persons.add(toAdd);
    }

    /**
     * Removes the person from the event.
     * The person must exist in the list.
     */
    public void removePerson(Person toRemove) {
        requireNonNull(toRemove);
        if (!persons.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }
    /**
     * Returns true if both event of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(seedu.address.model.event.Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && (otherEvent.getVenue().equals(getVenue())
                || otherEvent.getStartDateTime().equals(getStartDateTime()));
    }

    /**
     * Returns true if both events have all the same fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.event.Event)) {
            return false;
        }

        seedu.address.model.event.Event otherEvent = (seedu.address.model.event.Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getVenue().equals(getVenue())
                && otherEvent.getLabel().equals(getLabel())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, venue, startDateTime, endDateTime, label);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Venue: ")
                .append(getVenue())
                .append(" Label: ")
                .append(getLabel())
                .append(" Start Date Time: ")
                .append(getStartDateTime())
                .append(" End Date Time: ")
                .append(getEndDateTime());

        return builder.toString();
    }

}

