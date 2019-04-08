package seedu.address.model.reminder;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import seedu.address.model.reminder.Unit;


public class Interval {
    public static final String MESSAGE_CONSTRAINTS = "Interval should have both time and unit." +
            " Time should be a non-zero integer. Unit should be one of min|hour|year";
    public static final String INTERVALINT_VALIDATION_REGEX = "\\d+";
    public static final String UNIT_VALIDATION_REGEX = "(?i)min|year|hour";

    public final String intervalInt;
    public final Unit unit;

    public Interval(String intervalInt, String unit) {
        requireNonNull(unit);
        requireNonNull(intervalInt);
        checkArgument(isValidInterval(intervalInt, unit), MESSAGE_CONSTRAINTS);

        this.intervalInt = intervalInt;
        this.unit =new Unit(unit) ;
    }

    public static boolean isValidInterval(String intervalIntTest, String unitTest) {
        return unitTest.matches(UNIT_VALIDATION_REGEX) && intervalIntTest.matches(INTERVALINT_VALIDATION_REGEX) ;
    }

    public String toString() {
        return intervalInt + unit.toString();
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Interval // instanceof handles nulls
                && intervalInt.equals(((Interval) other).getIntervalInt())
                && unit.equals(((Interval) other).getUnit())); // state check
    }

    public String getUnit() {
        return unit.toString();
    }

    public String getIntervalInt() {
        return intervalInt;
    }

    public int hashCode() {
        return Objects.hash(intervalInt, unit);
    }

}
