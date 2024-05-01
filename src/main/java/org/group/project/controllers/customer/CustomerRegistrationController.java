package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.Customer;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.exceptions.InvalidUsernameException;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.main.CustomerView;

/**
 * This controller class handles the new-customer-registration fxml events.
 *
 * @author azmi_maz
 */
public class CustomerRegistrationController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField address;

    @FXML
    private TextField username;

    @FXML
    private Label errorUsernameLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private VBox newCustomerWindow;

    /**
     * This initializes the controller for the fxml.
     */
    @FXML
    public void initialize() {

        // Resets error label
        errorUsernameLabel.setVisible(false);
        errorUsernameLabel.setStyle("-fx-font-size: 1");


        confirmButton.setOnMousePressed(e -> confirmButtonClick());
        cancelButton.setOnMousePressed(e -> closeWindow());

    }

    // This method handles user registration confirmation.
    @FXML
    protected void confirmButtonClick() {

        try {

            // Clean up any error label.
            errorUsernameLabel.setText(null);
            errorUsernameLabel.setVisible(false);
            errorUsernameLabel.setStyle("-fx-font-size: 1");

            // New customer
            String customerFirstName = firstName.getText();
            String customerLastName = lastName.getText();
            String customerUsername = username.getText();
            String customerAddress = address.getText();

            UserManagement userManagement = new UserManagement();
            Customer newCustomer = userManagement.createNewCustomer(
                    customerFirstName,
                    customerLastName,
                    customerUsername,
                    customerAddress
            );

            // Checks username is not duplicate
            boolean usernameAlreadyExist = userManagement
                    .isUsernameAlreadyExist(customerUsername);
            if (usernameAlreadyExist) {
                throw new InvalidUsernameException();
            }

            if (
                    !firstName.getText().isBlank()
                            && !lastName.getText().isBlank()
                            && !username.getText().isBlank()
                            && !address.getText().isBlank()
            ) {
                // Add the new user to file and persist as active user
                userManagement.addNewCustomerToDatabase(
                        newCustomer
                );

                Main.setCurrentUser(userManagement.getActiveUser());

                AlertPopUpWindow.displayInformationWindow(
                        "Registration Successful!",
                        "Thank you for joining Cafe94, "
                                + firstName.getText() +
                                "!" + System.lineSeparator()
                                + "Feeling hangry?",
                        "Yes, I am."
                );

                CustomerView.controller.welcomeCustomer();

                Main.getStage().setScene(Main.getScenes()
                        .get(MainScenes.CUSTOMER));

                closeWindow();

            } else {

                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Please complete the form."
                );

            }

        } catch (InvalidUsernameException error) {
            errorUsernameLabel.setText(error.getMessage());
            AlertPopUpWindow.displayErrorWindow(
                    "Invalid Username",
                    error.getMessage()
            );
            errorUsernameLabel.setVisible(true);
            errorUsernameLabel.setStyle("-fx-font-size: 11");

            error.printStackTrace();
        } catch (TextFileNotFoundException | ClearFileFailedException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    @FXML
    protected void closeWindow() {
        Stage stage = (Stage) newCustomerWindow.getScene().getWindow();
        stage.close();
    }
}
