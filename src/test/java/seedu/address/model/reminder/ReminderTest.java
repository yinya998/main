package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static seedu.address.testutil.TypicalEvents.EVENT1;

import java.util.Objects;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.model.event.Event;

public class ReminderTest {
    private static Event event1;
    private static String message1;

    @BeforeClass
    public static void constructor() {
        event1 = new Event(EVENT1.getName(), EVENT1.getDescription(), EVENT1.getVenue(), EVENT1.getStartDateTime(),
                EVENT1.getEndDateTime(), EVENT1.getLabel());
        message1 = "Reminder: You have an Event!";
    }


    @Test
    public void createReminder_checkCorrectness() {
        Reminder reminder = new Reminder(event1, message1);
        assertNotNull(reminder);

        assertEquals(event1, reminder.getEvent());
        assertEquals(message1, reminder.getMessage());
    }

    @Test
    public void copyReminder_testing() {
        Reminder reminder1 = new Reminder(event1,message1);
        Reminder reminder2 = new Reminder(reminder1);

        assertEquals(reminder1, reminder2);
    }

    @Test
    public void equal() {
        Reminder reminder1 = new Reminder((Event) EVENT1, EVENT1.getName().toString());
        Reminder reminder2 = new Reminder((Event) EVENT1, EVENT1.getName().toString());

        assertEquals(reminder1, reminder2);
    }

    @Test
    public void setMessage() {
        Reminder reminder = new Reminder(event1, message1);
        assertNotNull(reminder);

        reminder.setMessage("testing set message method");
        assertEquals("testing set message method", reminder.getMessage());
    }

    @Test
    public void name_testing() {
        Reminder reminder = new Reminder("fake name", message1);
        assertNotNull(reminder);

        assertEquals("fake name", reminder.getName());
    }

    @Test
    public void toString_testing() {
        Reminder reminder = new Reminder(event1, message1);
        assertNotNull(reminder);

        assertEquals("Reminder: You have an Event!", reminder.toString());
    }

    @Test
    public void hashCode_testing() {
        Reminder reminder = new Reminder(event1, message1);
        assertNotNull(reminder);

        assertEquals(Objects.hash(reminder.getEvent(), reminder.getMessage()), reminder.hashCode());
    }
}
