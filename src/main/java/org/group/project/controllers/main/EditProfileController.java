package org.group.project.controllers.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.HelperMethods;
import org.group.project.scenes.MainScenes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class EditProfileController {

    @FXML
    private VBox vbox;

    private String userId;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private VBox addressBox;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    public void initialize() {


        saveButton.setOnAction(e -> {

            if (
                    !firstNameTextField.getText().isBlank() &&
                            !lastNameTextField.getText().isBlank() &&
                            !usernameTextField.getText().isBlank() &&
                            !addressTextField.getText().isBlank()
            ) {

                updateUserId();
                String firstName = firstNameTextField.getText().toLowerCase();
                String lastName = lastNameTextField.getText().toLowerCase();
                String username = usernameTextField.getText();
                String address = "";
                if (addressTextField.getText() != null) {
                    address = HelperMethods
                            .formatAddressToWrite(addressTextField.getText());
                }

                // TODO try catch
                try {
                    DataManager.editColumnDataByUniqueId(
                            "USERS", userId,
                            "firstName", firstName
                    );
                    DataManager.editColumnDataByUniqueId(
                            "USERS", userId,
                            "lastName", lastName
                    );
                    DataManager.editColumnDataByUniqueId(
                            "USERS", userId,
                            "username", username
                    );
                    if (addressTextField.getText() != null) {
                        DataManager.editColumnDataByUniqueId(
                                "USERS", userId,
                                "address", address
                        );
                    }
                    AlertPopUpWindow.displayInformationWindow(
                            "Info",
                            "There were changes made to the user profile. Please log in again.",
                            "Ok"
                    );
                    // TODO try catch
                    try {
                        HelperMethods.clearActiveUser();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Main.getStage().setScene(Main.getScenes().get(MainScenes.LOGIN));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                closeWindow();

            } else {

                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Please complete the form."
                );
            }

        });

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    public void populateUserDetails() throws FileNotFoundException {
        String currentUsername = Main.getCurrentUser().getUsername();
        List<String> userDetails = HelperMethods.getUserDataByUsername(currentUsername);
        String userType = userDetails.get(DataFileStructure
                .getIndexByColName("USERS", "userType"));
        firstNameTextField.setText(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "firstName")));
        lastNameTextField.setText(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "lastName")));
        usernameTextField.setText(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "username")));
        if (userType.equalsIgnoreCase("customer")) {
            addressTextField.setText(
                    HelperMethods.formatAddressToRead(userDetails.get(DataFileStructure
                            .getIndexByColName("USERS",
                                    "address"))));
            addressBox.setStyle("visibility: visible");
        } else {
            addressBox.setStyle("visibility: hidden");
        }
    }

    private void updateUserId() {

        if (Main.getCurrentUser() == null) {
            return;
        }

        // TODO try catch
        try {
            userId = String.valueOf(
                    HelperMethods
                            .findUserIdByUsername(
                                    Main.getCurrentUser().getUsername()));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
