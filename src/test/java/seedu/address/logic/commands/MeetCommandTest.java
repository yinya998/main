package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import java.time.LocalDateTime;
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
    public static final Supplier<EventBuilder> GENERIC_EVENTBUILDER_SUPPLIER = () -> new EventBuilder()
            .withName(GENERIC_MEETING_NAME)
            .withDescription(GENERIC_MEETING_DESCRIPTION)
            .withVenue(GENERIC_MEETING_VENUE)
            .withStartDateTime(GENERIC_VALID_START_TIME)
            .withEndDateTime(GENERIC_END_TIME_WITH_DEFAULT_DURATION)
            .withLabel(GENERIC_MEETING_LABEL);

    private Supplier<CommandHistory> emptyCommandHistorySupplier = () -> new CommandHistory();
    private Supplier<Model> typicalModelSupplier = () -> new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void testSetUpSimpleMeetingWithFirstTwoPersons() {
        MeetCommand test = new MeetCommandBuilder().build();

        // Create expectations.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        CommandTestUtil.assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                expectedResult, expectedModel);
        CommandTestUtil.assertCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                expectedResult, expectedModel);
    }

    @Test
    public void testSetUpSimpleMeetingWithFirstAndLastPerson() {
        Index lastPerson = Index.fromOneBased(typicalModelSupplier.get().getFilteredPersonList().size());
        Set<Index> indices = Set.of(INDEX_FIRST_PERSON, lastPerson);
        MeetCommand test = new MeetCommandBuilder().withIndices(indices).build();

        // Create expectations.
        Model expectedModel = typicalModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.GEORGE);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        CommandTestUtil.assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                expectedResult, expectedModel);

    }

    @Test
    public void testSetUpSimpleMeetingWithInvalidIndexUnfilteredList() {
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(Index.fromOneBased(typicalModelSupplier.get().getFilteredPersonList().size() + 1));
        MeetCommand test = new MeetCommandBuilder().withIndices(indices).build();
        CommandTestUtil.assertCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        CommandTestUtil.assertEventCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void testSetUpSimpleMeetingWithValidIndexFilteredList() {
        Model model = typicalModelSupplier.get();
        model.updateFilteredPersonList(x -> x.equals(TypicalPersons.ALICE) || x.equals(TypicalPersons.BENSON));
        Index firstValidIndex = INDEX_FIRST_PERSON;
        Index secondValidIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(firstValidIndex.getZeroBased() < model.getFilteredPersonList().size());
        assertTrue(secondValidIndex.getZeroBased() < model.getFilteredPersonList().size());
        Set<Index> indices = Set.of(firstValidIndex, secondValidIndex);
        MeetCommand test = new MeetCommandBuilder().withIndices(indices).build();

        // Create expectations.
        Model expectedModel = typicalModelSupplier.get();
        expectedModel.updateFilteredPersonList(x -> x.equals(TypicalPersons.ALICE) || x.equals(TypicalPersons.BENSON));
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
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
                MeetCommand.MESSAGE_NOT_ENOUGH_PERSONS_TO_FORM_MEETING);
        assertEventCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_NOT_ENOUGH_PERSONS_TO_FORM_MEETING);
    }

    @Test
    public void testSetUpSimpleMeetingWithOnePersonEntered() {
        Set<Index> singleIndexSet = Set.of(INDEX_FIRST_PERSON);
        MeetCommand test = new MeetCommandBuilder().withIndices(singleIndexSet).build();
        assertCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_NOT_ENOUGH_PERSONS_TO_FORM_MEETING);
        assertEventCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_NOT_ENOUGH_PERSONS_TO_FORM_MEETING);
    }
    @Test
    public void testSetUpSimpleMeetingWithValidTag() {
        Set<Index> emptyIndexSet = new HashSet<>();
        Set<Tag> tags = Set.of(new Tag("friends"));
        MeetCommand test = new MeetCommandBuilder().withIndices(emptyIndexSet).withTags(tags).build();

        // Setting expectations
        Model expectedModel = typicalModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON, TypicalPersons.DANIEL);
        setExpectedModel(expectedModel, expectedEvent);
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
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON, TypicalPersons.DANIEL);
        setExpectedModel(expectedModel, expectedEvent);
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
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON, TypicalPersons.CARL,
                TypicalPersons.DANIEL);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);

        assertCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
    }

    @Test
    public void testSetUpSimpleMeetingWithEarlyStartTime() {
        String earlyStartTime = "0001-01-01 00:00:00";
        MeetCommand test = new MeetCommandBuilder().withStartDateTime(earlyStartTime).build();

        // Setting expectations
        LocalDateTime currentDateTime = LocalDateTime.now()
                .withNano(0)
                .withSecond(0)
                .withMinute(0)
                .plusHours(1);
        String correctStartTime = currentDateTime
                .format(DateTime.DATE_TIME_FORMATTER);
        String correctEndTime = currentDateTime
                .plusHours(2)
                .format(DateTime.DATE_TIME_FORMATTER);
        Model expectedModel = typicalModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get()
                .withStartDateTime(correctStartTime)
                .withEndDateTime(correctEndTime)
                .build();

        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
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

    @Test
    public void testSetUpMeetingWithMultipleEventsInTheWay() {
        Supplier<Model> baseModelSupplier = () -> {
            Event eventInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
            eventInTheWay.addPerson(TypicalPersons.ALICE);
            Event anotherEventInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 02:00:00")
                    .withEndDateTime("9990-01-01 06:00:00")
                    .build();
            anotherEventInTheWay.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
            Model model = typicalModelSupplier.get();
            model.addEvent(eventInTheWay);
            model.addEvent(anotherEventInTheWay);
            return model;
        };

        MeetCommand test = new MeetCommandBuilder().build();

        // Set expectations.
        Model expectedModel = baseModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().withStartDateTime("9990-01-01 06:00:00")
                .withEndDateTime("9990-01-01 08:00:00").build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);
        assertCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);

    }

    @Test
    public void testSetUpMeetingWithMultipleEventsNotInTheWay() {
        Supplier<Model> baseModelSupplier = () -> {
            Event eventNotInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 02:00:00")
                    .withEndDateTime("9990-01-01 04:00:00")
                    .build();
            eventNotInTheWay.addPerson(TypicalPersons.ALICE);
            Event anotherEventNotInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 04:00:00")
                    .withEndDateTime("9990-01-01 06:00:00")
                    .build();
            anotherEventNotInTheWay.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
            Model model = typicalModelSupplier.get();
            model.addEvent(eventNotInTheWay);
            model.addEvent(anotherEventNotInTheWay);
            return model;
        };

        MeetCommand test = new MeetCommandBuilder().build();

        // Set expectations.
        Model expectedModel = baseModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);
        assertCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);

    }

    @Test
    public void testSetUpMeetingWithSomeEventsInTheWay() {
        Supplier<Model> baseModelSupplier = () -> {
            Event eventInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 00:00:00")
                    .withEndDateTime("9990-01-01 02:00:00")
                    .build();
            eventInTheWay.addPerson(TypicalPersons.ALICE);
            Event eventNotInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime(GENERIC_VALID_START_TIME)
                    .withEndDateTime(LATEST_END_TIME)
                    .build();
            eventNotInTheWay.addPerson(TypicalPersons.CARL,
                    TypicalPersons.DANIEL, TypicalPersons.ELLE, TypicalPersons.FIONA,
                    TypicalPersons.GEORGE);
            Event anotherEventNotInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 04:00:00")
                    .withEndDateTime("9990-01-01 06:00:00")
                    .build();
            anotherEventNotInTheWay.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
            Model model = typicalModelSupplier.get();
            model.addEvent(eventInTheWay);
            model.addEvent(eventNotInTheWay);
            model.addEvent(anotherEventNotInTheWay);
            return model;
        };

        MeetCommand test = new MeetCommandBuilder().build();

        // Set expectations.
        Model expectedModel = baseModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get()
                .withStartDateTime("9990-01-01 02:00:00")
                .withEndDateTime("9990-01-01 04:00:00")
                .build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);
        assertCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);

    }

    @Test
    public void testSetUpMeetingWithExcessiveDuration() {
        Supplier<Model> baseModelSupplier = () -> {
            Event eventInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 00:00:00")
                    .withEndDateTime("9990-01-01 02:00:00")
                    .build();
            eventInTheWay.addPerson(TypicalPersons.ALICE);
            Event anotherEventInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 04:00:00")
                    .withEndDateTime("9990-01-01 06:00:00")
                    .build();
            anotherEventInTheWay.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
            Model model = typicalModelSupplier.get();
            model.addEvent(eventInTheWay);
            model.addEvent(anotherEventInTheWay);
            return model;
        };

        MeetCommand test = new MeetCommandBuilder()
                .withDuration(0, 3, 0, 0)
                .build();

        // Set expectations.
        Model expectedModel = baseModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get()
                .withStartDateTime("9990-01-01 06:00:00")
                .withEndDateTime("9990-01-01 09:00:00")
                .build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);
        assertCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);

    }

    @Test
    public void testSetUpMeetingWithSmallDuration() {
        Supplier<Model> baseModelSupplier = () -> {
            Event eventInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 00:00:00")
                    .withEndDateTime("9990-01-01 02:00:00")
                    .build();
            eventInTheWay.addPerson(TypicalPersons.ALICE);
            Event anotherEventInTheWay = GENERIC_EVENTBUILDER_SUPPLIER.get()
                    .withStartDateTime("9990-01-01 03:00:00")
                    .withEndDateTime("9990-01-01 06:00:00")
                    .build();
            anotherEventInTheWay.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
            Model model = typicalModelSupplier.get();
            model.addEvent(eventInTheWay);
            model.addEvent(anotherEventInTheWay);
            return model;
        };

        MeetCommand test = new MeetCommandBuilder()
                .withDuration(0, 1, 0, 0)
                .build();

        // Set expectations.
        Model expectedModel = baseModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get()
                .withStartDateTime("9990-01-01 02:00:00")
                .withEndDateTime("9990-01-01 03:00:00")
                .build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);
        assertCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, baseModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);

    }

    @Test
    public void testSetUpMeetingWithEventFallingInBlock() {
        MeetCommand test = new MeetCommandBuilder()
                .withBlock("05:00 07:00", false)
                .build();

        // Set expectations.
        Model expectedModel = typicalModelSupplier.get();
        Event expectedEvent = GENERIC_EVENTBUILDER_SUPPLIER.get()
                .withStartDateTime("9990-01-01 05:00:00")
                .withEndDateTime("9990-01-01 07:00:00")
                .build();
        expectedEvent.addPerson(TypicalPersons.ALICE, TypicalPersons.BENSON);
        setExpectedModel(expectedModel, expectedEvent);
        CommandResult expectedResult = new CommandResult(MeetCommand.MESSAGE_SUCCESS
                + " " + expectedEvent.getName(), false, false, false);
        assertCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        test = new MeetCommandBuilder()
                .withBlock("07:00 05:00", true)
                .build();
        assertCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
        assertEventCommandSuccess(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(), expectedResult,
                expectedModel);
    }

    @Test
    public void testSetUpMeetingWithTooTightBlockBounds() {
        MeetCommand test = new MeetCommandBuilder()
                .withStartDateTime("9990-01-01 06:00:00")
                .withBlock("05:00 05:01", false)
                .build();

        assertCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_BLOCK_BOUNDS_TOO_TIGHT);
        assertEventCommandFailure(test, typicalModelSupplier.get(), emptyCommandHistorySupplier.get(),
                MeetCommand.MESSAGE_BLOCK_BOUNDS_TOO_TIGHT);
    }

    @Test
    public void testSetUpMeetingWithDuplicateEvent() {
        Model baseModel = typicalModelSupplier.get();
        Event toAdd = GENERIC_EVENTBUILDER_SUPPLIER.get().build();
        baseModel.addEvent(toAdd);
        baseModel.commitAddressBook();
        MeetCommand test = new MeetCommandBuilder().build();

        assertCommandFailure(test, baseModel, emptyCommandHistorySupplier.get(),
                String.format(MeetCommand.MESSAGE_DUPLICATE_EVENT, toAdd.getName(), toAdd.getStartDateTime()));
        assertEventCommandFailure(test, baseModel, emptyCommandHistorySupplier.get(),
                String.format(MeetCommand.MESSAGE_DUPLICATE_EVENT, toAdd.getName(), toAdd.getStartDateTime()));
    }

    @Test
    public void equalityTest() {
        MeetCommand first = new MeetCommandBuilder().build();
        MeetCommand second = first;
        assertEquals(first, second);
        second = new MeetCommandBuilder().build();
        assertEquals(first, second);
        second = new MeetCommandBuilder().withIndices(Set.of(Index.fromOneBased(2))).build();
        assertNotEquals(first, second);
        assertNotEquals(first, new Object());
        second = new MeetCommandBuilder().withIndices(Set.of(Index.fromOneBased(2))).build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withName("Different name").build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withDescription("Different description").build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withVenue("Different venue").build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withStartDateTime("0001-01-01 00:00:00").build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withEndDateTime("0001-01-01 00:00:00").build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withTags(Set.of(new Tag("tag"))).build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withBlock("00:00 00:01", false).build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withDuration(1, 1, 1, 1).build();
        assertNotEquals(first, second);
        second = new MeetCommandBuilder().withLabel("AnotherLabel").build();
        assertNotEquals(first, second);
    }

    private void setExpectedModel(Model m, Event e) {
        m.addEvent(e);
        m.setSelectedEvent(e);
        m.commitAddressBook();
    }

    private class MeetCommandBuilder {
        private Set<Index> indices;
        private Name name;
        private Description description;
        private Venue venue;
        private DateTime start;
        private DateTime end;
        private Label label;
        private Duration duration;
        private Set<Tag> tags;
        private Block block;


        MeetCommandBuilder() {
            this.indices = Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
            this.name = new Name(GENERIC_MEETING_NAME);
            this.description = new Description(GENERIC_MEETING_DESCRIPTION);
            this.venue = new Venue(GENERIC_MEETING_VENUE);
            this.start = new DateTime(GENERIC_VALID_START_TIME);
            this.end = new DateTime(LATEST_END_TIME);
            this.label = new Label(GENERIC_MEETING_LABEL);
            this.duration = DEFAULT_DURATION;
            this.tags = new HashSet<>();
            this.block = DEFAULT_BLOCK;
        }

        MeetCommand build() {
            return new MeetCommand(indices, name, description, venue, start, end, label, duration, tags,
                    block);
        }

        MeetCommandBuilder withName(String name) {
            this.name = new Name(name);
            return this;
        }

        MeetCommandBuilder withDescription(String description) {
            this.description = new Description(description);
            return this;
        }

        MeetCommandBuilder withVenue(String venue) {
            this.venue = new Venue(venue);
            return this;
        }

        MeetCommandBuilder withLabel(String label) {
            this.label = new Label(label);
            return this;
        }

        MeetCommandBuilder withIndices(Set<Index> indices) {
            this.indices = indices;
            return this;
        }

        MeetCommandBuilder withDuration(int... duration) {
            this.duration = Duration.parse(String.format(DURATION_BUILDER_STRING, duration[0],
                    duration[1], duration[2], duration[3]));
            return this;
        }

        MeetCommandBuilder withStartDateTime(String startDateTime) {
            this.start = new DateTime(startDateTime);
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

        MeetCommandBuilder withBlock(String block, boolean negated) {
            String[] times = block.split(" ");
            this.block = new Block(LocalTime.parse(times[0]), LocalTime.parse(times[1]), negated);
            return this;
        }
    }
}
