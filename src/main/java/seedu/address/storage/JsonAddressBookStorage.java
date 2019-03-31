package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
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

    public JsonAddressBookStorage(Path addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
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

        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());

        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        } catch (NoSuchElementException nse) {
            return Optional.empty();
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookFilePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param addressBookFilePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path addressBookFilePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(addressBookFilePath);

        FileUtil.createIfMissing(addressBookFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), addressBookFilePath);
    }

}
