package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void getName() {
        // valid name
        assertTrue(FileUtil.getName("/a/b/c.txt").endsWith("c.txt"));
    }

    @Test
    public void copyFile() {
        try {
            String dir = "src/test/resources/";
            String path = FileUtil.copyFile("src/main/resources/images/userPhotos/DEFAULT_PHOTO.png", dir);
            java.io.File f = new java.io.File(path);
            assertTrue(f.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
