package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.tag.Tag;


public class PhotoCommand extends Command {
    public static final String COMMAND_WORD = "photo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds photo to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX PHOTO_PATH\n"
            + "Example: " + COMMAND_WORD + " 3 Myphoto.png";

    public static final String MESSAGE_ADD_PHOTO_SUCCESS = "Added photo to person: %1$s";
    public static final String MESSAGE_ADD_PHOTO_FAIL = "Operation hs failed";
    public static final String MESSAGE_DELETE_PHOTO_SUCCESS = "Deleted Photo from Person: %1$s";

    private final Index targetIndex;
    private final Photo photo;

    public PhotoCommand(Index targetIndex, Photo photo) {
        requireNonNull(targetIndex);
        requireNonNull(photo);
        this.targetIndex = targetIndex;
        this.photo = photo;
    }



    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person person = lastShownList.get(targetIndex.getZeroBased());

        //model.addPhoto(person, photo);

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setPhoto(photo);
        Person editedPerson = createEditedPerson(person, editPersonDescriptor);

        if (!person.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(person, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_ADD_PHOTO_SUCCESS, person));


    }
    private static Person createEditedPerson(Person personToEdit, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Photo updatedPhoto = editPersonDescriptor.getPhoto().orElse(personToEdit.getPhoto());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,updatedPhoto);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((PhotoCommand) other).targetIndex)
                && this.photo.equals(((PhotoCommand) other).photo)); // state check
    }


}
