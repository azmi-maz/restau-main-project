package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.DataManager;
import org.group.project.classes.HelperMethods;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerAddBookingController {

    @FXML
    private VBox vbox;

    @FXML
    private DatePicker reservationDatePicker;

    @FXML
    private ChoiceBox reservationTimeChoiceBox;

    @FXML
    private ChoiceBox numOfGuestsChoiceBox;

    @FXML
    private ChoiceBox lenOfReservationTimeChoiceBox;

    @FXML
    private ChoiceBox tablePreferenceChoiceBox;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private String userId;

    public void initialize() {

        // TODO enum this?
        reservationTimeChoiceBox.getItems().add(LocalTime.of(10, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(10, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(11, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(11, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(12, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(12, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(13, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(13, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(14, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(14, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(15, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(15, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(16, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(16, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(17, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(17, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(18, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(18, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(19, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(19, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(20, 00));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(20, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(21, 00));

        // TODO
        numOfGuestsChoiceBox.getItems().add("2");
        numOfGuestsChoiceBox.getItems().add("4");
        numOfGuestsChoiceBox.getItems().add("8");
        numOfGuestsChoiceBox.getItems().add("10");


        lenOfReservationTimeChoiceBox.getItems().add("1");
        lenOfReservationTimeChoiceBox.getItems().add("2");
        lenOfReservationTimeChoiceBox.getItems().add("3");
        lenOfReservationTimeChoiceBox.getItems().add("4");
        lenOfReservationTimeChoiceBox.getItems().add("5");

        // TODO enum or get from database
        tablePreferenceChoiceBox.getItems().add("Table A");
        tablePreferenceChoiceBox.getItems().add("Table B");
        tablePreferenceChoiceBox.getItems().add("Table C");
        tablePreferenceChoiceBox.getItems().add("Table D");
        tablePreferenceChoiceBox.getItems().add("Table E");
        tablePreferenceChoiceBox.getItems().add("Table F");
        tablePreferenceChoiceBox.getItems().add("Table G");
        tablePreferenceChoiceBox.getItems().add("Table H");
        tablePreferenceChoiceBox.getItems().add("Table I");
        tablePreferenceChoiceBox.getItems().add("Table J");
        tablePreferenceChoiceBox.getItems().add("Table K");

        confirmButton.setOnAction(e -> {
            // TODO set new value

            // TODO try catch
            String newBookingId = "";
            try {
                newBookingId = String.valueOf(HelperMethods.getNewIdByFile("BOOKINGS"));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String bookingDate = reservationDatePicker.getValue().getYear() + "-"
                    + reservationDatePicker.getValue().getMonthValue() + "-"
                    + reservationDatePicker.getValue().getDayOfMonth();
            String[] timeArray =
                    reservationTimeChoiceBox.getValue().toString().split(":");
            String bookingTime = Integer.parseInt(timeArray[0]) + "-"
                    + Integer.parseInt(timeArray[1]);
            String numOfGuests = numOfGuestsChoiceBox.getValue().toString();
            String bookingLength =
                    lenOfReservationTimeChoiceBox.getValue().toString();
            String tablePreference =
                    tablePreferenceChoiceBox.getValue().toString();
            String bookingStatus = "pending-approval";

            List<String> newBooking = new ArrayList<>(Arrays.asList(
                    newBookingId,
                    userId,
                    bookingDate,
                    bookingTime,
                    numOfGuests,
                    bookingLength,
                    tablePreference,
                    bookingStatus
            ));

            // TODO handle try catch
            try {
                DataManager.appendDataToFile("BOOKINGS", newBooking);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            CustomerBookingsHistoryViewController controller =
                    new CustomerBookingsHistoryViewController();

            // TODO comment
            try {
                controller.refreshReservationList();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            closeWindow();

        });

        cancelButton.setOnAction(e -> {
            closeWindow();
        });
    }

    // TODO checks and comment
    public void prepareNewBooking(String userId) {
        this.userId = userId;
    }


    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }


}
