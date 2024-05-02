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

/**
 * This class allows the customer to add new booking.
 *
 * @author azmi_maz
 */
public class CustomerAddBookingController {

    @FXML
    private VBox vbox;

    @FXML
    private DatePicker reservationDatePicker;

    @FXML
    private ChoiceBox<LocalTime> reservationTimeChoiceBox;

    @FXML
    private ChoiceBox<Integer> numOfGuestsChoiceBox;

    @FXML
    private ChoiceBox<Integer> lenOfReservationTimeChoiceBox;

    @FXML
    private ChoiceBox<Table> tablePreferenceChoiceBox;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private int userId;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        reservationDatePicker.setOnAction(e -> {
            if (reservationDatePicker.getValue().compareTo(
                    LocalDate.now()) < 0) {
                AlertPopUpWindow.displayErrorWindow(
                        "Reservation date cannot be less " +
                                "than current date."
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
                    e.getMessage()
            );
            e.printStackTrace();
        }

        // TODO get from database
        numOfGuestsChoiceBox.setOnAction(e -> {

            try {
                Floor floor = new Floor();
                int maxGuestSelected = numOfGuestsChoiceBox.getValue();
                floor.updateTablesBasedOnGuests(
                        maxGuestSelected,
                        tablePreferenceChoiceBox
                );
            } catch (TextFileNotFoundException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }

        });


        /*
         * To reformat the date picker to be in dd/mm/yyyy format
         * Source: https://stackoverflow.com/questions/26831978/
         * javafx-datepicker-getvalue-in-a-specific-format (April 2024)
         */
        reservationDatePicker.setConverter(
                new StringConverter<>() {
                    final DateTimeFormatter dateFormatter = DateTimeFormatter
                            .ofPattern("dd/MM/yyyy");

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

        confirmButton.setOnAction(e -> {

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
                    Booking newBooking = floor.createNewBooking(
                            userId,
                            bookingDate,
                            bookingTime,
                            numOfGuests,
                            tablePreference,
                            bookingLength
                    );
                    boolean isSuccessful = customer.addNewBooking(
                            newBooking,
                            floor
                    );
                    if (isSuccessful) {
                        AlertPopUpWindow.displayInformationWindow(
                                "Table Reservation Update",
                                "New table reservation was " +
                                        "made successfully.",
                                "Ok"
                        );
                    }
                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

                closeWindow();

            } else {

                AlertPopUpWindow.displayErrorWindow(
                        "Please complete the form."
                );
            }

        });

        cancelButton.setOnAction(e -> {
            closeWindow();
        });
    }

    /**
     * This method sets up the user id of the customer.
     *
     * @param userId
     */
    public void prepareNewBooking(int userId) {
        this.userId = userId;
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }


}
