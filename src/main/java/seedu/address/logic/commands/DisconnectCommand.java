package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
/**
 * Connects a contact with an event in the address book.
 */
public class DisconnectCommand extends Command {

    public static final String COMMAND_WORD = "disconnect";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Disconnects a contact with an event "
            + "by the index number used in the displayed contact and event list. \n"
            + "Parameters: CONTACT_INDEX, EVENT_INDEX(must be a positive integer) "
            + PREFIX_CONTACT_INDEX + "CONTACT_INDEX "
            + PREFIX_EVENT_INDEX + "EVENT_INDEX \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CONTACT_INDEX + "1 "
            + PREFIX_EVENT_INDEX + "2 ";

    public static final String MESSAGE_DISCONNECT_SUCCESS = "Disconnect contact %1$s and event %2$s";
    public static final String MESSAGE_NOT_CONNECT = "Fail to connect the contact and event.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This contact has already been connected to this event.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact has already been connected to this event.";

    private final Index contactIndex;
    private final Index eventIndex;

    /**
     * @param contactIndex of the contact in the filtered contact list to edit
     * @param eventIndex   of the event in the filtered event list to edit
     */
    public DisconnectCommand(Index contactIndex, Index eventIndex) {
        requireNonNull(contactIndex);
        requireNonNull(eventIndex);

        this.contactIndex = contactIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownContactList = model.getFilteredPersonList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (contactIndex.getZeroBased() >= lastShownContactList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToRemove = lastShownEventList.get(eventIndex.getZeroBased());
        Person contactToRemove = lastShownContactList.get(contactIndex.getZeroBased());
        Event updatedEvent = removeContactFromEvent(contactToRemove, eventToRemove);

        if (!eventToRemove.isSameEvent(updatedEvent) && model.hasEvent(updatedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.setEvent(eventToRemove, updatedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DISCONNECT_SUCCESS, contactToRemove, eventToRemove));
    }

    /**
     * Creates and returns a {@code Event} after disconnecting {@code contactToRemove}
     * with {@code eventToRemove}.
     */
    private static Event removeContactFromEvent(Person contactToRemove, Event eventToRemove) {
        assert contactToRemove != null;
        assert eventToRemove != null;
        eventToRemove.removePerson(contactToRemove);
        return eventToRemove;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisconnectCommand)) {
            return false;
        }

        // state check
        DisconnectCommand e = (DisconnectCommand) other;
        return contactIndex.equals(e.contactIndex)
                && eventIndex.equals(e.eventIndex);
    }
}
