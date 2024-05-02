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
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;

/**
 * This class allows the manager to view the selected staff details.
 *
 * @author azmi_maz
 */
public class ManagerStaffManagementDetailsController {

    @FXML
    private VBox vbox;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField hoursLeftTextField;

    @FXML
    private TextField totalHoursWorkedTextField;

    @FXML
    private ChoiceBox<String> positionChoiceBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button incrementHoursLeftButton;

    @FXML
    private Button decrementHoursLeftButton;

    @FXML
    private Button returnButton;

    private String userId;

    private static final int MAX_STAFF_HOURS = 44;
    private static final int MIN_STAFF_HOURS = 0;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        setTextFieldToDisabled();

        try {

            UserManagement userManagement = new UserManagement();

            ImageLoader.setUpGraphicButton(incrementHoursLeftButton,
                    15, 15, "arrow-up");
            ImageLoader.setUpGraphicButton(decrementHoursLeftButton,
                    15, 15, "arrow-down");

            userManagement.updateStaffTypeChoiceBox(
                    positionChoiceBox
            );

            incrementHoursLeftButton.setOnAction(e -> {
                incrementHoursLeft();
                setButtonVisibility();
            });

            decrementHoursLeftButton.setOnAction(e -> {
                decrementHoursLeft();
                setButtonVisibility();
            });

            saveButton.setOnAction(e -> {
                Manager manager = (Manager) Main.getCurrentUser();

                String firstName = firstNameTextField.getText().toLowerCase();
                String lastName = lastNameTextField.getText().toLowerCase();
                String username = usernameTextField.getText();
                String hoursLeft = hoursLeftTextField.getText();
                String totalHoursWorked = totalHoursWorkedTextField.getText();
                String position = positionChoiceBox.getValue().toLowerCase();

                boolean isSuccessful = false;
                try {
                    isSuccessful = manager.editStaffMemberDetails(
                            userManagement,
                            userId,
                            firstName,
                            lastName,
                            username,
                            hoursLeft,
                            totalHoursWorked,
                            position
                    );
                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

                if (isSuccessful) {
                    AlertPopUpWindow.displayInformationWindow(
                            "Staff Detail Edit",
                            "Edit was done successfully.",
                            "Ok"
                    );
                }

                closeWindow();

            });

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    /**
     * This method populates the selected staff details.
     *
     * @param userId - the selected staff user id.
     * @param staff  - the selected staff.
     */
    public void setStaffDetails(
            String userId,
            Staff staff
    ) {
        this.userId = userId;
        firstNameTextField.setText(
                staff.getFirstNameForDisplay()
        );
        lastNameTextField.setText(
                staff.getLastNameForDisplay()
        );
        usernameTextField.setText(
                staff.getUsername()
        );
        hoursLeftTextField.setText(String.valueOf(
                staff.getNumOfHoursToWork()
        ));
        totalHoursWorkedTextField.setText(String.valueOf(
                staff.getNumOfTotalHoursWorked()
        ));

        UserManagement userManagement = null;
        try {
            userManagement = new UserManagement();
        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        String staffType = userManagement.getStaffClass(
                staff
        );
        positionChoiceBox.setValue(staffType.substring(0, 1).toUpperCase()
                + staffType.substring(1));
        setButtonVisibility();
    }

    private void incrementHoursLeft() {
        int currentHour = Integer.parseInt(hoursLeftTextField.getText());
        currentHour++;
        hoursLeftTextField.setText(String.valueOf(currentHour));

        int currentTotalHoursWorked = Integer.parseInt(
                totalHoursWorkedTextField.getText());
        currentTotalHoursWorked--;
        totalHoursWorkedTextField.setText(String.valueOf(
                currentTotalHoursWorked));
    }

    private void decrementHoursLeft() {
        int currentHour = Integer.parseInt(hoursLeftTextField.getText());
        currentHour--;
        hoursLeftTextField.setText(String.valueOf(currentHour));

        int currentTotalHoursWorked = Integer.parseInt(
                totalHoursWorkedTextField.getText());
        currentTotalHoursWorked++;
        totalHoursWorkedTextField.setText(String.valueOf(
                currentTotalHoursWorked));
    }

    private void setButtonVisibility() {
        int currentHour = Integer.parseInt(hoursLeftTextField.getText());
        if (currentHour == MAX_STAFF_HOURS) {
            incrementHoursLeftButton.setVisible(false);
        } else {
            incrementHoursLeftButton.setVisible(true);
        }

        if (currentHour == MIN_STAFF_HOURS) {
            decrementHoursLeftButton.setVisible(false);
        } else {
            decrementHoursLeftButton.setVisible(true);
        }
    }

    private void setTextFieldToDisabled() {
        hoursLeftTextField.setOnMousePressed(e -> {
            hoursLeftTextField.setDisable(true);
        });

        hoursLeftTextField.setOnMouseReleased(e -> {
            hoursLeftTextField.setDisable(false);
        });

        totalHoursWorkedTextField.setOnMousePressed(e -> {
            totalHoursWorkedTextField.setDisable(true);
        });

        totalHoursWorkedTextField.setOnMouseReleased(e -> {
            totalHoursWorkedTextField.setDisable(false);
        });

    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }


}
