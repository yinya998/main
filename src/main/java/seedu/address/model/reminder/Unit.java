package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a unit for reminder in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidUnit(String)}
 */
public class Unit {

    public static final String MESSAGE_CONSTRAINTS = "Units should be selected from min, hour and year";
    public static final String VALIDATION_REGEX = "(?i)min|year|hour";

    public final String unit;

    /**
     * Constructs a {@code Unit}.
     *
     * @param unit A valid unit.
     */
    public Unit(String unit) {
        requireNonNull(unit);
        checkArgument(isValidUnit(unit), MESSAGE_CONSTRAINTS);
        this.unit = unit.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid unit.
     */
    public static boolean isValidUnit(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Unit // instanceof handles nulls
                && unit.equals(((Unit) other).getUnit())); // state check
    }

    @Override
    public int hashCode() {
        return unit.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return unit.toLowerCase();
    }

    public String getUnit() {
        return unit.toLowerCase();
    }
}



