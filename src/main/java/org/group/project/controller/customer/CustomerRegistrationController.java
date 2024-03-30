package org.group.project.controller.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.AlertPopUpWindow;
import org.group.project.classes.DataFileStructure;
import org.group.project.classes.DataManager;
import org.group.project.classes.HelperMethods;
import org.group.project.exceptions.InvalidUserException;
import org.group.project.scenes.MainScenes;

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

            // Add the new user to file
            DataManager.appendNewUserToFile(newUserDetails);

            AlertPopUpWindow.displayConfirmationWindow(
                    "Registration Successful!",
                    "Thank you for joining Cafe94, " + firstName.getText() +
                            "!" +"\n" + "Feeling hangry?"
            );

            closeWindow();

            Main.getStage().setScene(Main.getScenes().get(MainScenes.CUSTOMER));

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
