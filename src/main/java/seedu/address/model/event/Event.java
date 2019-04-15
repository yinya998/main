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

import seedu.address.model.reminder.ReminderList;

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
    private ReminderList reminders = new ReminderList();

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

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Description description, Venue venue, DateTime startDateTime, DateTime endDateTime,
                 Label label, Set<Person> persons, ReminderList reminders) {
        requireAllNonNull(name, description, venue, startDateTime, endDateTime, label);
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.label = label;
        this.persons.addAll(persons);
        this.reminders = reminders;
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


    public ReminderList getReminders() {
        return reminders;
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
    public void addPerson(Person... toAdd) {
        requireNonNull(toAdd);
        for (Person p : toAdd) {
            if (persons.contains(p)) {
                throw new DuplicatePersonException();
            }
            persons.add(p);
        }

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
                && otherEvent.getVenue().equals(getVenue())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime());
    }

    /**
     * Creates a clone of this event.
     * @return  The cloned event.
     */
    public Event clone() {
        Event clone = new Event(
                this.name,
                this.description,
                this.venue,
                this.startDateTime,
                this.endDateTime,
                this.label
        );
        clone.addPerson(this.persons.toArray(new Person[0]));
        return clone;
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

        Set<Person> personsInEvent = new HashSet<>();
        personsInEvent.addAll(this.persons);
        personsInEvent.addAll(otherEvent.persons);

        if (personsInEvent.size() > this.persons.size() || personsInEvent.size() > otherEvent.persons.size()) {
            return false;
        }

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

