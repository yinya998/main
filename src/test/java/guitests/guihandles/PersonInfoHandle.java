package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class PersonInfoHandle extends NodeHandle<Node> {

    public static final String PERSON_INFO_ID = "#personInfo";

    public PersonInfoHandle(Node personInfoNode) {
        super(personInfoNode);

    }

}
