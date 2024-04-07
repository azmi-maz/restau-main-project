package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.DataManager;
import org.group.project.classes.HelperMethods;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerAddNewStaffController {

    @FXML
    private VBox vbox;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private ChoiceBox<String> positionChoiceBox;

    @FXML
    private Button addStaffButton;

    @FXML
    private Button cancelButton;

    public void initialize() {

        // TODO enum this?
        positionChoiceBox.getItems().add("Chef");
        positionChoiceBox.getItems().add("Driver");
        positionChoiceBox.getItems().add("Waiter");

        addStaffButton.setOnAction(e -> {

            int getNewUserId = -1;
            try {
                getNewUserId = HelperMethods.getNewUserId();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String newUserId = String.valueOf(getNewUserId);
            String firstName = firstNameTextField.getText().toLowerCase();
            String lastName = lastNameTextField.getText().toLowerCase();
            String username = usernameTextField.getText();
            String userType = positionChoiceBox.getValue().toLowerCase();

            // TODO review this
            List<String> newUser = new ArrayList<>(Arrays.asList(
                    newUserId,
                    firstName,
                    lastName,
                    username,
                    userType,
                    "", "44", "0", "false", "false", "0"
            ));

            // TODO try catch
            try {
                DataManager.appendDataToFile(
                        "USERS",
                        newUser);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            closeWindow();

        });

        cancelButton.setOnAction(e -> {
            closeWindow();
        });

    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
