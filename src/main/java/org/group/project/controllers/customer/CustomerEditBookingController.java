package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.group.project.Main;
import org.group.project.classes.Booking;
import org.group.project.classes.Customer;
import org.group.project.classes.Floor;
import org.group.project.classes.Table;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomerEditBookingController {

    @FXML
    private VBox vbox;

    @FXML
    private DatePicker reservationDatePicker;

    @FXML
    private ChoiceBox<LocalTime> reservationTimeChoiceBox;

    @FXML
    private ChoiceBox<Integer> numOfGuestsChoiceBox;

    @FXML
    private ChoiceBox<Table> tablePreferenceChoiceBox;

    @FXML
    private ChoiceBox<Integer> lenOfReservationTimeChoiceBox;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button closeButton;

    private Booking currentBooking;

    public void initialize() {

        reservationDatePicker.setOnAction(e -> {
            if (reservationDatePicker.getValue()
                    .compareTo(LocalDate.now()) < 0) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Reservation date cannot be " +
                                "less than current date."
                );
                reservationDatePicker.setValue(LocalDate.now());
            }
        });


        try {
            Floor floor = new Floor();

            floor.updateReservationTimeChoiceBox(
                    reservationTimeChoiceBox
            );
            floor.updateNumOfGuestsChoiceBox(
                    numOfGuestsChoiceBox
            );
            floor.updateBookingLengthChoiceBox(
                    lenOfReservationTimeChoiceBox
            );
        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

        // TODO get from database
        numOfGuestsChoiceBox.setOnAction(e -> {

            try {
                Floor floor = new Floor();

                tablePreferenceChoiceBox.getItems().clear();

                int maxGuestSelected = numOfGuestsChoiceBox.getValue();

                floor.updateTablesBasedOnGuests(
                        maxGuestSelected,
                        tablePreferenceChoiceBox
                );
            } catch (TextFileNotFoundException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        ex.getMessage()
                );
                ex.printStackTrace();
            }

        });

        // TODO cite https://stackoverflow.com/questions/26831978/javafx-datepicker-getvalue-in-a-specific-format
        reservationDatePicker.setConverter(
                new StringConverter<>() {
                    final DateTimeFormatter dateFormatter =
                            DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    @Override
                    public String toString(LocalDate date) {
                        return (date != null) ? dateFormatter.format(date) : "";
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return (string != null && !string.isEmpty())
                                ? LocalDate.parse(string, dateFormatter)
                                : null;
                    }
                });

        saveChangesButton.setOnAction(e -> {
            // TODO set new value

            Customer customer = (Customer) Main.getCurrentUser();

            if (
                    reservationDatePicker.getValue() != null
                            && reservationTimeChoiceBox.getValue() != null
                            && numOfGuestsChoiceBox.getValue() != null
                            && tablePreferenceChoiceBox.getValue() != null
                            && lenOfReservationTimeChoiceBox.getValue() != null
            ) {

                LocalDate bookingDate = reservationDatePicker.getValue();
                LocalTime bookingTime = reservationTimeChoiceBox.getValue();
                int numOfGuests = numOfGuestsChoiceBox.getValue();
                Table tablePreference = tablePreferenceChoiceBox.getValue();
                int bookingLength = lenOfReservationTimeChoiceBox.getValue();

                try {
                    Floor floor = new Floor();
                    Booking editedBooking = floor
                            .getCurrentBooking(
                                    currentBooking,
                                    bookingDate,
                                    bookingTime,
                                    numOfGuests,
                                    tablePreference,
                                    bookingLength
                            );
                    boolean isSuccessful = customer.editBooking(
                            editedBooking,
                            floor
                    );
                    if (isSuccessful) {
                        AlertPopUpWindow.displayInformationWindow(
                                "Table Reservation Update",
                                "Amendments were made successfully.",
                                "Ok"
                        );
                    }

                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            "Error",
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

            } else {

                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Please complete the form."
                );
            }

            closeWindow();

        });

        closeButton.setOnAction(e -> {
            closeWindow();
        });

    }

    public void setBookingToView(Booking booking) {
        currentBooking = booking;
        reservationDatePicker.setValue(booking.getBookingDate());
        reservationTimeChoiceBox.setValue(
                booking.getBookingTime());
        numOfGuestsChoiceBox.setValue(booking.getNumOfGuests());
        lenOfReservationTimeChoiceBox.setValue(
                booking.getBookingLengthInHour());
        tablePreferenceChoiceBox.setValue(
                booking.getTablePreference().getFirst());
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }


}
