package org.group.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.HelperMethods;
import org.group.project.scenes.MainScenes;

import java.io.IOException;

/**
 * This controller class handles login-view fxml events.
 *
 * @author azmi_maz
 */
public class LoginController {

    private Stage stage;

    @FXML
    private TextField loginUsername;

    @FXML
    private Label resultLabel;

    @FXML
    protected void onLoginButtonClick() throws IOException {

        boolean isUsernameExist =
                HelperMethods.isUsernameExist(loginUsername.getText());

        if (isUsernameExist) {

//            System.out.println(resultLabel.getStyleClass());

            // These remove error, success, hidden-label. Without removing
            // all, the style name piles up
            resultLabel.getStyleClass().removeAll("error");
            resultLabel.getStyleClass().removeAll("success");
            resultLabel.getStyleClass().removeAll("hidden-label");

            // This adds success css
            resultLabel.getStyleClass().add("success");

//            System.out.println(resultLabel.getStyleClass());
            // The success message
            resultLabel.setText("Login successful!");

            Main.getStage().setScene(Main.getScenes().get(MainScenes.CUSTOMER));


        } else {

            // The error message
            resultLabel.setText("Login unsuccessful. Please try again.");

//            System.out.println(resultLabel.getStyleClass());

            // These remove hidden-label, success and error. Without removing
            // all, the style name piles up
            resultLabel.getStyleClass().removeAll("hidden-label");
            resultLabel.getStyleClass().removeAll("success");
            resultLabel.getStyleClass().removeAll("error");

            // The adds the error css
            resultLabel.getStyleClass().add("error");
//            System.out.println(resultLabel.getStyleClass());
        }
    }



}
