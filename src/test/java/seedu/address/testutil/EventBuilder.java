package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.ReminderList;


/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "CS2103 project meeting";
    public static final String DEFAULT_DESCRIPTION = "quick meeting";
    public static final String DEFAULT_VENUE = "COM1 LEVEL2";
    public static final String DEFAULT_STARTDATETIME = "2019-01-31 14:00:00";
    public static final String DEFAULT_ENDDATETIME = "2019-01-31 16:00:00";
    public static final String DEFAULT_LABEL = "important";

    private Name name;
    private Venue venue;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Description description;
    private Label label;
    private Set<Person> persons;
    private ReminderList reminders;

    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        venue = new Venue(DEFAULT_VENUE);
        startDateTime = new DateTime(DEFAULT_STARTDATETIME);
        endDateTime = new DateTime(DEFAULT_ENDDATETIME);
        label = new Label(DEFAULT_LABEL);
        persons = new HashSet<>();
        reminders = new ReminderList();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        description = eventToCopy.getDescription();
        venue = eventToCopy.getVenue();
        startDateTime = eventToCopy.getStartDateTime();
        endDateTime = eventToCopy.getEndDateTime();
        label = eventToCopy.getLabel();
        persons = eventToCopy.getPersons();
        reminders = eventToCopy.getReminders();
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartDateTime(String startDateTime) {
        this.startDateTime = new DateTime(startDateTime);
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new DateTime(endDateTime);
        return this;
    }

    /**
     * Sets the {@code Label} of the {@code Event} that we are building.
     */
    public EventBuilder withLabel(String label) {
        this.label = new Label(label);
        return this;
    }


    public Event build() {
        return new Event(name, description, venue, startDateTime, endDateTime, label, persons, reminders);
    }

}

