package org.group.project.controllers.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.Customer;
import org.group.project.classes.User;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenes;

/**
 * This class allows users to edit their profile details.
 *
 * @author azmi_maz
 */
public class EditProfileController {

    @FXML
    private VBox vbox;

    private int userId;

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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {


        saveButton.setOnAction(e -> {

            if (
                    !firstNameTextField.getText().isBlank() &&
                            !lastNameTextField.getText().isBlank() &&
                            !usernameTextField.getText().isBlank()
            ) {

                updateUserId();
                String firstName = firstNameTextField.getText().toLowerCase();
                String lastName = lastNameTextField.getText().toLowerCase();
                String username = usernameTextField.getText();
                String address = "";
                if (addressTextField.getText() != null) {
                    address = addressTextField.getText();
                }

                UserManagement userManagement = null;
                try {
                    userManagement = new UserManagement();
                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

                try {
                    if (addressTextField.getText() != null) {
                        userManagement.editExistingUserProfile(
                                userId,
                                firstName,
                                lastName,
                                username,
                                address
                        );
                    } else {
                        userManagement.editExistingUserProfile(
                                userId,
                                firstName,
                                lastName,
                                username
                        );
                    }
                    AlertPopUpWindow.displayInformationWindow(
                            "Info",
                            "There were changes made to the user " +
                                    "profile. Please log in again.",
                            "Ok"
                    );

                    // Log off by removing active user info
                    try {
                        DataManager.clearFileData("ACTIVE_USER");
                    } catch (ClearFileFailedException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }

                    Main.getStage().setScene(Main.getScenes()
                            .get(MainScenes.LOGIN));

                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

                closeWindow();

            } else {

                AlertPopUpWindow.displayErrorWindow(
                        "Please complete the form."
                );
            }

        });

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    /**
     * This method populates the current user details.
     */
    public void populateUserDetails() {

        UserManagement userManagement = null;
        try {

            userManagement = new UserManagement();

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        String currentUsername = Main.getCurrentUser().getUsername();
        User currentUser = userManagement.getUserByUsername(
                currentUsername);

        String userType = userManagement.getStaffClass
                (currentUser).toLowerCase();
        firstNameTextField.setText(
                currentUser.getFirstNameForDisplay());
        lastNameTextField.setText(
                currentUser.getLastNameForDisplay());
        usernameTextField.setText(
                currentUser.getUsername());
        if (userType.equalsIgnoreCase("customer")) {
            Customer customer = (Customer) currentUser;
            addressTextField.setText(
                    customer.getDeliveryAddressToRead()
            );
            addressBox.setStyle("visibility: visible");
        } else {
            addressBox.setStyle("visibility: hidden");
        }
    }

    private void updateUserId() {

        if (Main.getCurrentUser() == null) {
            return;
        }

        try {
            UserManagement userManagement = new UserManagement();
            userId = userManagement.getUserIdByUsername(
                    Main.getCurrentUser().getUsername());
        } catch (TextFileNotFoundException ex) {
            AlertPopUpWindow.displayErrorWindow(
                    ex.getMessage()
            );
            ex.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
