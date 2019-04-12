package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertEventCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TypicalPersons;

public class MeetCommandTest {

    public static final String GENERIC_MEETING_NAME = "Meeting";
    public static final String GENERIC_MEETING_DESCRIPTION = "This is a description!";
    public static final String GENERIC_MEETING_VENUE = "This is a meeting venue";
    public static final String GENERIC_MEETING_LABEL = "genericLabel";
    public static final String DURATION_BUILDER_STRING = "P%dDT%dH%dM%d.0S";
    public static final String GENERIC_VALID_START_TIME = "9990-01-01 00:00:00";
    public static final String GENERIC_END_TIME_WITH_DEFAULT_DURATION = "9990-01-01 02:00:00";
    public static final String LATEST_END_TIME = "9999-12-31 23:59:59";
    public static final Duration DEFAULT_DURATION = Duration.ofHours(2);
    public static final Block DEFAULT_BLOCK = new Block(LocalTime.parse("00:00"), LocalTime.parse("00:00"), false);
    public static final Supplier<EventBuilder> GENERIC_EVENTBUILDER_SUPPLIER = () -> new EventBuilder().withName(GENERIC_MEETING_NAME)
            .withDescription(GENERIC_MEETING_DESCRIPTION)
            .withVenue(GENERIC_MEETING_VENUE)
            .withStartDateTime(GENERIC_VALID_START_TIME)
            .withEndDateTime(GENERIC_END_TIME_WITH_DEFAULT_DURATION)
            .withLabel(GENERIC_MEETING_LABEL);

