package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Interval;
import seedu.address.model.reminder.Unit;

/**
 * Contains utility methods used for parsing strings for event class in the various *Parser classes.
 */
public class ParserUtilForReminder {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String unit} into an {@code Unit}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code unit} is invalid.
     */
    public static Unit parseUnit(String unit) throws ParseException {
        requireNonNull(unit);
        String trimmedUnit = unit.trim();
        if (!Unit.isValidUnit(trimmedUnit)) {
            throw new ParseException(Unit.MESSAGE_CONSTRAINTS);
        }
        return new Unit(trimmedUnit);
    }

    /**
     * Parses a {@code String interval} into an {@code Interval}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static Interval parseIntervalAndUnit(String interval, String unit) throws ParseException {
        requireNonNull(interval, unit);
        String trimmedInterval = interval.trim();
        String trimmedUnit = unit.trim();
        if (!Unit.isValidUnit(trimmedUnit)) {
            throw new ParseException(Unit.MESSAGE_CONSTRAINTS);
        }
        if (!Interval.isValidInterval(trimmedInterval, trimmedUnit)) {
            throw new ParseException(Interval.MESSAGE_CONSTRAINTS);
        }
        //System.out.println("parse interval now, test from Jill");
        return new Interval(trimmedInterval, trimmedUnit);
    }


}
