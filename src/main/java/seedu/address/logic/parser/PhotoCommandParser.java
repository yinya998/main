package seedu.address.logic.parser;
import static com.google.common.io.Files.getFileExtension;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Photo;

public class PhotoCommandParser implements Parser<PhotoCommand> {
    /* Regular expressions for validation. ArgumentMultiMap not applicable here. */
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<index>\\S+)(?<url>.+)");
    public static final String PHOTO_PATH_PARENT_DIRECTORY = "src/main/resources/images/userPhotos/";

    /**
     * Parses the given {@code String} of arguments in the context of the {@link PhotoCommand}
     * and returns an {@link PhotoCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PhotoCommand parse(String args) throws ParseException{
        requireNonNull(args);

        // Defensive programming here to trim again.
        final Matcher matcher = COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(matcher.group("index").trim());
            Photo photo = new Photo(matcher.group("url").trim());
            String localPhotoPath = copyPhotoToLocal(photo.getPath());
            Photo localPhoto = new Photo(localPhotoPath);
            return new PhotoCommand(index, localPhoto);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ive.getMessage()));
        }
    }

    private String copyPhotoToLocal(String photoPath) {
        File sourceFile = new File(photoPath);
        String extension = getFileExtension(photoPath);
        String fileName = String.valueOf( new Date().getTime());
        String destinationPath = PHOTO_PATH_PARENT_DIRECTORY + fileName + "." + extension;
        try {
            Files.copy(sourceFile.toPath(), (new File(destinationPath)).toPath());
        } catch (IOException e) {
            assert false : "Cannot copy the file!";
        }
        return destinationPath;
    }
}
