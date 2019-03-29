package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class PhotoCommandTest {

    @Test
    public void parse() {
        try {
            PhotoCommand cmd = new PhotoCommand();
            cmd = cmd.parse("2 sdas");
            assertTrue(cmd.getTargetIndex().getZeroBased() == 1 && "sdas".equals(cmd.getPhoto().getPath()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
