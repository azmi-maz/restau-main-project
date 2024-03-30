package org.group.project.classes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * This class informs the user for any success or failure of requests made.
 *
 * @author azmi_maz
 */
public class AlertPopUpWindow {

    public static void displayConfirmationWindow(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().set(0, new ButtonType("Yes, I am.",
                ButtonBar.ButtonData.OK_DONE));
        alert.showAndWait();
    }
}
