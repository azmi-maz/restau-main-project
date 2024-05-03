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

/**
 * This class allows the customer to view notification details.
 *
 * @author azmi_maz
 */
public class CustomerNotificationDetailsController {
    private static final String DATE = "Date: ";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String SUBJECT = "Subject: ";
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    /**
     * This method is used to populate the selected notification details.
     *
     * @param notificationDate - the date of the notification.
     * @param notificationTime - the time of the notification.
     * @param notificationType - the type of notification.
     * @param messageBody      - the main message.
     */
    public void populateNotificationDetails(
            LocalDate notificationDate,
            LocalTime notificationTime,
            String notificationType,
            String messageBody
    ) {
        dateStampLabel.setText(
                DATE + notificationDate.format(
                        DateTimeFormatter.ofPattern(DATE_FORMAT))
                        + " " + notificationTime.format(
                        DateTimeFormatter.ofPattern(TIME_FORMAT))
        );

        subjectLabel.setText(
                SUBJECT + notificationType
        );

        notificationTextArea.setText(messageBody);
        notificationTextArea.setWrapText(true);
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
