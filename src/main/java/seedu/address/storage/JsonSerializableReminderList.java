package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.reminder.Reminder;

/**
 * An Immutable ReminderList that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
public class JsonSerializableReminderList {

    public static final String MESSAGE_DUPLICATE_REMINDER = "Reminders list contains duplicate reminder(s).";

    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given reminders.
     */
    @JsonCreator
    public JsonSerializableReminderList(@JsonProperty("reminders") List<JsonAdaptedReminder> reminders) {
        this.reminders.addAll(reminders);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableReminderList(ReadOnlyAddressBook source) {
        reminders.addAll(source.getReminderList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
    }

    public List<JsonAdaptedReminder> getReminders() {
        return reminders;
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook reminderList = new AddressBook();

        for (JsonAdaptedReminder jsonAdaptedReminder : reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();

            if (reminderList.hasReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REMINDER);
            }
            reminderList.addReminder(reminder);
        }

        return reminderList;
    }

}
