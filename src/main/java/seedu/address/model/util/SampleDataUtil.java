package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Label;
import seedu.address.model.event.Venue;
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
            new Person(new Name("Elbert Wang"), new Phone("99272758"), new Email("elbertwang@gmail.com"),
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
                    new Address("pgp, blk764, #13-07"),
                    new Photo("data/DEFAULT_PHOTO.png"),
                    getTagSet("owesMoney")),
            new Person(new Name("Alice Cullen"), new Phone("91251313"), new Email("alice@gmail.com"),
                    new Address("ntu, blk56, #09-08"),
                    new Photo("data/DEFAULT_PHOTO.png"),
                    getTagSet("teammate")),
        };
    }

    public static Event[] getSampleEvent() {
        return new Event[]{
            new Event(new seedu.address.model.event.Name("Google talk"), new Description("info session"),
                    new Venue("com1 level2"), new DateTime("2019-03-12 14:00:00"),
                    new DateTime("2019-03-25 16:00:00"), new Label("important")),
            new Event(new seedu.address.model.event.Name("CS2103 project meeting"), new Description("quick meeting"),
                    new Venue("central library"), new DateTime("2019-04-16 16:00:00"),
                    new DateTime("2019-04-16 17:00:00"), new Label("urgent")),
            new Event(new seedu.address.model.event.Name("wine appreciation"), new Description("workshop"),
                    new Venue("pgp blk78"), new DateTime("2019-04-17 15:00:00"),
                    new DateTime("2019-04-17 17:00:00"), new Label("fun")),
            new Event(new seedu.address.model.event.Name("Sea Interview"), new Description("Software engineering"),
                    new Venue("google building"), new DateTime("2019-04-18 10:00:00"),
                    new DateTime("2019-04-18 13:00:00"), new Label("urgent")),
            new Event(new seedu.address.model.event.Name("Biz presentation"), new Description("CCA"),
                    new Venue("LT13"), new DateTime("2019-04-20 09:00:00"),
                    new DateTime("2019-04-20 11:00:00"), new Label("urgent")),
            new Event(new seedu.address.model.event.Name("final exam"), new Description("1008"),
                    new Venue("Utown"), new DateTime("2019-05-10 18:00:00"),
                    new DateTime("2019-05-10 21:00:00"), new Label("must")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Event sampleEvent: getSampleEvent()) {
            sampleAb.addEvent(sampleEvent);
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
