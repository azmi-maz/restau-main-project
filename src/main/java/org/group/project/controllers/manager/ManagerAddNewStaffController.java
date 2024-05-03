package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.Manager;
import org.group.project.classes.Staff;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;

/**
 * This class allows manager to add new staff.
 *
 * @author azmi_maz
 */
public class ManagerAddNewStaffController {
    private static final String ADD_STAFF_TITLE = "New Staff";
    private static final String ADD_STAFF_MESSAGE = "%s was added " +
            "successfully.";
    private static final String OK = "Ok";
    private static final String INCOMPLETE = "Please complete the form.";
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

                Manager manager = (Manager) MainScenesMap.getCurrentUser();

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
                                ADD_STAFF_TITLE,
                                String.format(
                                        ADD_STAFF_MESSAGE,
                                        newStaff.getDataForListDisplay()
                                ), OK
                        );
                    }

                    closeWindow();

                } else {
                    AlertPopUpWindow.displayErrorWindow(
                            INCOMPLETE
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
