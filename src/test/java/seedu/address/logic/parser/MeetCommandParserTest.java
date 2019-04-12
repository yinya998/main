package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeetCommand;
import seedu.address.model.event.Block;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Label;
import seedu.address.model.event.Name;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;

public class MeetCommandParserTest {

    public static final Name DEFAULT_NAME = new Name(MeetCommandParser.DEFAULT_NAME);
    public static final Description DEFAULT_DESCRIPTION = new Description(MeetCommandParser.DEFAULT_DESCRIPTION);
    public static final Venue DEFAULT_VENUE = new Venue(MeetCommandParser.DEFAULT_VENUE);
    public static final DateTime DEFAULT_START_TIME = new DateTime(MeetCommandParser.DEFAULT_START_TIME);
    public static final DateTime DEFAULT_END_TIME = new DateTime(MeetCommandParser.DEFAULT_END_TIME);
    public static final Label DEFAULT_LABEL = new Label(MeetCommandParser.DEFAULT_LABEL);
    public static final Duration DEFAULT_DURATION = Duration.ofHours(2);
    public static final Block DEFAULT_BLOCK = new Block(LocalTime.of(0, 0), LocalTime.of(0, 0), false);

    @Test
    public void testSingleIndex() {
        MeetCommandParser parser = new MeetCommandParser();
        MeetCommand expectedCommand = new DefaultMeetCommandBuilder()
                .withIndices(createIndexSetFrom(1))
                .build();

        assertParseSuccess(parser, "1", expectedCommand);
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MeetCommand.MESSAGE_USAGE));
        expectedCommand = new DefaultMeetCommandBuilder()
                .withIndices(createIndexSetFrom(1, 2, 3, 4, 5))
                .build();
        assertParseSuccess(parser, "1 2 3 4 5", expectedCommand);
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MeetCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MeetCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withIndices(createIndexSetFrom(1))
                .withTags(Set.of(new Tag("friends")))
                .build();
        assertParseSuccess(parser, "1 t/friends", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withIndices(createIndexSetFrom(1, 9))
                .withTags(Set.of(new Tag("friends"), new Tag("colleagues")))
                .build();
        assertParseSuccess(parser, "1 1 1 9 9 9 9 t/friends t/friends t/colleagues t/colleagues",
                expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withIndices(createIndexSetFrom(1))
                .withName(new Name("Some other name"))
                .build();
        assertParseSuccess(parser, "1 n/ Some other name", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withIndices(createIndexSetFrom(1))
                .withDescription(new Description("Some other description"))
                .build();
        assertParseSuccess(parser, "1 d/ Some other description ", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withVenue(new Venue("Some other venue"))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 v/ Some other venue ", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withStartDateTime(new DateTime("2019-01-01 01:23:45"))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 s/2019-01-01 01:23:45", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withEndDateTime(new DateTime("2019-01-01 01:23:45"))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 e/2019-01-01 01:23:45", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withLabel(new Label("iamanewlabel"))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 l/ iamanewlabel ", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withDuration(Duration.parse("P0DT23H0M0.0S"))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 duration/1 -1 0 0", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withBlock(new Block(LocalTime.of(2, 0), LocalTime.of(4, 0), false))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 block/02:00 04:00", expectedCommand);
        assertParseSuccess(parser, "1 block/!04:00 02:00", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withBlock(Block.morning(false))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 block/morning", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withBlock(Block.afternoon(false))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 block/afternoon", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withBlock(Block.evening(false))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 block/evening", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withBlock(Block.night(false))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 block/night", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withBlock(Block.midnight(false))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 block/midnight", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withBlock(Block.breakfast(false))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 block/breakfast", expectedCommand);
        expectedCommand = new DefaultMeetCommandBuilder()
                .withBlock(Block.breakfast(true))
                .withIndices(createIndexSetFrom(1))
                .build();
        assertParseSuccess(parser, "1 block/!breakfast", expectedCommand);

    }

    /**
     * This method creates a set of indices using variable one-based indices in ints.
     * @param oneBased The variable number of one based indices.
     * @return The HashSet of one based indices.
     */
    private Set<Index> createIndexSetFrom(int... oneBased) {
        Set<Index> indices = new HashSet<>();
        for (int i : oneBased) {
            indices.add(Index.fromOneBased(i));
        }
        return indices;
    }

    private class DefaultMeetCommandBuilder {

        private Set<Index> indices;
        private Name name;
        private Description description;
        private Venue venue;
        private DateTime startDateTime;
        private DateTime endDateTime;
        private Label label;
        private Duration duration;
        private Set<Tag> tags;
        private Block block;

        DefaultMeetCommandBuilder() {
            this.indices = new HashSet<>();
            this.name = DEFAULT_NAME;
            this.description = DEFAULT_DESCRIPTION;
            this.venue = DEFAULT_VENUE;
            this.startDateTime = DEFAULT_START_TIME;
            this.endDateTime = DEFAULT_END_TIME;
            this.label = DEFAULT_LABEL;
            this.duration = DEFAULT_DURATION;
            this.tags = new HashSet<>();
            this.block = DEFAULT_BLOCK;
        }

        DefaultMeetCommandBuilder withIndices(Set<Index> indices) {
            this.indices = indices;
            return this;
        }

        DefaultMeetCommandBuilder withName(Name name) {
            this.name = name;
            return this;
        }

        DefaultMeetCommandBuilder withDescription(Description description) {
            this.description = description;
            return this;
        }

        DefaultMeetCommandBuilder withVenue(Venue venue) {
            this.venue = venue;
            return this;
        }

        DefaultMeetCommandBuilder withStartDateTime(DateTime startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        DefaultMeetCommandBuilder withEndDateTime(DateTime endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        DefaultMeetCommandBuilder withLabel(Label label) {
            this.label = label;
            return this;
        }

        DefaultMeetCommandBuilder withDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        DefaultMeetCommandBuilder withTags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        DefaultMeetCommandBuilder withBlock(Block block) {
            this.block = block;
            return this;
        }

        /**
         * Builds the MeetCommand based on the attributes provided.
         * @return The resulting MeetCommand.
         */
        MeetCommand build() {
            return new MeetCommand(this.indices,
                    this.name,
                    this.description,
                    this.venue,
                    this.startDateTime,
                    this.endDateTime,
                    this.label,
                    this.duration,
                    this.tags,
                    this.block);
        }

    }

}
