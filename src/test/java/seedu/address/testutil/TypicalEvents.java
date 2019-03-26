package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_LABEL_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCTIPTION_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT1;

import seedu.address.model.event.*;

public class TypicalEvents {
    public static final Event EVENT1 = new Event (VALID_NAME_EVENT1,VALID_DESCTIPTION_EVENT1,VALID_VENUE_EVENT1,
            VALID_STARTTIME_EVENT1,VALID_ENDTIME_EVENT1,VALID_LABEL_EVENT1);
}
