package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.DataManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class CustomerEditBookingController {

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
    private Button saveChangesButton;

    @FXML
    private Button closeButton;

    private int bookingId;

    public void initialize() {

        reservationTimeChoiceBox.getItems().add(LocalTime.of(6, 30));
        reservationTimeChoiceBox.getItems().add(LocalTime.of(7, 00));

        // TODO check if these values are correct
        numOfGuestsChoiceBox.getItems().add("2");
        numOfGuestsChoiceBox.getItems().add("4");
        numOfGuestsChoiceBox.getItems().add("8");
        numOfGuestsChoiceBox.getItems().add("10");

        lenOfReservationTimeChoiceBox.getItems().add("1");
        lenOfReservationTimeChoiceBox.getItems().add("2");
        lenOfReservationTimeChoiceBox.getItems().add("3");
        lenOfReservationTimeChoiceBox.getItems().add("4");

        saveChangesButton.setOnAction(e -> {
            // TODO set new value
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

            // TODO handle try catch
            try {
                DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                        "bookingDate", bookingDate);
                DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                        "bookingTime", bookingTime);
                DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                        "numOfGuests", numOfGuests);
                DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                        "bookingLength", bookingLength);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            closeWindow();

        });

        closeButton.setOnAction(e -> {
            closeWindow();
        });

    }

    public void setBookingToView(int bookingId,
                                 LocalDate reservationDate,
                                 LocalTime reservationTime,
                                 int numOfGuests,
                                 int bookingLength) {
        this.bookingId = bookingId;
        // TODO cant seem to apply DateTimeFormatter.ofPattern("dd/MM/yyyy")
        reservationDatePicker.setValue(reservationDate);
        reservationTimeChoiceBox.setValue(reservationTime);
        numOfGuestsChoiceBox.setValue(String.valueOf(numOfGuests));
        lenOfReservationTimeChoiceBox.setValue(bookingLength);
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }


}
