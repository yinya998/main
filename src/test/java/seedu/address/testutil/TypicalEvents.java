package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT1 = new EventBuilder().withName("event1")
            .withDescription("CS2103 lecture").withVenue("LT16")
            .withStartDateTime("2019-01-01 14:00:00")
            .withEndDateTime("2019-01-01 16:00:00")
            .withLabel("important").build();
    public static final Event EVENT2 = new EventBuilder().withName("event2")
            .withDescription("CS2103 tutorial").withVenue("com1 b1")
            .withStartDateTime("2019-01-21 14:00:00")
            .withEndDateTime("2019-01-21 15:00:00")
            .withLabel("urgent").build();
    public static final Event EVENT3 = new EventBuilder().withName("event3")
            .withDescription("CS2103 lab").withVenue("com1 level1")
            .withStartDateTime("2019-01-31 14:00:00")
            .withEndDateTime("2019-01-31 16:00:00")
            .withLabel("noturgent").build();
    public static final Event EVENT4 = new EventBuilder().withName("event4")
            .withDescription("CS2103 project meeting").withVenue("central library")
            .withStartDateTime("2019-02-01 14:00:00")
            .withEndDateTime("2019-02-01 16:00:00")
            .withLabel("highpriority").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT1, EVENT2, EVENT3, EVENT4));
    }
}

