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
import org.group.project.scenes.MainScenesMap;

import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * This class enables the waiter to view pending table reservations.
 *
 * @author azmi_maz
 */
public class WaiterApproveBookingsViewController {
    private static final String BG_IMAGE = "images" +
            "/background/waiter-main" +
            ".jpg";
    private static final String CUSTOMER_COLUMN = "Customer";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String CUSTOMER = "customer";
    private static final String DATE_COLUMN = "Booking Date";
    private static final String TIME_COLUMN = "Booking Time";
    private static final String GUESTS_COLUMN = "No. of Guests";
    private static final String NUM_OF_GUESTS = "numOfGuests";
    private static final String SLOT_COLUMN = "Time Slot";
    private static final String TABLE_COLUMN = "Table";
    private static final String STATUS_COLUMN = "Status";
    private static final String BOOKING_STATUS = "bookingStatus";
    private static final String ACTION_COLUMN = "Action";
    private static final String CONFIRM_BUTTON = "confirm";
    private static final String APPROVE_TOOLTIP = "Approve";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final int COLUMN_WIDTH_65 = 65;
    private static final int COLUMN_WIDTH_90 = 90;
    private static final int COLUMN_WIDTH_150 = 150;
    private static final int COLUMN_WIDTH_200 = 200;
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String APPROVE_TITLE = "Table Reservation Approval";
    private static final String APPROVE_MESSAGE = "Do you want to approve this table reservation?";
    private static final String OK = "Ok";
    private static final String OK_DONE = "OK_DONE";
    private static final String SUCCESS_TITLE = "Table Reservation";
    private static final String SUCCESS_MESSAGE = "Booking no.%d was approved " +
            "successfully.";
    private static final String CANCEL_TOOLTIP = "Cancel";
    private static final String CANCEL_BUTTON = "cancel";
    private static final String CANCEL_TITLE = "Table Reservation Approval";
    private static final String CANCEL_MESSAGE = "Do you want to cancel this table reservation?";
    private static final String RESERVATION_CANCELLED_TITLE = "Table Reservation";
    private static final String RESERVATION_CANCELLED_MESSAGE = "Booking no.%d was rejected " +
            "successfully.";
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
            bgImage = new Image(Main.class
                    .getResource(BG_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
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

        customerColumn.setText(CUSTOMER_COLUMN);
        customerColumn.setMinWidth(COLUMN_WIDTH_150);
        customerColumn.setStyle(CENTERED);
        customerColumn.setCellValueFactory(
                new PropertyValueFactory<>(CUSTOMER));

        bookingDateColumn.setText(DATE_COLUMN);
        bookingDateColumn.setMinWidth(COLUMN_WIDTH_150);
        bookingDateColumn.setStyle(CENTERED);
        bookingDateColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getBookingDateInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        bookingTimeColumn.setText(TIME_COLUMN);
        bookingTimeColumn.setMinWidth(COLUMN_WIDTH_150);
        bookingTimeColumn.setStyle(CENTERED);
        bookingTimeColumn.setCellValueFactory(cellData -> {
            String formattedTime = cellData
                    .getValue().getBookingTime()
                    .format(DateTimeFormatter.ofPattern(TIME_FORMAT));
            return new SimpleObjectProperty<>(formattedTime);
        });

        numOfGuestsColumn.setText(GUESTS_COLUMN);
        numOfGuestsColumn.setMinWidth(COLUMN_WIDTH_90);
        numOfGuestsColumn.setStyle(CENTERED);
        numOfGuestsColumn.setCellValueFactory(
                new PropertyValueFactory<>(NUM_OF_GUESTS));

        bookingLengthColumn.setText(SLOT_COLUMN);
        bookingLengthColumn.setMinWidth(COLUMN_WIDTH_150);
        bookingLengthColumn.setStyle(CENTERED);
        bookingLengthColumn.setCellValueFactory(cellData -> {
            String formattedValue =
                    cellData.getValue().getTimePeriodOfBooking();
            return new SimpleObjectProperty<>(formattedValue);
        });

        tablePreferenceColumn.setText(TABLE_COLUMN);
        tablePreferenceColumn.setMinWidth(COLUMN_WIDTH_150);
        tablePreferenceColumn.setStyle(CENTERED);
        tablePreferenceColumn.setCellValueFactory(cellData -> {
            String table = cellData.getValue().getTableNames();
            return new SimpleObjectProperty<>(table);
        });

        bookingStatusColumn.setText(STATUS_COLUMN);
        bookingStatusColumn.setMinWidth(COLUMN_WIDTH_200);
        bookingStatusColumn.setStyle(CENTERED);
        bookingStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>(BOOKING_STATUS));

        actionButtonColumn.setText(ACTION_COLUMN);

        actionButtonColumn1.setMinWidth(COLUMN_WIDTH_65);
        actionButtonColumn1.setStyle(CENTERED);
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button confirmButton = new Button();
            confirmButton.setTooltip(new Tooltip(APPROVE_TOOLTIP));
            ImageLoader.setUpGraphicButton(confirmButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, CONFIRM_BUTTON);
            Booking selectedBooking = cellData.getValue();
            Customer selectedCustomer = cellData.getValue().getCustomer();

            confirmButton.setOnAction(e -> {
                Waiter waiter = (Waiter) MainScenesMap.getCurrentUser();

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        APPROVE_TITLE,
                        APPROVE_MESSAGE
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase(OK_DONE)) {

                    try {
                        boolean isSuccessful = waiter.approveTableReservation(
                                selectedBooking,
                                selectedCustomer
                        );
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    SUCCESS_TITLE,
                                    String.format(
                                            SUCCESS_MESSAGE,
                                            selectedBooking.getBookingId()
                                    ), OK
                            );
                        }
                        refreshReservationList();

                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }
                }
            });

            return new SimpleObjectProperty<>(confirmButton);
        });

        actionButtonColumn2.setMinWidth(COLUMN_WIDTH_65);
        actionButtonColumn2.setStyle(CENTERED);
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button cancelButton = new Button();
            cancelButton.setTooltip(new Tooltip(CANCEL_TOOLTIP));
            ImageLoader.setUpGraphicButton(cancelButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, CANCEL_BUTTON);
            Booking selectedBooking = cellData.getValue();
            Customer selectedCustomer = cellData.getValue().getCustomer();

            // This changes the booking status to failed
            cancelButton.setOnAction(e -> {
                Waiter waiter = (Waiter) MainScenesMap.getCurrentUser();

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        CANCEL_TITLE,
                        CANCEL_MESSAGE
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase(OK_DONE)) {

                    try {

                        boolean isSuccessful = waiter.cancelTableReservation(
                                selectedBooking,
                                selectedCustomer
                        );
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    RESERVATION_CANCELLED_TITLE,
                                    String.format(
                                            RESERVATION_CANCELLED_MESSAGE,
                                            selectedBooking.getBookingId()
                                    ), OK
                            );
                        }
                        refreshReservationList();

                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
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
