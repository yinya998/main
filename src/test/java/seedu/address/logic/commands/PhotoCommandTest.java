package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Photo;
import seedu.address.testutil.Assert;

public class PhotoCommandTest {

    @Test
    public void parseSuccess() {
        try {
            PhotoCommand photoCommand = new PhotoCommand();
            photoCommand = photoCommand.parse("2 user/photo.jpg");
            assertTrue(photoCommand.getTargetIndex().getZeroBased() == 1
                    && "user/photo.jpg".equals(photoCommand.getPhoto().getPath()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parsePathWithWhitespace() {
        try {
            PhotoCommand cmd = new PhotoCommand();
            cmd = cmd.parse("   2 user/abc efg/photo.jpg   ");
            assertTrue(cmd.getTargetIndex().getZeroBased() == 1
                    && "user/abc efg/photo.jpg".equals(cmd.getPhoto().getPath()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isValidPhotoPath () {
        // null path
        Assert.assertThrows(NullPointerException.class, () -> Photo.isValidPhotoPath(null));

        // valid
        assertTrue(Photo.isValidPhotoPath("docs/images/yinya998.png"));
        // invalid
        assertFalse(Photo.isValidPhotoPath(" ")); // spaces only
        assertFalse(Photo.isValidPhotoPath("docs/images/test6.pn111111g"));

    }

    @Test
    public void testNotImage () {
        // valid
        assertTrue(PhotoCommand.isImage("docs/images/yinya998.png"));
        // invalid
        assertFalse(PhotoCommand.isImage("docs/diagrams/ArchitectureDiagram.pptx"));
        assertFalse(PhotoCommand.isImage("docs/images/test6.pn111111g"));
    }

    @Test
    public void testFileSize () {
        // valid
        assertTrue(PhotoCommand.isPhotoSizeWithinRange("docs/images/yinya998.png"));
    }

}
