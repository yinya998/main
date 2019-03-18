package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a ReminderList in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ReminderList implements Iterable<Reminder> {
    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();

    /**
     * If the reminder list contains reminder other, then return true.
     * Otherwise, return false.
     */
    public boolean contains(Reminder other) {
        requireNonNull(other);
        return internalList.contains(other);
    }

    /**
     *  Add reminder toAdd into reminder list, which throws {@code DuplicateReminderException}
     *  If toAdd already exists in reminder list.
     */
    public void add(Reminder toAdd) throws DuplicateReminderException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReminderException();
        }
        internalList.add(new Reminder(toAdd));
    }

    @Override
    public Iterator<Reminder> iterator() {
        return internalList.iterator();
    }

    /**
     *  Returns true if both reminder lists have same data filed.
     *  This defines a stronger notion of equality between two reminders.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderList // instanceof handles nulls
                && this.internalList.equals(((ReminderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
