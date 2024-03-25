package org.group.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.group.project.classes.HelperMethods;

import java.io.IOException;

public class LoginController {

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
