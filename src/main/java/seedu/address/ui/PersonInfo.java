package seedu.address.ui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import javafx.scene.image.ImageView;

public class PersonInfo extends UiPart<Region> {

    @FXML
    private ImageView photoImageView;

    @FXML
    private Label titleNameLabel;
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
    @FXML
    private Label commentLabel;

    private String DEFAULT_COMMENT = "This is a default comment";
    private static final String FXML = "PersonInfo.fxml";

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
    }
//
//    @FXML
//    public void loadPersonInfo() throws IOException {
//        AnchorPane PersonInfoPane = FXMLLoader.load(getClass().getResource("PersonInfo.fxml"));
//    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            //Image userIcon = new Image("/Users/chenyinya/Desktop/photo1");
            //okie!!   "seedu/address/view/usericon.png");
            //"/Users/chenyinya/AddressApp/src/seedu/address/view/usericon.png");


            // File source = new File("/Users/pankaj/tmp/source.avi");
            // File dest = new File("/Users/pankaj/tmp/dest.avi");

            File source = new File("/Users/chenyinya/Desktop/icon2.png");
            try {
                Image imageForFile = new Image(source.toURI().toURL().toExternalForm());
                photoImageView.setImage(imageForFile);
            }
            catch( IOException e ) { }

            //    Files.copy(source.toPath(), dest.toPath());

            // Fill the labels with info from the person object.
            titleNameLabel.setText(person.getName().toString());
            nameLabel.setText(person.getName().toString());
            addressLabel.setText(person.getAddress().toString());
            emailLabel.setText(person.getAddress().toString());
            phoneNumberLabel.setText(person.getPhone().toString());
            tagLabel.setText(person.getTags().toString());
            commentLabel.setText(DEFAULT_COMMENT);



        } else {
            // Person is null, remove all the text.
            nameLabel.setText("");
            addressLabel.setText("");
            emailLabel.setText("");
            phoneNumberLabel.setText("");
            tagLabel.setText("");
            commentLabel.setText(DEFAULT_COMMENT);
        }
    }

}




