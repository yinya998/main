package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Photo in the address book.
 */
public class Photo {
    public static final String MESSAGE_CONSTRAINTS = "Invalid path of photo";
    public static final String DEFAULT_PHOTOPATH = "data/DEFAULT_PHOTO.png";
    // file path of image
    private String path;

    public Photo() {
        this.path = DEFAULT_PHOTOPATH;
    }

    public Photo(String path) {
        requireNonNull(path);
        if (path.equals("")) {
            throw new IllegalArgumentException("invalid path");
        }

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
        // check default
        if (trimmedPhoto.equals("data/DEFAULT_PHOTO.png")) {
            return true;
        }
        requireNonNull(trimmedPhoto);
        return new java.io.File(trimmedPhoto).exists();
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
