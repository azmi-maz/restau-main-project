package org.group.project.controllers.customer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.Booking;
import org.group.project.classes.Customer;
import org.group.project.classes.Floor;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * This class allows the customer to view the booking history.
 *
 * @author azmi_maz
 */
public class CustomerBookingsHistoryViewController {
    private static final String BG_IMAGE = "images" +
            "/background/main-bg" +
            ".jpg";
    private static final int COLUMN_WIDTH_65 = 65;
    private static final int COLUMN_WIDTH_90 = 90;
    private static final int COLUMN_WIDTH_150 = 150;
    private static final int COLUMN_WIDTH_200 = 200;
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
    private static final String EDIT_BUTTON = "edit";
    private static final int BUTTON_WIDTH = 15;
    private static final int BIG_BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 15;
    private static final int BIG_BUTTON_HEIGHT = 25;
    private static final String PENDING = "pending-approval";
    private static final String INFO_TITLE = "Info";
    private static final String INFO_MESSAGE = "You cannot edit " +
            "this reservation.";
    private static final String OK = "Ok";
    private static final String VIEW_BOOKING_WINDOW = "smallwindows/" +
            "customer-view-booking" +
            ".fxml";
    private static final String EDIT_TABLE_TITLE = "Edit Table Reservation";
    private static final String DELETE_BUTTON = "delete";
    private static final String OK_DONE = "OK_DONE";
    private static final String DELETE_TITLE = "Table Reservation Update";
    private static final String DELETE_MESSAGE = "Reservation no.%d was " +
            "deleted successfully.";
    private static final String ADD_BOOKING_WINDOW = "smallwindows/" +
            "customer-add-booking" +
            ".fxml";
    private static final String ADD_BOOKING_TITLE = "New Reservation Request";
    private static final String CANCEL_BOOKING_TITLE = "Cancel Table " +
            "Reservation Request";
    private static final String CANCEL_BOOKING_MESSAGE = "Do you want " +
            "to cancel this reservation?";
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
    private Button newReservationButton;

    @FXML
    private TableView<Booking> reservationTable = new TableView<>();
    private ObservableList<Booking> data =
            FXCollections.observableArrayList();

    @FXML
    private BorderPane borderPane;

    private int userId;

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
            String formattedTime = cellData.getValue()
                    .getBookingTimeInFormat();
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
            Button editButton = new Button();
            ImageLoader.setUpGraphicButton(editButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, EDIT_BUTTON);
            Booking currentBooking = cellData.getValue();

            editButton.setOnMousePressed(e -> {

                if (!cellData
                        .getValue()
                        .getBookingStatus()
                        .equalsIgnoreCase(PENDING)) {

                    AlertPopUpWindow.displayInformationWindow(
                            INFO_TITLE,
                            INFO_MESSAGE,
                            OK
                    );
                    return;
                }

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    VIEW_BOOKING_WINDOW));

                    VBox vbox = fxmlLoader.load();

                    CustomerEditBookingController controller =
                            fxmlLoader.getController();

                    controller.setBookingToView(currentBooking);

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle(EDIT_TABLE_TITLE);

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshReservationList();

                } catch (IOException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }
            });

            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(COLUMN_WIDTH_65);
        actionButtonColumn2.setStyle(CENTERED);
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            ImageLoader.setUpGraphicButton(deleteButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, DELETE_BUTTON);
            Booking selectedBooking = cellData.getValue();

            // Deletes the selected reservation and refresh list.
            deleteButton.setOnMousePressed(e -> {
                Customer customer = (Customer) MainScenesMap.getCurrentUser();

                Optional<ButtonType> userChoice =
                        promptForUserAcknowledgement();

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase(OK_DONE)) {

                    try {
                        boolean isSuccessful = customer.deleteBooking(
                                selectedBooking
                        );
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    DELETE_TITLE,
                                    String.format(
                                            DELETE_MESSAGE,
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
            return new SimpleObjectProperty<>(deleteButton);
        });

        reservationTable.setItems(data);

        ImageLoader.setUpGraphicButton(newReservationButton,
                BIG_BUTTON_WIDTH,
                BIG_BUTTON_HEIGHT,
                EDIT_BUTTON);

        newReservationButton.setOnAction(e -> {

            try {
                FXMLLoader fxmlLoader =
                        new FXMLLoader(Main.class.getResource(
                                ADD_BOOKING_WINDOW));

                VBox vbox = fxmlLoader.load();

                CustomerAddBookingController controller =
                        fxmlLoader.getController();

                controller.prepareNewBooking(userId);

                Scene editScene = new Scene(vbox,
                        WindowSize.SMALL.WIDTH,
                        WindowSize.SMALL.HEIGHT);

                Stage editStage = new Stage();
                editStage.setScene(editScene);

                editStage.setTitle(ADD_BOOKING_TITLE);

                editStage.initModality(Modality.APPLICATION_MODAL);

                editStage.showAndWait();

                refreshReservationList();

            } catch (IOException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
        });

    }

    /**
     * This method refreshes the table list of table reservations.
     */
    public void refreshReservationList() {

        updateUserId();

        // This clears up the list everytime it refreshes.
        reservationTable.getItems().clear();
        data.clear();

        try {

            Floor floor = new Floor();
            floor.getBookingDataByUserId(
                    data,
                    userId
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }

    private Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                CANCEL_BOOKING_TITLE,
                CANCEL_BOOKING_MESSAGE
        );
    }

    private void updateUserId() {

        if (MainScenesMap.getCurrentUser() == null) {
            return;
        }

        try {

            UserManagement userManagement = new UserManagement();
            userId = userManagement.getUserIdByUsername(
                    MainScenesMap.getCurrentUser().getUsername());

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

}
