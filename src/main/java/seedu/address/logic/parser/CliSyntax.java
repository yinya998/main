package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TAG_EXPORT = new Prefix("t/");
    public static final Prefix PREFIX_PATH = new Prefix("p/");
    public static final Prefix PREFIX_PHOTO = new Prefix("ph/");
    public static final Prefix PREFIX_FILENAME = new Prefix("n/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_VENUE = new Prefix("v/");
    public static final Prefix PREFIX_START_TIME = new Prefix("s/");
    public static final Prefix PREFIX_END_TIME = new Prefix("e/");
    public static final Prefix PREFIX_LABEL = new Prefix("l/");
    public static final Prefix PREFIX_CONTACT_INDEX = new Prefix("ci/");
    public static final Prefix PREFIX_EVENT_INDEX = new Prefix("ei/");
}