    private Supplier<CommandHistory> emptyCommandHistorySupplier = () -> new CommandHistory();
    private Supplier<Model> typicalModelSupplier = () -> new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void testSetUpSimpleMeetingWithFirstPerson() {
        MeetCommand test = new MeetCommandBuilder().build();

        // Create expectations.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE);
        expectedModel.addEvent(expectedEvent);
        expectedModel.setSelectedEvent(expectedEvent);
        expectedModel.commitAddressBook();
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        CommandTestUtil.assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                expectedResult, expectedModel);
        CommandTestUtil.assertCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                expectedResult, expectedModel);
    }

    @Test
    public void testSetUpSimpleMeetingWithLastPerson() {
        Index lastPerson = Index.fromOneBased(typicalModelSupplier.get().getFilteredPersonList().size());
        Set<Index> indices = Set.of(lastPerson);
        MeetCommand test = new MeetCommandBuilder().withIndices(indices).build();

        // Create expectations.
        Model expectedModel = typicalModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.GEORGE);
        expectedModel.addEvent(expectedEvent);
        expectedModel.setSelectedEvent(expectedEvent);
        expectedModel.commitAddressBook();
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        CommandTestUtil.assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                expectedResult, expectedModel);

    }

    @Test
    public void testSetUpSimpleMeetingWithInvalidIndexUnfilteredList() {
        Set<Index> indices = Set.of(Index.fromOneBased(typicalModelSupplier.get().getFilteredPersonList().size() + 1));
        MeetCommand test = new MeetCommandBuilder().withIndices(indices).build();
        CommandTestUtil.assertCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        CommandTestUtil.assertEventCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void testSetUpSimpleMeetingWithValidIndexFilteredList() {
        Model model = typicalModelSupplier.get();
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index validIndex = INDEX_FIRST_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        Set<Index> indices = Set.of(validIndex);
        MeetCommand test = new MeetCommandBuilder().withIndices(indices).build();

        // Create expectations.
        Model expectedModel = typicalModelSupplier.get();
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.GEORGE);
        expectedModel.addEvent(expectedEvent);
        expectedModel.setSelectedEvent(expectedEvent);
        expectedModel.commitAddressBook();
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        assertCommandSuccess(test, model, emptyCommandHistorySupplier.get(), expectedResult, expectedModel);
        assertEventCommandSuccess(test, model, emptyCommandHistorySupplier.get(), expectedResult, expectedModel);
    }

    @Test
    public void testSetUpSimpleMeetingWithInvalidIndexFilteredList() {
        Model model = typicalModelSupplier.get();
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        Set<Index> indices = Set.of(outOfBoundIndex);
        MeetCommand test = new MeetCommandBuilder().withIndices(indices).build();
        assertCommandFailure(test, model, emptyCommandHistorySupplier.get(),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertEventCommandFailure(test, model, emptyCommandHistorySupplier.get(),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void testSetUpSimpleMeetingWithNoOneEntered() {
        Set<Index> emptyIndexSet = new HashSet<>();
        MeetCommand test = new MeetCommandBuilder().withIndices(emptyIndexSet).build();
        assertCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_NO_PERSONS_MATCH_TAGS_PROVIDED);
        assertEventCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_NO_PERSONS_MATCH_TAGS_PROVIDED);
    }

    @Test
    public void testSetUpSimpleMeetingWithValidTag() {
        Set<Index> emptyIndexSet = new HashSet<>();
        Set<Tag> tags = Set.of(new Tag("friends"));
        MeetCommand test = new MeetCommandBuilder().withIndices(emptyIndexSet).withTags(tags).build();

        // Setting expectations
        Model expectedModel = typicalModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        expectedModel.addEvent(expectedEvent);
        expectedModel.setSelectedEvent(expectedEvent);
        expectedModel.commitAddressBook();
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        assertCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
    }

    @Test
    public void testSetUpSimpleMeetingWithValidAndInvalidTags() {
        Set<Index> emptyIndexSet = new HashSet<>();
        Set<Tag> tags = Set.of(new Tag("friends"), new Tag("ABCDEFG"));
        MeetCommand test = new MeetCommandBuilder().withIndices(emptyIndexSet).withTags(tags).build();

        // Setting expectations
        Model expectedModel = typicalModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        expectedModel.addEvent(expectedEvent);
        expectedModel.setSelectedEvent(expectedEvent);
        expectedModel.commitAddressBook();
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        assertCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
    }

    @Test
    public void testSetUpSimpleMeetingWithValidIndicesAndTags() {
        Set<Index> indices = Set.of(INDEX_FIRST_PERSON, Index.fromOneBased(3));
        Set<Tag> tags = Set.of(new Tag("friends"));
        MeetCommand test = new MeetCommandBuilder().withIndices(indices).withTags(tags).build();

        // Setting expectations
        Model expectedModel = typicalModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON, TypicalPersons.CARL);
        expectedModel.addEvent(expectedEvent);
        expectedModel.setSelectedEvent(expectedEvent);
        expectedModel.commitAddressBook();
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        assertCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
    }

    @Test
    public void testSetUpSimpleMeetingWithEarlyEndTime() {
        String earlyEndTime = "9990-01-01 01:59:59";
        MeetCommand test = new MeetCommandBuilder().withEndDateTime(earlyEndTime).build();
        assertCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_CANNOT_FIND_MEETING_EVENT);
        assertEventCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_CANNOT_FIND_MEETING_EVENT);
    }

    private class MeetCommandBuilder {
        Set<Index> indices = Set.of(INDEX_FIRST_PERSON);
        Name name = new Name(GENERIC_MEETING_NAME);
        Description description = new Description(GENERIC_MEETING_DESCRIPTION);
        Venue venue = new Venue(GENERIC_MEETING_VENUE);
        DateTime start = new DateTime(GENERIC_VALID_START_TIME);
        DateTime end = new DateTime(LATEST_END_TIME);
        Label label = new Label(GENERIC_MEETING_LABEL);
        Duration duration = DEFAULT_DURATION;
        Set<Tag> tags = new HashSet<>();
        Block block = DEFAULT_BLOCK;

        MeetCommand build() {
            return new MeetCommand(indices, name, description, venue, start, end, label, duration, tags,
                    block);
        }

        MeetCommandBuilder withIndices(Set<Index> indices) {
            this.indices = indices;
            return this;
        }

        MeetCommandBuilder withName(Name name) {
            this.name = name;
            return this;
        }

        MeetCommandBuilder withName(String name) {
            this.name = new Name(name);
            return this;
        }

        MeetCommandBuilder withDescription(Description description) {
            this.description = description;
            return this;
        }

        MeetCommandBuilder withDescription(String description) {
            this.description = new Description(description);
            return this;
        }

        MeetCommandBuilder withEndDateTime(String endDateTime) {
            this.end = new DateTime(endDateTime);
            return this;
        }

        MeetCommandBuilder withTags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }
    }
}
