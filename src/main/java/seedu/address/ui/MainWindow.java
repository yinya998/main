package seedu.address.ui;

import static seedu.address.ui.WindowViewState.EVENTS;
import static seedu.address.ui.WindowViewState.PERSONS;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.WrongViewException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private ListPanel listPanel;
    private ListPanel listPanel2;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private WindowViewState currentState;
    private PersonInfo personInfo;
    private EventInfo eventInfo;
    private boolean showFullReminder;
    @FXML
    private StackPane dataDetailsPanelPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane listPanel2Placeholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.currentState = PERSONS;
        this.showFullReminder = false;
        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            //if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
            //   menuItem.getOnAction().handle(new ActionEvent());
            //    event.consume();
            //}
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personInfo = new PersonInfo(logic.selectedPersonProperty());
        eventInfo = new EventInfo(logic.selectedEventProperty());
        resetView();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath(), logic.getAddressBook());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

    }

    /**
     * Switches the view of the UI when the switch command is entered.
     */
    void handleSwitch() {
        if (this.currentState == PERSONS) {
            this.currentState = EVENTS;
        } else {
            this.currentState = PERSONS;
        }
        resetView();
    }

    /**
     * handle the view of reminder list.
     * @param isShowFullReminder
     */
    void handleShowFullReminder(boolean isShowFullReminder) {
        if (this.showFullReminder != isShowFullReminder) {
            this.showFullReminder = isShowFullReminder;
            resetView();
        }
    }

    /**
     * Resets the view given the current state of the UI.
     */
    void resetView() {
        listPanelPlaceholder.getChildren().clear();
        dataDetailsPanelPlaceholder.getChildren().clear();

        if (!showFullReminder) {
            listPanel2 = new ReminderListPanel(logic.getFilteredReminderList(), logic.selectedReminderProperty(),
                    logic::setSelectedReminder);
            listPanel2Placeholder.getChildren().add(listPanel2.getRoot());
        } else {
            listPanel2 = new ReminderListFullPanel(logic.getFilteredReminderList(), logic.selectedReminderProperty(),
                    logic::setSelectedReminder);
            listPanel2Placeholder.getChildren().add(listPanel2.getRoot());
        }
        if (currentState == PERSONS) {
            dataDetailsPanelPlaceholder.getChildren().add(personInfo.getRoot());
            listPanel = new PersonListPanel(logic.getFilteredPersonList(), logic.selectedPersonProperty(),
                    logic::setSelectedPerson);
            listPanelPlaceholder.getChildren().add(listPanel.getRoot());
        } else if (currentState == EVENTS) {
            dataDetailsPanelPlaceholder.getChildren().add(eventInfo.getRoot());
            listPanel = new EventListPanel(logic.getFilteredEventList(), logic.selectedEventProperty(),
                    logic::setSelectedEvent);
            listPanelPlaceholder.getChildren().add(listPanel.getRoot());
        }
    }

    void handlePersonCommand() {
        this.currentState = PERSONS;
        resetView();
    }

    void handleEventCommand() {
        this.currentState = EVENTS;
        resetView();
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public ListPanel getListPanel() {
        return listPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String, WindowViewState)
     */
    private CommandResult executeCommand(String commandText)
            throws CommandException, ParseException, WrongViewException {
        try {
            CommandResult commandResult = logic.execute(commandText, currentState);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            handleShowFullReminder(commandResult.isShowFullReminder());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isSwitchView()) {
                handleSwitch();
            }
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (WrongViewException wve) {
            logger.info("Cannot run " + commandText + " in this view.");
            resultDisplay.setFeedbackToUser(wve.getMessage());
            handleSwitch();
            throw wve;
        }
    }

    public WindowViewState getViewState() {
        return this.currentState;
    }
}
