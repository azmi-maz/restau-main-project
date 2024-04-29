package org.group.project.controllers.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class UserProfileView {

    public void showWindow() {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource(
                            "smallwindows/user-edit-profile" +
                                    ".fxml"));

            VBox vbox = fxmlLoader.load();

            EditProfileController controller =
                    fxmlLoader.getController();

            controller.populateUserDetails();

            Scene editScene = new Scene(vbox,
                    WindowSize.SMALL.WIDTH,
                    WindowSize.SMALL.HEIGHT);

            Stage editStage = new Stage();
            editStage.setScene(editScene);
            // TODO Should final variable this
            editStage.setTitle("View User Profile");

            editStage.initModality(Modality.APPLICATION_MODAL);

            editStage.showAndWait();

        } catch (IOException ex) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    ex.getMessage()
            );
            ex.printStackTrace();
        }
    }
}
