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
    private Interval interval;
    private boolean show;
    private boolean notShow;

    /**
     * Every filed must be present and not NULL
     */
    public Reminder(Event event, String message) {
        requireAllNonNull(event, message);
        this.event = event;
        this.message = message;
        this.show = false;
        this.notShow = false;
        this.interval = new Interval("2", "min");
    }

    public Reminder(Event event, Interval interval, String message) {
        requireAllNonNull(event, interval, message);
        this.event = event;
        this.message = message;
        this.show = false;
        this.notShow = false;
        this.interval = interval;
    }

    public Reminder (Reminder source) {
        this(source.getEvent(), source.getInterval(), source.getMessage());
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

    public Interval getInterval() {
        return interval;
    }

    public String getName() {
        return event.getName().toString();
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

        //System.out.println("I m in reminder equal to check whether it is contained");
        Reminder otherReminder = (Reminder) other;
        /*System.out.println("message "+otherReminder.getMessage()+" "+ getMessage()+otherReminder.getMessage().equals(getMessage()));
        System.out.println("interval "+otherReminder.getInterval()+" "+getInterval()+otherReminder.getInterval().equals(getInterval()));
        System.out.println( "name " +otherReminder.getEvent().getName()+" "+(getEvent().getName())+otherReminder.getEvent().getName().equals(getEvent().getName()));
        System.out.println("description "+otherReminder.getEvent().getDescription()+ " " +getEvent().getDescription()+ otherReminder.getEvent().getDescription().equals(getEvent().getDescription()));
        System.out.println("venue " +otherReminder.getEvent().getVenue()+ " "+getEvent().getVenue()+otherReminder.getEvent().getVenue().equals(getEvent().getVenue()));
        System.out.println("label "+otherReminder.getEvent().getLabel()+" "+getEvent().getLabel()+otherReminder.getEvent().getLabel().equals(getEvent().getLabel()));
        System.out.println("startdatetime "+otherReminder.getEvent().getStartDateTime()+" "+ getEvent().getStartDateTime()+otherReminder.getEvent().getStartDateTime().equals(getEvent().getStartDateTime()));
        System.out.println("end datetime "+ otherReminder.getEvent().getEndDateTime()+" "+getEvent().getEndDateTime()+otherReminder.getEvent().getEndDateTime().equals(getEvent().getEndDateTime()));
        */

        return otherReminder.getMessage().equals(getMessage())
                && otherReminder.getInterval().equals(getInterval())
                && otherReminder.getEvent().getName().equals(getEvent().getName())
                && otherReminder.getEvent().getDescription().equals(getEvent().getDescription())
                && otherReminder.getEvent().getVenue().equals(getEvent().getVenue())
                && otherReminder.getEvent().getLabel().equals(getEvent().getLabel())
                && otherReminder.getEvent().getStartDateTime().equals(getEvent().getStartDateTime())
                && otherReminder.getEvent().getEndDateTime().equals(getEvent().getEndDateTime());
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
                && otherReminder.getName().equals(getName())
                && otherReminder.getInterval().equals(getInterval());
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Message: ")
                .append(getMessage())
                .append(getName())
                .append(" Description: ")
                .append(getEvent().getDescription())
                .append(" Venue: ")
                .append(getEvent().getVenue())
                .append(" Label: ")
                .append(getEvent().getLabel())
                .append(" Start Date Time: ")
                .append(getEvent().getStartDateTime())
                .append(" End Date Time: ")
                .append(getEvent().getEndDateTime())
                .append(" Interval: ")
                .append(getInterval());
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, interval, message);
    }

    /**
     *     compare current time with reminder time, to decide whether show this reminder or not.
     *         if reminder Time is equal to current time, return 0
     *         if reminder Time is earlier than current time, return -1
     *         if reminder Time is later than current time, return 1
     */
    public boolean compareWithCurrentTime() {
        //Date fakeReminderTimeLower = getFakeReminderTimeLower(this.getInterval());
        Date fakeReminderTimeUpper = getFakeReminderTimeUpper(this.getInterval());
        Date startTime = changeStringIntoDateFormat(this.getEvent().getStartDateTime().toString());
        //System.out.println("comparing show?"+this.getEvent().getStartDateTime().toString()+"<="+fakeReminderTimeUpper);
        //System.out.println("answer"+startTime.compareTo(fakeReminderTimeUpper));

        if (startTime.compareTo(fakeReminderTimeUpper) <= 0) {
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
        Date deleteTimeUpper = getReminderDeleteTimeUpper(this.getInterval());
        Date startTime = changeStringIntoDateFormat(this.getEvent().getStartDateTime().toString());
        //System.out.println("comparing whether to delete"+this.getEvent().getStartDateTime().toString()+" <= " +deleteTimeUpper.toString());
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
     * @param interval change interval time into milllis seconds.
     * @return
     */
    public int changeIntervalIntoMillis(Interval interval) {
        int time = Integer.parseInt(interval.getIntervalInt());
        if (interval.getUnit().equalsIgnoreCase("MIN")) {
            return time * 60 * 1000;
        } else if (interval.getUnit().equalsIgnoreCase("HOUR")) {
            return time * 60 * 60 * 1000;
        } else if (interval.getUnit().equalsIgnoreCase("YEAR")) {
            return time * 365 * 60 * 60 * 1000;
        } else {
            throw new RuntimeException("This is a unit exception. It should not happen");
        }
    }

    /**
     * Return the earliest reminder time
     */
    /*public Date getFakeReminderTimeLower(Interval interval) {
        int intervalMillis = changeIntervalIntoMillis(interval);
        Date temp = new Date(System.currentTimeMillis() + intervalMillis - 30 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }*/

    /**
     * @return the latest reminder time
     */
    public Date getFakeReminderTimeUpper(Interval interval) {
        int intervalMillis = changeIntervalIntoMillis(interval);
        Date temp = new Date(System.currentTimeMillis() + intervalMillis );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }

    /*
    Return the earliest delete time.
    Delete time is : 3 minutes after reminder shows up.
     */
    public Date getReminderDeleteTimeLower(Interval interval) {
        int intervalMillis = changeIntervalIntoMillis(interval);
        Date temp = new Date(System.currentTimeMillis() + intervalMillis - 180 * 1000 - 30 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }

    /*
    Return the latest delete time.
     Delete time is : 3 minutes after reminder, in which reminder is 2 minutes before start time.
     */
    public Date getReminderDeleteTimeUpper(Interval interval) {
        int intervalMillis = changeIntervalIntoMillis(interval);
        Date temp = new Date(System.currentTimeMillis() + intervalMillis - 60 * 1000);
        //System.out.println("delete time is "+temp.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        temp = changeStringIntoDateFormat(sdf.format(temp));
        return temp;
    }
}
