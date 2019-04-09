package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.Reminder;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class ReminderCard extends UiPart<Region> {
    private static final String FXML = "ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reminder reminder;

    @FXML
    private Label name;

    @FXML
    private Label venue;

    @FXML
    private Label message;

    @FXML
    private Label interval;

    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        name.setText(reminder.getEvent().getName().toString());
        message.setText(reminder.getMessage());
        interval.setText(reminder.getInterval().toString());
        venue.setText(reminder.getEvent().getVenue().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return reminder.getMessage().equals(card.reminder.getMessage())
                && reminder.getEvent().equals(card.reminder.getEvent())
                && reminder.getInterval().equals(card.reminder.getInterval());
    }

}
