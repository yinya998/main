package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * This abstract class allows for different types of ListPanels to be created.
 */
public abstract class ListPanel extends UiPart<Region> {

    ListPanel(String fxml) {
        super(fxml);
    }

}
