package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PhotoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Photo(null));
    }

    @Test
    public void constructor_invalidPhoto_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Photo(invalidPhone));
    }

    @Test
    public void isValidPhotoPath() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Photo.isValidPhotoPath(null));

        // valid
        assertTrue(Photo.isValidPhotoPath("docs/images/test6.png"));
        // invalid
        assertFalse(Photo.isValidPhotoPath(" ")); // spaces only
        assertFalse(Photo.isValidPhotoPath("docs/images/test6.pn111111g"));

    }
}
