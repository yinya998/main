package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;


/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path addressBookFilePath;
    private Path eventListFilePath;

    public JsonAddressBookStorage(Path addressBookFilePath, Path eventListFilePath) {
        this.addressBookFilePath = addressBookFilePath;
        this.eventListFilePath = eventListFilePath;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public Path getEventListFilePath() {
        return eventListFilePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException {
        return readAddressBook(addressBookFilePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the contact lists. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);

        try {
            Optional<ReadOnlyAddressBook> addressBookContent = Optional.of(jsonAddressBook.get().toModelType());
            return addressBookContent;

        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookFilePath, eventListFilePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param addressBookFilePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path addressBookFilePath, Path eventListFilePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(addressBookFilePath);
        requireNonNull(eventListFilePath);

        FileUtil.createIfMissing(addressBookFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), addressBookFilePath);
    }

}
