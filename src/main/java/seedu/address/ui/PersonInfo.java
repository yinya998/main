package seedu.address.ui;

import static seedu.address.model.person.Photo.DEFAULT_PHOTOPATH;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * The person info display person info
 */
public class PersonInfo extends UiPart<Region> {

    public static final String FXML = "PersonInfo.fxml";
    public static final String DEFAULT_COMMENT = "This is a default comment";

    @FXML
    private ImageView photoImageView;
    /*
    @FXML
    private Label titleNameLabel;
    */
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label tagLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumberLabel;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Logger logger = LogsCenter.getLogger(getClass());


    public PersonInfo(ObservableValue<Person> selectedPerson) {
        super(FXML);
        showPersonDetails(null);
        // Load person page when selected person changes.
        selectedPerson.addListener((observable, oldValue, newValue) ->
                showPersonDetails(newValue));
        nameLabel.setWrapText(true);
        addressLabel.setWrapText(true);
        //titleNameLabel.setWrapText(true);
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            //todo no photo
            //displayPhoto(person.getPhoto().toString());
            // Fill the labels with info from the person object.
            //titleNameLabel.setText(person.getName().toString());
            nameLabel.setText(person.getName().toString());
            addressLabel.setText(person.getAddress().toString());
            emailLabel.setText(person.getEmail().toString());
            phoneNumberLabel.setText(person.getPhone().toString());
            tagLabel.setText(person.getTags().toString());

        } else {
            displayPhoto(DEFAULT_PHOTOPATH);
            // Person is null, remove all the text.
            //titleNameLabel.setText("Select person to view details.");
            nameLabel.setText("Select person to view details.");
            addressLabel.setText("-");
            emailLabel.setText("-");
            phoneNumberLabel.setText("-");
            tagLabel.setText("-");
        }
    }

    /**
     * display photo
     */
    public void displayPhoto(String sourceString) {
        // Image userPhoto =new Image("file: src/main/resources/images/userPhotos/photo2.png");
        // photoImageView.setImage(userPhoto);

        // File source = new File("   /Users/chenyinya/Desktop/photo3.jpeg
        // File dest = new File("/Users/pankaj/tmp/dest.avi");
        // Files.copy(source.toPath(), dest.toPath());

        try {
            File source = new File(sourceString);
            Image imageForFile = new Image(source.toURI().toURL().toExternalForm());
            photoImageView.setImage(imageForFile);
        } catch (IOException e) {
            e.getMessage();
        }


    }

}




