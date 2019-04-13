package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;

/**
 * Represents a Photo in the address book.
 */
public class Photo {
    public static final String MESSAGE_CONSTRAINTS = "Cannot add photo to person";
    public static final String DEFAULT_PHOTOPATH = "data/DEFAULT_PHOTO.png";
    // file path of image
    private String path;

    public Photo() {
        this.path = DEFAULT_PHOTOPATH;
    }

    public Photo(String path) {
        requireNonNull(path);
        if (path.equals("")) {
            throw new IllegalArgumentException("The path should not be empty");
        }

        this.path = path;
    }

    /**
     * checking path whether or not valid.
     *
     * @param trimmedPhoto
     * @return
     */
    public static boolean isValidPhotoPath(String trimmedPhoto) {
        // check default
        if (trimmedPhoto.equals("data/DEFAULT_PHOTO.png")) {
            return true;
        }
        requireNonNull(trimmedPhoto);
        File f = new File(trimmedPhoto);
        return f.exists();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && path.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
