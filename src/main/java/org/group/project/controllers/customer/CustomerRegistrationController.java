package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.HelperMethods;
import org.group.project.exceptions.InvalidUserException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.main.CustomerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @FXML
    protected void confirmButtonClick() {

        try {
            // Clean up any error label.
            errorUsernameLabel.setText(null);
            errorUsernameLabel.setVisible(false);
            errorUsernameLabel.setStyle("-fx-font-size: 1");

            // New customer
            List<String> newUserDetails = new ArrayList<>();
            List<TextField> dataRow = new ArrayList<>(Arrays.asList(firstName,
                    lastName, username, address));
            List<String> fileRowStructure = new ArrayList<>(Arrays.asList());
            fileRowStructure.addAll(DataFileStructure.getValues("USERS"));

            boolean isColumnMatched = false;
            for (String colName : fileRowStructure) {
                for (TextField textField : dataRow) {
                    if (textField != null && colName
                            .equalsIgnoreCase(textField.getId())) {
                        if (textField.getId().equalsIgnoreCase("address")) {
                            newUserDetails.add(HelperMethods
                                    .formatAddressToWrite(textField.getText()));
                        } else {
                            newUserDetails.add(textField.getText());
                        }
                        isColumnMatched = true;
                    }
                }
                if (!isColumnMatched) {
                    newUserDetails.add("");
                    isColumnMatched = false;
                }
            }

            // Checks username is not duplicate
            boolean usernameAlreadyExist =
                    HelperMethods.isUsernameExist(username.getText());
            if (usernameAlreadyExist) {
                throw new InvalidUserException("Username already exist. " +
                        "Please " +
                        "use another username.");
            }

            // Is a customer
            newUserDetails.add(DataFileStructure.getIndexByColName("USERS",
                    "userType"), "customer");

            // Customer id
            newUserDetails.set(DataFileStructure.getIndexByColName("USERS",
                            "userId"),
                    Integer.toString(HelperMethods.getNewUserId()));

            // Not applicable for customer
            newUserDetails.addAll(Arrays.asList("0", "0", "false", "false",
                    "0"));

            if (
                    !firstName.getText().isBlank()
                            && !lastName.getText().isBlank()
                            && !username.getText().isBlank()
                            && !address.getText().isBlank()
            ) {
                // Add the new user to file
                DataManager.appendDataToFile("USERS", newUserDetails);

                // Set the new user as the active user
                List<String> currentUser = new ArrayList<>(Arrays.asList(
                        newUserDetails
                                .get(DataFileStructure.getIndexColOfUniqueId("USERS")),
                        newUserDetails.get(DataFileStructure
                                .getIndexByColName("USERS", "firstName")),
                        newUserDetails.get(DataFileStructure
                                .getIndexByColName("USERS", "lastName")),
                        newUserDetails.get(DataFileStructure
                                .getIndexByColName("USERS", "username")),
                        newUserDetails.get(DataFileStructure
                                .getIndexByColName("USERS", "userType"))
                ));

                DataManager.appendDataToFile("ACTIVE_USER", currentUser);

                Main.setCurrentUser(HelperMethods.getActiveUser());

                AlertPopUpWindow.displayInformationWindow(
                        "Registration Successful!",
                        "Thank you for joining Cafe94, " + firstName.getText() +
                                "!" + System.lineSeparator() + "Feeling hangry?",
                        "Yes, I am."
                );

                CustomerView.controller.welcomeCustomer();

                Main.getStage().setScene(Main.getScenes().get(MainScenes.CUSTOMER));


                closeWindow();

            } else {

                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Please complete the form."
                );

            }

        } catch (Exception error) {
            errorUsernameLabel.setText(error.getMessage());
            errorUsernameLabel.setVisible(true);
            errorUsernameLabel.setStyle("-fx-font-size: 11");

            error.printStackTrace();
        }
    }

    @FXML
    protected void closeWindow() {
        Stage stage = (Stage) newCustomerWindow.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {

        // Reset error label
        errorUsernameLabel.setVisible(false);
        errorUsernameLabel.setStyle("-fx-font-size: 1");


        confirmButton.setOnMousePressed(e -> confirmButtonClick());
        cancelButton.setOnMousePressed(e -> closeWindow());

    }
}
