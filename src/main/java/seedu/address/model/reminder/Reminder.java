package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import seedu.address.model.event.Event;

/**
 *  Represents a Reminder in the address book.
 *  Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reminder {
    private Event event;
    private String message;
    private String name;

    /**
     * Every filed must be present and not NULL
     */
    public Reminder(Event event, String message) {
        requireAllNonNull(event, message);
        this.event = event;
        this.message = message;
    }

    public Reminder(String name, String message) {
        requireAllNonNull(name, message);
        this.name = name;
        this.message = message;
    }

    public Reminder (Reminder source) {
        this(source.getEvent(), source.getMessage());
    }

    /**
     * Set the message of reminder, reminder message must be present and not NULL
     * @param message
     */
    public void setMessage(String message) {
        requireAllNonNull(message);
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
    //TODO: implement getEvent, which needs a get() in event class, event file
    public Event getEvent() {
        return event;
    }

    /**
     * Returns true if both reminders have same data filed.
     * This defines a stronger notion of equality between two reminders.
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Reminder && this.equals((Reminder) other)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, message);
    }


}
