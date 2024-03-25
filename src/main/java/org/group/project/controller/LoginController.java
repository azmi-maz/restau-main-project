package org.group.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.group.project.classes.User;
import org.group.project.test.generators.CustomerGenerator;

public class LoginController {

    @FXML
    private TextField loginUsername;

    @FXML
    private Label resultLabel;

    @FXML
    protected void onLoginButtonClick() throws InterruptedException {
        // This is for testing only!
        User user = CustomerGenerator.createCustomer1();

        if (loginUsername.getText().equalsIgnoreCase(user.getUsername())){
            System.out.println(resultLabel.getStyleClass());
            resultLabel.getStyleClass().removeAll("error");
            resultLabel.getStyleClass().removeAll("success");
            resultLabel.getStyleClass().removeAll("hidden-label");
            resultLabel.getStyleClass().add("success");
            System.out.println(resultLabel.getStyleClass());
            resultLabel.setText("Login successful!");
        } else {
            resultLabel.setText("Login unsuccessful. Please try again.");
//            resultLabel.getStylesheets()
//            resultLabel.getStylesheets().add("");
//            resultLabel.setVisible(false);
//            System.out.println(resultLabel.getClass());
            System.out.println(resultLabel.getStyleClass());
            resultLabel.getStyleClass().removeAll("hidden-label");
            resultLabel.getStyleClass().removeAll("success");
            resultLabel.getStyleClass().removeAll("error");
            resultLabel.getStyleClass().add("error");
            System.out.println(resultLabel.getStyleClass());
        }
    }

}
