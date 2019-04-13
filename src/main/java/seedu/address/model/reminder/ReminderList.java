package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.event.Event;

/**
 * Represents a ReminderList in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ReminderList implements Iterable<Reminder> {
    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reminder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    /**
     * If the reminder list contains reminder other, then return true.
     * Otherwise, return false.
     */
    public boolean contains(Reminder other) {
        requireNonNull(other);
        //System.out.println("comparing whether it is contained in the list" + internalList.contains(other));
        return internalList.contains(other);
    }

    /*public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.isSameEvent(editedEvent) && contains(editedEvent)) {
            throw new EventNotFoundException();
        }

        internalList.set(index, editedEvent);
    }*/

    /**
     * Replaces the contents of this list with {@code Reminders}.
     * {@code persons} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        requireAllNonNull(reminders);
        if (!remindersAreUnique(reminders)) {
            throw new DuplicateReminderException();
        }
        internalList.setAll(reminders);
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

    /**
     * make the reminder toAdd shown in the UI
     * @param toAdd
     */
    public void addShown(Reminder toAdd) {
        requireNonNull(toAdd);
        int index = internalList.indexOf(toAdd);
        if (index == -1) {
            throw new NotFoundException();
        }
        toAdd.setShow(true);
        internalList.set(index, toAdd);
    }

    public Reminder get(int index) {
        return internalList.get(index);
    }
    /**
     * Removes the equivalent reminder from the list.
     * The event must exist in the list.
     */
    public void remove(Reminder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new NotFoundException();
        }
    }

    /**
     * Removes the reminder related to this event from the list.
     * The reminder must exist in the list.
     */
    public void remove(Event eventToRemove) {
        requireNonNull(eventToRemove);
        //System.out.println("reminder size now is " + internalList.size());
        ObservableList<Reminder> deleteInternalList = FXCollections.observableArrayList();
        for (int i = 0; i < internalList.size(); i++) {
            Reminder toRemove = internalList.get(i);
            if (toRemove.getEvent().equals(eventToRemove)) {
                deleteInternalList.add(toRemove);
            }
        }

        for (int i = 0; i < deleteInternalList.size(); i++) {
            Reminder toRemove = deleteInternalList.get(i);
            if (!internalList.remove(toRemove)) {
                throw new NotFoundException();
            }
        }
    }

    /**
     * Check whether there are reminders related to this event
     * @param eventToRemove
     * @return
     */
    public boolean isRemove(Event eventToRemove) {
        requireNonNull(eventToRemove);
        //System.out.println("reminder size now is " + internalList.size());
        for (int i = 0; i < internalList.size(); i++) {
            Reminder toRemove = internalList.get(i);
            if (toRemove.getEvent().equals(eventToRemove)) {
                return true;
            }
        }
        return false;
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

    public ObservableList<Reminder> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     *
     * @param reminders
     * @return true if {@code reminders} contains only unique reminders.
     */
    private boolean remindersAreUnique(List<Reminder> reminders) {
        for (int i = 0; i < reminders.size() - 1; i++) {
            for (int j = i + 1; j < reminders.size(); j++) {
                if (reminders.get(i).equals(reminders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
