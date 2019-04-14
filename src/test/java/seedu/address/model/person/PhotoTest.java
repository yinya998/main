package seedu.address.model.person;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PhotoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Photo(null));
    }

    @Test
    public void constructor_invalidPhoto_throwsIllegalArgumentException() {
        String invalidPhoto = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Photo(invalidPhoto));
    }
}
