package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.DataManager;
import org.group.project.classes.ImageLoader;

import java.io.IOException;

public class ManagerStaffManagementDetailsController {

    @FXML
    private VBox vbox;

    @FXML
    private VBox hoursLeftVbox;

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

    public void initialize() {

        setTextFieldToDisabled();

        ImageLoader.setUpGraphicButton(incrementHoursLeftButton, 15, 15, "arrow-up");
        ImageLoader.setUpGraphicButton(decrementHoursLeftButton, 15, 15, "arrow-down");

        // TODO enum this?
        positionChoiceBox.getItems().add("Chef");
        positionChoiceBox.getItems().add("Driver");
        positionChoiceBox.getItems().add("Waiter");

        incrementHoursLeftButton.setOnAction(e -> {
            incrementHoursLeft();
            setButtonVisibility();
        });

        decrementHoursLeftButton.setOnAction(e -> {
            decrementHoursLeft();
            setButtonVisibility();
        });

        saveButton.setOnAction(e -> {

            String firstName = firstNameTextField.getText().toLowerCase();
            String lastName = lastNameTextField.getText().toLowerCase();
            String username = usernameTextField.getText();
            String hoursLeft = hoursLeftTextField.getText();
            String totalHoursWorked = totalHoursWorkedTextField.getText();
            String position = positionChoiceBox.getValue().toLowerCase();

            // TODO handle try catch
            try {
                DataManager.editColumnDataByUniqueId("USERS", userId,
                        "firstName", firstName);
                DataManager.editColumnDataByUniqueId("USERS", userId,
                        "lastName", lastName);
                DataManager.editColumnDataByUniqueId("USERS", userId,
                        "username", username);
                DataManager.editColumnDataByUniqueId("USERS", userId,
                        "numOfHoursToWork", hoursLeft);
                DataManager.editColumnDataByUniqueId("USERS", userId,
                        "numOfTotalHoursWorked", totalHoursWorked);
                DataManager.editColumnDataByUniqueId("USERS", userId,
                        "userType", position);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            closeWindow();

        });

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    public void setStaffDetails(
            String userId,
            String firstName,
            String lastName,
            String username,
            int hoursLeft,
            int totalHoursWorked,
            String position
    ) {
        this.userId = userId;
        firstNameTextField.setText(firstName);
        lastNameTextField.setText(lastName);
        usernameTextField.setText(username);
        hoursLeftTextField.setText(String.valueOf(hoursLeft));
        totalHoursWorkedTextField.setText(String.valueOf(totalHoursWorked));
        positionChoiceBox.setValue(position.substring(0, 1).toUpperCase()
                + position.substring(1));
        setButtonVisibility();
    }

    public void incrementHoursLeft() {
        int currentHour = Integer.parseInt(hoursLeftTextField.getText());
        currentHour++;
        hoursLeftTextField.setText(String.valueOf(currentHour));

        int currentTotalHoursWorked = Integer.parseInt(totalHoursWorkedTextField.getText());
        currentTotalHoursWorked--;
        totalHoursWorkedTextField.setText(String.valueOf(currentTotalHoursWorked));
    }

    public void decrementHoursLeft() {
        int currentHour = Integer.parseInt(hoursLeftTextField.getText());
        currentHour--;
        hoursLeftTextField.setText(String.valueOf(currentHour));

        int currentTotalHoursWorked = Integer.parseInt(totalHoursWorkedTextField.getText());
        currentTotalHoursWorked++;
        totalHoursWorkedTextField.setText(String.valueOf(currentTotalHoursWorked));
    }

    public void setButtonVisibility() {
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

    public void setTextFieldToDisabled() {
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
