package seedu.address.model.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code duration} matches any of the keywords given.
 */
public class DurationPredicate implements Predicate<Event> {
    private final int offset;
    private final int op;
    private final int millisecondOneHour = 3600000;

    public DurationPredicate(char op, int offsett) {
        this.offset = offsett;
        this.op = op;
    }

    @Override
    public boolean test(Event event) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String eventStartDateS = event.getStartDateTime().toString();
            String eventEndDateS = event.getEndDateTime().toString();

            Date eventStartDateD = dateFormat.parse(eventStartDateS);
            Date eventEndDateD = dateFormat.parse(eventEndDateS);

            long eventDuration = eventEndDateD.getTime() - eventStartDateD.getTime();
            int durationMSec = offset * millisecondOneHour;

            if (op == '<') {
                return eventDuration < durationMSec;
            } else if (op == '>') {
                return eventDuration > durationMSec;
            } else if (op == '>') {
                return (eventDuration - durationMSec) < 1 && (eventDuration - durationMSec) > -1;
            }
            return false;
        } catch (ParseException e) {
            return false; // throw new ParseException(String.format(MESSAGE_FINDE_TIME), e);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DurationPredicate// instanceof handles nulls
                && offset == (((DurationPredicate) other).offset)
                && op == (((DurationPredicate) other).op)); // state check
    }

}
