package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Victoria Lee"), new Phone("85234523"), new Email("viclee@gmail.com"),
                    new Address("utown, #06-40"),
                    new Photo("data/DEFAULT_PHOTO.png"),
                    getTagSet("friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Victoria Street 74, #11-04"),
                    new Photo("data/DEFAULT_PHOTO.png"),
                    getTagSet("owesMoney")),
            new Person(new Name("Elbert Wang"), new Phone("99272758"), new Email("jsw@gmail.com"),
                    new Address("Serangoon Gardens, #07-18"),
                    new Photo("data/DEFAULT_PHOTO.png"),
                    getTagSet("salesman", "friends")),
            new Person(new Name("Elberta Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Tampines Street 20, #17-35"),
                    new Photo("data/DEFAULT_PHOTO.png"),
                    getTagSet("doctor")),
            new Person(new Name("Tony Stark"), new Phone("66666666"), new Email("marvel8@usa.com"),
                    new Address("Stark's building"),
                    new Photo("data/DEFAULT_PHOTO.png"),
                    getTagSet("victory")),
            new Person(new Name("Trump Clinton"), new Phone("92624417"), new Email("trumpcli@usa.com"),
                    new Address("pgp, bik764, #13-07"),
                    new Photo("data/DEFAULT_PHOTO.png"),
                    getTagSet("owesMoney")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
