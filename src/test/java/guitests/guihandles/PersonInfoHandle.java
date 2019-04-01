package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class PersonInfoHandle extends NodeHandle<Node> {

    public static final String PERSON_INFO_ID = "#personInfo";

    public PersonInfoHandle(Node personInfoNode) {
        super(personInfoNode);
    }

}
