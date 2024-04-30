package org.group.project.classes.auxiliary;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class informs the user for any success or failure of requests made.
 *
 * @author azmi_maz
 */
public class AlertPopUpWindow {

    /**
     * This display information for users to acknowledge.
     *
     * @param title      - the title of the alert window.
     * @param content    - the main message.
     * @param buttonName - the button text, e.g. Ok
     */
    public static void displayInformationWindow(String title, String content
            , String buttonName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().set(0, new ButtonType(buttonName,
                ButtonBar.ButtonData.OK_DONE));
        alert.showAndWait();
    }

    /**
     * This display a choice for the user to choose from; either
     * answer yes or no to the question asked.
     *
     * @param title   - the title of the alert window.
     * @param content - the main message.
     * @return the selected response as button type.
     */
    public static Optional<ButtonType> displayConfirmationWindow(String title,
                                                                 String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().set(0,
                new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE));
        alert.getButtonTypes().set(1,
                new ButtonType("No", ButtonBar.ButtonData.NO));
        return alert.showAndWait();
    }

    /**
     * This display a choice for user to display or delete reports.
     *
     * @param title   - the title of the alert window.
     * @param content - the main message.
     * @return the selected response as button type.
     */
    public static Optional<ButtonType> displayChoiceWindow(String title,
                                                           String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().set(0,
                new ButtonType("Display", ButtonBar.ButtonData.OK_DONE));
        alert.getButtonTypes().set(1,
                new ButtonType("Delete", ButtonBar.ButtonData.NO));
        return alert.showAndWait();
    }

    /**
     * This display errors that was caught by the try-catch.
     *
     * @param title   - the title of the alert.
     * @param content - the main message of the error.
     */
    public static void displayErrorWindow(String title,
                                          String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
