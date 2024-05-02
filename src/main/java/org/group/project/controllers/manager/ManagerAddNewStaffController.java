package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.Manager;
import org.group.project.classes.Staff;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

/**
 * This class allows manager to add new staff.
 *
 * @author azmi_maz
 */
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        try {

            UserManagement userManagement = new UserManagement();
            userManagement.updateStaffTypeChoiceBox(
                    positionChoiceBox
            );

            addStaffButton.setOnAction(e -> {

                Manager manager = (Manager) Main.getCurrentUser();

                if (
                        !firstNameTextField.getText().isBlank() &&
                                !lastNameTextField.getText().isBlank() &&
                                !usernameTextField.getText().isBlank() &&
                                positionChoiceBox.getValue() != null
                ) {

                    String firstName = firstNameTextField
                            .getText().toLowerCase();
                    String lastName = lastNameTextField
                            .getText().toLowerCase();
                    String username = usernameTextField.getText();
                    String userType = positionChoiceBox
                            .getValue().toLowerCase();

                    Staff newStaff = userManagement.createNewStaff(
                            firstName,
                            lastName,
                            username,
                            userType
                    );

                    boolean isSuccessful = false;

                    try {

                        isSuccessful = manager.addNewStaffMember(
                                userManagement,
                                newStaff
                        );

                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }

                    if (isSuccessful) {
                        AlertPopUpWindow.displayInformationWindow(
                                "New Staff",
                                String.format(
                                        "%s was added successfully.",
                                        newStaff.getDataForListDisplay()
                                ), "Ok"
                        );
                    }

                    closeWindow();

                } else {
                    AlertPopUpWindow.displayErrorWindow(
                            "Please complete the form."
                    );
                }

            });

        } catch (TextFileNotFoundException ex) {
            AlertPopUpWindow.displayErrorWindow(
                    ex.getMessage()
            );
            ex.printStackTrace();
        }

        cancelButton.setOnAction(e -> {
            closeWindow();
        });

    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
