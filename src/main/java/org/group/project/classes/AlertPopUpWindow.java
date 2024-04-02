package org.group.project.classes;

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
}
