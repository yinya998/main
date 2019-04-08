package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an event's venue in event list.
 * Guarantees: immutable;is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue {


    public static final String MESSAGE_CONSTRAINTS = "Venue can take any values, and it should not be blank, and "
             + "should not contain numerical values only";

    /*
     * The first character of the venue must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public static final String INVALID_REGEX = "-?\\d+";

    public final String value;

    /**
     * Constructs an {@code Venue}.
     *
     * @param venue A valid venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        checkArgument(isValidVenue(venue), MESSAGE_CONSTRAINTS);
        value = venue;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidVenue(String test) {
        if (test.matches(INVALID_REGEX)) {
            return false;
        }

        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.Venue // instanceof handles nulls
                && value.equals(((seedu.address.model.event.Venue ) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
