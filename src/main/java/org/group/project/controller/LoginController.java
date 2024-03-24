package org.group.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField loginUsername;

    @FXML
    private Label resultLabel;

    @FXML
    protected void onLoginButtonClick() {
        resultLabel.setText(loginUsername.getText());
    }

}
