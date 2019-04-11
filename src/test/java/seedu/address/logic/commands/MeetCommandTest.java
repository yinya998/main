package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Block;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;

public class MeetCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final String meetingName = "Meeting";
        final String meetingDescription = "Generic";
        final String meetingVenue = "NUS";
        final String startDateTime = "2019-05-01 00:00:00";
        final String endDateTime = "9999-12-31 23:59:59";
        final String labelName = "meeting";
        final String durationName = "P0DT2H0M0.0S";

        Set<Integer> indices = Set.of(1);
        Name nameOfEvent = new Name(meetingName);
        Description description = new Description(meetingDescription);
        Venue venue = new Venue(meetingVenue);
        DateTime start = new DateTime(startDateTime);
        DateTime end = new DateTime(endDateTime);
        Label l = new Label(labelName);
        Set<Tag> tags = new HashSet<>();
        Duration d = Duration.parse(durationName);
        Block b = new Block(LocalTime.parse("00:00"), LocalTime.parse("00:00"), false);
        MeetCommand test = new MeetCommand(indices, nameOfEvent, description, venue, start, end, l, d, tags, b);


        // Create expectations.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToAdd = expectedModel.getFilteredPersonList().get(0);
        Event expectedEvent = new EventBuilder().withName(meetingName)
                .withDescription(meetingDescription)
                .withVenue(meetingVenue)
                .withStartDateTime(startDateTime)
                .withEndDateTime("2019-05-01 02:00:00")
                .withLabel(labelName)
                .build();
        expectedEvent.addPerson(personToAdd);
        expectedModel.addEvent(expectedEvent);
        expectedModel.commitAddressBook();
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        CommandTestUtil.assertEventCommandSuccess(test, model, commandHistory, expectedResult, expectedModel);

    }

}
