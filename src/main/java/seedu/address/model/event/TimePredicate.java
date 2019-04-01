package seedu.address.model.event;

import static seedu.address.logic.commands.FindECommand.MESSAGE_FINDE_TIME;
import static seedu.address.logic.commands.FindECommand.MESSAGE_NO_PARAMETER;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindECommand;

//import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests that a {@code Event}'s {@code venue} matches any of the keywords given.
 */
public class TimePredicate implements Predicate<Event> {
    private final String keyword;

    public TimePredicate(String keyword) {
        this.keyword = keyword.trim();
    }

    @Override
    public boolean test(Event event) {
        try {
            char op = keyword.charAt(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date dateToBeProcessed = dateFormat.parse(keyword.substring(1));
            String dateStandard = event.getStartDateTime().toString();

            Date dateStandardD = dateFormat.parse(dateStandard);
            if(op == '<'){
                return dateToBeProcessed.before(dateStandardD);
            }
            else if(op == '>'){
                return dateToBeProcessed.after(dateStandardD);
            }

            else if(keyword.equals("today") || keyword.equals("ytd") || keyword.equals("tmr")){
                int offset = 0;
                if(keyword.equals("ytd")) {
                    offset = -1;
                }
                else if(keyword.equals("tmr")) {
                    offset = 1;
                }
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateToBeProcessed2= dateFormat2.parse(keyword);

                Calendar c1 = Calendar.getInstance();
                c1.add(Calendar.DATE, offset);
                Date todayDate = dateFormat2.parse(dateFormat2.format(c1.getTime()));
                return todayDate.equals(dateToBeProcessed2);

            }

        }catch (ParseException e)
        {
            throw e;
           // throw new ParseException(String.format(MESSAGE_FINDE_TIME), e);

        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimePredicate // instanceof handles nulls
                && keyword.equals(((TimePredicate) other).keyword)); // state check
    }

}
