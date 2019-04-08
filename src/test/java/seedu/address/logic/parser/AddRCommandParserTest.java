package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.reminder.Interval;
import seedu.address.model.reminder.Unit;

public class AddRCommandParserTest {
    private Interval interval = new Interval("2", "min");
    private Unit unit = new Unit("min");
    private Index index = Index.fromOneBased(2);
    private AddRCommandParser parser = new AddRCommandParser();

    private String message = "Invalid command format! \n"
            + "addR: Adds an reminder to the address book. "
            + "Parameters: EVENT_LIST_INDEX (must be a positive integer) t/INTERVAL u/UNIT\n"
            + "Example: addR 1 t/3 u/MIN";
    @Test
    public void parse_failure() {
        assertParseFailure(parser, "t/2 u/min", message);
        assertParseFailure(parser, "2 t/2 u/random", "Units should be selected from min, hour and year");
    }
}
