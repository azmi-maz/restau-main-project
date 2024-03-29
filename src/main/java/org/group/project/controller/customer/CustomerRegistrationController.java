package org.group.project.controller.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.group.project.classes.DataFileStructure;
import org.group.project.classes.DataManager;
import org.group.project.classes.HelperMethods;
import org.group.project.exceptions.InvalidUserException;

import java.io.FileNotFoundException;
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
    private Label errorUsername;

    @FXML
    protected void confirmButtonClick() {

        try {
            // Clean up any error labels.
            // Need to handle css here; visibility or z-index or height
            // invisibile?
            errorUsername.setText(null);

            // This is for a new customer.
            List<String> newUserDetails = new ArrayList<>();
            List<TextField> dataRow = new ArrayList<>(Arrays.asList(firstName, lastName, address, username));
            List<String> fileRowStructure = new ArrayList<>(Arrays.asList());
            fileRowStructure.addAll(DataFileStructure.getValues("USERS"));

            for (String colName : fileRowStructure) {
                for (TextField textField : dataRow) {
                    if (textField != null && colName.equalsIgnoreCase(textField.getId())) {
                        newUserDetails.add(textField.getText());
                    }
                }
            }

            // Checks username is not duplicate
            boolean usernameAlreadyExist =
                    HelperMethods.isUsernameExist(username.getText());

            if (usernameAlreadyExist) {
                throw new InvalidUserException("Username already exist. Please " +
                        "use another username.");
            }

            // Is a customer
            newUserDetails.add(DataFileStructure.getIndexByColName("USERS",
                    "isCustomer"), "true");

            // Customer id
            newUserDetails.add(DataFileStructure.getIndexByColName("USERS",
                            "customerId"),
                    Integer.toString(HelperMethods.getNewCustomerId()));

            // Add the new user to file
            DataManager.appendNewUserToFile(newUserDetails, true);

            // Need to handle css success format.
            errorUsername.setText("successful");


        } catch (Exception e) {
            errorUsername.setText(e.getMessage());
            // Need to handle css error format.

            e.printStackTrace();
        }

    }

    @FXML
    protected void cancelButtonClick() {

    }

    public static void main(String[] args) throws FileNotFoundException {

    }
}
