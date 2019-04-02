package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
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
    private String remindTime = "00:02";
    private boolean show;
    private boolean notShow;

    /**
     * Every filed must be present and not NULL
     */
    public Reminder(Event event, String message) {
        requireAllNonNull(event, message);
        this.event = event;
        this.message = message;
        this.name = event.getName().toString();
        this.show = false;
        this.notShow = false;
    }

    /*public Reminder(String name, String message) {
        requireAllNonNull(name, message);
        this.name = name;
        this.message = message;
    }*/

    public Reminder (Reminder source) {
        this(source.getEvent(), source.getMessage());
    }

    /**
     * Set the message of reminder, reminder message must be present and not NULL
     */
    public void setMessage(String message) {
        requireAllNonNull(message);
        this.message = message;
    }

    public void setShow(boolean show) {
        requireAllNonNull(show);
        this.show = show;
    }

    public void setNotShow(boolean notShow) {
        requireAllNonNull(notShow);
        this.notShow = notShow;
    }

    public boolean getShow() {
        return show;
    }

    public boolean getNotShow() {
        return notShow;
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
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.reminder.Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getMessage().equals(getMessage())
                && otherReminder.getEvent().equals(getEvent());
    }

    /**
     * Returns true if both reminders of the same name have same message.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameReminder(seedu.address.model.reminder.Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getMessage().equals(getMessage())
                && otherReminder.getName().equals(getName());
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getMessage());

        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, message);
    }

    /**
     *     compare current time with reminder time, to decide whether show this reminder or not.
     *         if reminder Time is equal to current time, return 0
     *         if reminder Time is earlier than current time, return -1
     *         if reminder Time is later than current time, return 1
     */
    public boolean compareWithCurrentTime() {
        Date fakeReminderTimeLower = getFakeReminderTimeLower();
        Date fakeReminderTimeUpper = getFakeReminderTimeUpper();
        Date startTime = changeStringIntoDateFormat(this.getEvent().getStartDateTime().toString());

        if (startTime.compareTo(fakeReminderTimeLower) >= 0 && startTime.compareTo(fakeReminderTimeUpper) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete te reminder
     * @return
     */
    public boolean deleteReminder() {
        Date deleteTimeUpper = getReminderDeleteTimeUpper();
        Date startTime = changeStringIntoDateFormat(this.getEvent().getStartDateTime().toString());
        if (startTime.compareTo(deleteTimeUpper) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * change the time in String format into Date format
     * @param date
     * @return
     */
    public Date changeStringIntoDateFormat(String date) {
        String stringDate = date;
        Date dateParse = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            dateParse = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateParse;
    }

    /**
     * Return the earliest reminder time
     */
    public Date getFakeReminderTimeLower() {
        Date temp = new Date(System.currentTimeMillis() + 120 * 1000 - 30 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }

    /**
     * @return the latest reminder time
     */
    public Date getFakeReminderTimeUpper() {
        Date temp = new Date(System.currentTimeMillis() + 120 * 1000 + 30 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }

    /*
    Return the earliest delete time.
    Delete time is : 3 minutes after reminder, in which reminder is 2 minutes before start time.
     */
    public Date getReminderDeleteTimeLower() {
        Date temp = new Date(System.currentTimeMillis() - 60 * 1000 - 30 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }
    /*
    Return the latest delete time.
     Delete time is : 3 minutes after reminder, in which reminder is 2 minutes before start time.
     */
    public Date getReminderDeleteTimeUpper() {
        Date temp = new Date(System.currentTimeMillis() - 60 * 1000 + 30 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }
}
