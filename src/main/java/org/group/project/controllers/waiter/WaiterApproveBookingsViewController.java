package org.group.project.controllers.waiter;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.Booking;
import org.group.project.classes.Customer;
import org.group.project.classes.Floor;
import org.group.project.classes.Waiter;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;

import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * This class enables the waiter to view pending table reservations.
 */
public class WaiterApproveBookingsViewController {

    @FXML
    private TableColumn<Booking, Customer> customerColumn;

    @FXML
    private TableColumn<Booking, String> bookingDateColumn;

    @FXML
    private TableColumn<Booking, String> bookingTimeColumn;

    @FXML
    private TableColumn<Booking, Integer> numOfGuestsColumn;

    @FXML
    private TableColumn<Booking, String> bookingLengthColumn;

    @FXML
    private TableColumn<Booking, String> tablePreferenceColumn;

    @FXML
    private TableColumn<Booking, String> bookingStatusColumn;

    @FXML
    private TableColumn<Booking, Button> actionButtonColumn;

    @FXML
    private TableColumn<Booking, Button> actionButtonColumn1;

    @FXML
    private TableColumn<Booking, Button> actionButtonColumn2;

    @FXML
    private TableView<Booking> pendingApprovalsTable = new TableView<>();
    private ObservableList<Booking> data =
            FXCollections.observableArrayList();

    @FXML
    private BorderPane borderPane;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class.getResource("images" +
                    "/background/waiter-main" +
                    ".jpg").toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false,
                false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        refreshReservationList();

        customerColumn.setText("Customer");
        customerColumn.setMinWidth(150);
        customerColumn.setStyle("-fx-alignment: CENTER;");
        customerColumn.setCellValueFactory(
                new PropertyValueFactory<>("customer"));

        bookingDateColumn.setText("Booking Date");
        bookingDateColumn.setMinWidth(150);
        bookingDateColumn.setStyle("-fx-alignment: CENTER;");
        bookingDateColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getBookingDateInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        bookingTimeColumn.setText("Booking Time");
        bookingTimeColumn.setMinWidth(150);
        bookingTimeColumn.setStyle("-fx-alignment: CENTER;");
        bookingTimeColumn.setCellValueFactory(cellData -> {
            String formattedTime = cellData
                    .getValue().getBookingTime()
                    .format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty<>(formattedTime);
        });

        numOfGuestsColumn.setText("No. of Guests");
        numOfGuestsColumn.setMinWidth(90);
        numOfGuestsColumn.setStyle("-fx-alignment: CENTER;");
        numOfGuestsColumn.setCellValueFactory(
                new PropertyValueFactory<>("numOfGuests"));

        bookingLengthColumn.setText("Time Slot");
        bookingLengthColumn.setMinWidth(150);
        bookingLengthColumn.setStyle("-fx-alignment: CENTER;");
        bookingLengthColumn.setCellValueFactory(cellData -> {
            String formattedValue =
                    cellData.getValue().getTimePeriodOfBooking();
            return new SimpleObjectProperty<>(formattedValue);
        });

        tablePreferenceColumn.setText("Table");
        tablePreferenceColumn.setMinWidth(150);
        tablePreferenceColumn.setStyle("-fx-alignment: CENTER;");
        tablePreferenceColumn.setCellValueFactory(cellData -> {
            String table = cellData.getValue().getTableNames();
            return new SimpleObjectProperty<>(table);
        });

        bookingStatusColumn.setText("Status");
        bookingStatusColumn.setMinWidth(200);
        bookingStatusColumn.setStyle("-fx-alignment: CENTER;");
        bookingStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>("bookingStatus"));

        actionButtonColumn.setText("Action");

        actionButtonColumn1.setMinWidth(65);
        actionButtonColumn1.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button confirmButton = new Button();
            confirmButton.setTooltip(new Tooltip("Approve"));
            ImageLoader.setUpGraphicButton(confirmButton,
                    15, 15, "confirm");
            Booking selectedBooking = cellData.getValue();
            Customer selectedCustomer = cellData.getValue().getCustomer();

            confirmButton.setOnAction(e -> {
                Waiter waiter = (Waiter) Main.getCurrentUser();

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        "Table Reservation Approval",
                        "Do you want to approve this table reservation?"
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {

                    try {
                        boolean isSuccessful = waiter.approveTableReservation(
                                selectedBooking,
                                selectedCustomer
                        );
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    "Table Reservation",
                                    String.format(
                                            "Booking no.%d was approved " +
                                                    "successfully.",
                                            selectedBooking.getBookingId()
                                    ), "Ok"
                            );
                        }
                        refreshReservationList();

                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                "Error",
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }
                }
            });

            return new SimpleObjectProperty<>(confirmButton);
        });

        actionButtonColumn2.setMinWidth(65);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button cancelButton = new Button();
            cancelButton.setTooltip(new Tooltip("Cancel"));
            ImageLoader.setUpGraphicButton(cancelButton,
                    15, 15, "cancel");
            Booking selectedBooking = cellData.getValue();
            Customer selectedCustomer = cellData.getValue().getCustomer();

            // This changes the booking status to failed
            cancelButton.setOnAction(e -> {
                Waiter waiter = (Waiter) Main.getCurrentUser();

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        "Table Reservation Approval",
                        "Do you want to cancel this table reservation?"
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {

                    try {

                        boolean isSuccessful = waiter.cancelTableReservation(
                                selectedBooking,
                                selectedCustomer
                        );
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    "Table Reservation",
                                    String.format(
                                            "Booking no.%d was rejected " +
                                                    "successfully.",
                                            selectedBooking.getBookingId()
                                    ), "Ok"
                            );
                        }
                        refreshReservationList();

                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                "Error",
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }

                }
            });

            return new SimpleObjectProperty<>(cancelButton);
        });

        pendingApprovalsTable.setItems(data);

    }

    /**
     * This method refreshes the table of reservations pending for
     * approvals.
     */
    public void refreshReservationList() {

        // This clears up the list everytime it refreshes.
        pendingApprovalsTable.getItems().clear();
        data.clear();

        try {

            Floor floor = new Floor();

            floor.getPendingBookingData(data);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }

    private Optional<ButtonType> promptForUserAcknowledgement(
            String header,
            String message
    ) {
        return AlertPopUpWindow.displayConfirmationWindow(
                header,
                message
        );
    }
}
