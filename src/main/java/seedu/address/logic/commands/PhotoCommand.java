package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.Photo.DEFAULT_PHOTOPATH;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.WrongViewException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.tag.Tag;
import seedu.address.ui.WindowViewState;

/**
 * {@code PhotoCommand} forms a setting photo event with a list of persons.
 *
 * @author yinya998x\
 */
public class PhotoCommand extends Command {
    /**
     * Command type.
     */
    public static final String COMMAND_WORD = "photo";
    public static final String COMMAND_SUB = "clear";

    /**
     * Messages.
     */
    public static final String MESSAGE_ADD_PHOTO_SUCCESS = "Added photo to person: %1$s";
    public static final String MESSAGE_CLEAR_PHOTO_SUCCESS = "Cleared photo to person";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds photo to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX PHOTO_PATH\n"
            + "Example: " + COMMAND_WORD + " 2 /users/alice/desktop/photo.jpeg";
    public static final String MESSAGE_INVALID_PHOTOPATH = "The path of the photo is invalid";
    public static final String MESSAGE_SIZE_EXCEED = "The size of the photo should below 20MB";
    public static final String MESSAGE_FILE_NOT_IMAGE = "The file is not an image";

    private Index targetIndex;
    private Photo photo;

    public PhotoCommand() {
    }

    public PhotoCommand(Index targetIndex, Photo photo) {
        requireNonNull(targetIndex);
        requireNonNull(photo);
        this.targetIndex = targetIndex;
        this.photo = photo;
    }

    /**
     * Parse target index and path of photo from string arguments.
     *
     * @param arguments
     * @return
     * @throws ParseException
     */
    public PhotoCommand parse(String arguments) throws ParseException {
        requireNonNull(arguments);
        String argumentsTirm = arguments.trim();
        String[] strings = argumentsTirm.split("\\s+");
        this.targetIndex = ParserUtil.parseIndex(strings[0]);
        int indexDigit = strings[0].length();
        String path = argumentsTirm.substring(indexDigit).trim();
        this.photo = new Photo(path);
        return this;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, WindowViewState windowViewState)
            throws CommandException, WrongViewException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (windowViewState != WindowViewState.PERSONS) {
            throw new WrongViewException(Messages.MESSAGE_WRONG_VIEW + ". " + Messages.MESSAGE_RETRY_IN_PERSONS_VIEW);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person person = lastShownList.get(targetIndex.getZeroBased());

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        try {
            if (photo.getPath().equals(COMMAND_SUB)) {
                photo.setPath(DEFAULT_PHOTOPATH);
                Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
                String path = personToEdit.getPhoto().getPath();
                File file = new File(path);
                file.delete();
            } else {
                if (!isValidPhotoPath(photo.getPath())) {
                    return new CommandResult(MESSAGE_INVALID_PHOTOPATH);
                }
                if (!isImage(photo.getPath())) {
                    return new CommandResult(MESSAGE_FILE_NOT_IMAGE);
                }
                if (!isPhotoSizeWithinRange(photo.getPath())) {
                    return new CommandResult(MESSAGE_SIZE_EXCEED);
                }
                String user = System.getProperty("user.name");
                String dir = "data/";
                String copyPath = FileUtil.copyFile(photo.getPath(), String.format(dir, user));
                photo.setPath(copyPath);
            }

            editPersonDescriptor.setPhoto(photo);
            Person editedPerson = createEditedPerson(person, editPersonDescriptor);

            if (!person.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.setPerson(person, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();

            if (photo.getPath().equals(DEFAULT_PHOTOPATH)) {
                return new CommandResult(MESSAGE_CLEAR_PHOTO_SUCCESS);
            } else {
                return new CommandResult(String.format(MESSAGE_ADD_PHOTO_SUCCESS, photo));
            }

        } catch (IOException e) {
            return new CommandResult(Photo.MESSAGE_CONSTRAINTS);
        }

    }

    /**
     * check if a file is an image
     *
     * @param pathName
     * @return isImage
     */
    public static boolean isImage(String pathName) {
        try {
            File file = new File(pathName);
            return ImageIO.read(file) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * check path whether or not valid.
     *
     * @param pathName
     * @return
     */
    public static boolean isValidPhotoPath(String pathName) {
        if (pathName.equals("data/DEFAULT_PHOTO.png")) {
            return true;
        }
        requireNonNull(pathName);
        File f = new File(pathName);
        return f.exists();
    }

    /**
     * check the size of the file is within range
     *
     * @param pathName
     * @return
     */
    public static boolean isPhotoSizeWithinRange(String pathName) {
        File file = new File(pathName);
        double sizeInMb = ((double) file.length()) / 1024 / 1024;
        return sizeInMb < 20;
    }

    /**
     * create person object.
     *
     * @param personToEdit
     * @param editPersonDescriptor
     * @return
     */
    private static Person createEditedPerson(
            Person personToEdit, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress =
                editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Photo updatedPhoto = editPersonDescriptor.getPhoto().orElse(personToEdit.getPhoto());

        return new Person(updatedName, updatedPhone,
                updatedEmail, updatedAddress, updatedPhoto, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((PhotoCommand) other).targetIndex)
                && this.photo.equals(((PhotoCommand) other).photo)); // state check
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
