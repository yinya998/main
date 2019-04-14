package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Represents an event's date and time in the event list
 * Guarantees: immutable;is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {


    public static final String MESSAGE_CONSTRAINTS = "Datetime should be of the format of yyyy-mm-dd hh:mm:ss, "
            + "please check whether your date and time are valid";
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final String value;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid dateTime.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        value = dateTime;
    }

    /**
     * Returns true if a given string is a valid date time.
     */
    public static boolean isValidDateTime(String test) {
        boolean isValidDateTime = false;
        if (test.matches(VALIDATION_REGEX)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setLenient(false);
            try {
                format.parse(test);
                isValidDateTime = true;
            } catch (ParseException e) {
                isValidDateTime = false;
            }

        }
        return isValidDateTime;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.DateTime // instanceof handles nulls
                && value.equals(((seedu.address.model.event.DateTime) other).value)); // state check
    }

    /**
     * Returns true if this DateTime is before or equal another DateTime
     */
    public boolean isBefore(Object other) {
        boolean isStartBeforeEnd = false;
        if (other instanceof seedu.address.model.event.DateTime) {
            DateTime endDateTime = (seedu.address.model.event.DateTime) other;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setLenient(false);
            try {
                Date start = format.parse(this.value);
                Date end = format.parse(endDateTime.value);
                if (start.before(end) || start.equals(end)) {
                    isStartBeforeEnd = true;
                }
            } catch (ParseException e) {
                isStartBeforeEnd = false;
            }
        }
        return isStartBeforeEnd;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
