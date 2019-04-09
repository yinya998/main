package seedu.address.model.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

//import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests that a {@code Event}'s {@code venue} matches any of the keywords given.
 */
public class TimePredicate implements Predicate<Event> {
    private final String keyword;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public TimePredicate(String keyword) {
        this.keyword = keyword.trim();
    }

    @Override
    public boolean test(Event event) {
        try {
            char op = keyword.charAt(0);
            String eventDateS = event.getStartDateTime().toString().substring(0, 10);
            Date eventDateD = dateFormat.parse(eventDateS);

            if (op == '<') {
                Date dateToBeProcessed = dateFormat.parse(keyword.substring(1));
                return dateToBeProcessed.after(eventDateD);
            } else if (op == '>') {
                Date dateToBeProcessed = dateFormat.parse(keyword.substring(1));
                return dateToBeProcessed.before(eventDateD);
            } else if (op == '=') {
                Date dateToBeProcessed = dateFormat.parse(keyword.substring(1));
                return dateToBeProcessed.equals(eventDateD);
            } else {
                int offset = 0;
                if (keyword.equals("ytd")) {
                    offset = -1;
                } else if (keyword.equals("tmr")) {
                    offset = 1;
                }

                Calendar c1 = Calendar.getInstance();
                c1.add(Calendar.DATE, offset);
                Date todayDate = dateFormat.parse(dateFormat.format(c1.getTime()));
                return todayDate.equals(eventDateD);
            }

        } catch (ParseException e) {
            return false; // throw new ParseException(String.format(MESSAGE_FINDE_TIME), e);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimePredicate // instanceof handles nulls
                && keyword.equals(((TimePredicate) other).keyword)); // state check
    }

}
