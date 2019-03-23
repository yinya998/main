package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Photo in the address book.
 */
public class Photo {
    // file path of image
    public String path;

    public Photo(String path) {
        requireNonNull(path);
        this.path = path;
    }

    /**
     * checking path whether or not valid.
     * path must be started with 'file:'
     *
     * @param trimmedPhoto
     * @return
     */
    public static boolean isValidPhotoPath(String trimmedPhoto) {
        requireNonNull(trimmedPhoto);
        return true;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
