package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;

/**
 * Contains utility methods used for parsing strings for event class in the various *Parser classes.
 */
public class ParserUtilForEvent {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DURATION = "Duration must be in the format D H M S"
        + " where D, H, M and S are all integers.\nThe duration must also be non-negative.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String venue} into an {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Venue.isValidVenue(trimmedVenue)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedVenue);
    }

    /**
     * Parses a {@code String dateTime} into an {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String label} into an {@code Label}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code label} is invalid.
     */
    public static Label parseLabel(String label) throws ParseException {
        requireNonNull(label);
        String trimmedLabel = label.trim();
        if (!Label.isValidLabelName(trimmedLabel)) {
            throw new ParseException(Label.MESSAGE_CONSTRAINTS);
        }
        return new Label(trimmedLabel);
    }

    /**
     * Parses a {@code String duration} into a {@code Duration}.
     * @param duration The string to be parsed into a duration.
     * @return The resulting duration.
     * @throws ParseException If the duration entered throws a DateTimeParseException
     *                        or if there are not enough arguments provided or the duration
     *                        is negative.
     */
    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        String[] trimmedDuration = duration.trim().split(" ");
        Duration d;
        try {
            if (trimmedDuration.length != 4) {
                throw new ParseException(MESSAGE_INVALID_DURATION);
            }
            d = Duration.parse(String.format("P%sDT%sH%sM%s.0S", trimmedDuration[0],
                    trimmedDuration[1], trimmedDuration[2], trimmedDuration[3]));
            if (d.isNegative()) {
                throw new ParseException(MESSAGE_INVALID_DURATION);
            }
        } catch (DateTimeParseException | IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }
        return d;
    }

}
