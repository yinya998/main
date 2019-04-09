package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

/**
 * The person info display person info
 */
public class EventInfo extends UiPart<Region> {

    public static final String FXML = "EventInfo.fxml";
    public static final String DEFAULT_DESCRIPTION = "This is a default description";

    /*
    @FXML
    private Label titleNameLabel;
    */
    @FXML
    private Label nameLabel;
    @FXML
    private Label venueLabel;
    @FXML
    private Label tagLabel;
    @FXML
    private Label startsOnLabel;
    @FXML
    private Label endsOnLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label participantsLabel;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Logger logger = LogsCenter.getLogger(getClass());


    public EventInfo(ObservableValue<Event> selectedEvent) {
        super(FXML);
        showEventDetails(null);
        // Load person page when selected person changes.
        selectedEvent.addListener((observable, oldValue, newValue) ->
                showEventDetails(newValue));
        nameLabel.setWrapText(true);
        participantsLabel.setWrapText(true);
        descriptionLabel.setWrapText(true);
        venueLabel.setWrapText(true);
        tagLabel.setWrapText(true);
    }

    /**
     * Fills all text fields to show details about the event.
     * If the specified event is null, all text fields are cleared.
     *
     * @param event the event or null
     */
    private void showEventDetails(Event event) {
        if (event != null) {
            // Fill the labels with info from the person object.
            // titleNameLabel.setText(event.getName().toString());
            nameLabel.setText(event.getName().toString());
            venueLabel.setText(event.getVenue().toString());
            startsOnLabel.setText(event.getStartDateTime().toString());
            endsOnLabel.setText(event.getEndDateTime().toString());
            tagLabel.setText(event.getLabel().toString());
            descriptionLabel.setText(event.getDescription().toString());
            participantsLabel.setText(event.getPersons()
                    .stream()
                    .map(x -> x.getName().toString())
                    .reduce((x, y) -> x + ", " + y)
                    .orElse("-"));
        } else {
            // Event is null, remove all the text.
            // titleNameLabel.setText("Select event to view details.");
            nameLabel.setText("Select event to view details.");
            venueLabel.setText("-");
            startsOnLabel.setText("-");
            endsOnLabel.setText("-");
            tagLabel.setText("-");
            descriptionLabel.setText("-");
            participantsLabel.setText("-");
        }
    }
}
