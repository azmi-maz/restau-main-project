package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomerNotificationDetailsController {

    @FXML
    private VBox vbox;

    @FXML
    private Label dateStampLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private TextArea notificationTextArea;

    @FXML
    private Button returnButton;

    public void initialize() {

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    public void populateNotificationDetails(
            LocalDate notificationDate,
            LocalTime notificationTime,
            String notificationType,
            String messageBody
    ) {
        dateStampLabel.setText(
                "Date: " + notificationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + " " + notificationTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
        );

        subjectLabel.setText(
                "Subject: " + notificationType
        );

        notificationTextArea.setText(messageBody);
        notificationTextArea.setWrapText(true);
    }

    public void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
