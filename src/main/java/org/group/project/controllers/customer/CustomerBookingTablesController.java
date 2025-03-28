package org.group.project.controllers.customer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.group.project.Main;
import org.group.project.classes.Booking;
import org.group.project.classes.Customer;
import org.group.project.classes.Floor;
import org.group.project.classes.Table;
import org.group.project.classes.Tables;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class allows the customer to book tables for booking.
 * 
 * @author azmi_maz
 */
public class CustomerBookingTablesController {
    private static final String BG_IMAGE = "images" +
            "/background/main-bg" +
            ".jpg";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String INVALID_DATE = "Reservation date cannot be " +
            "less than current date.";
    private static final String INCOMPLETE_FIELDS = "Please complete the required booking details.";
    private static final String ADD_BOOKING_WINDOW = "smallwindows/" +
            "customer-add-booking" +
            ".fxml";
    private static final String ADD_BOOKING_TITLE = "New Reservation Request";
    private static final String TABLE_IS_BOOKED_TITLE = "Table Status";
    private static final String TABLE_IS_BOOKED_CONTENT = "This table is reserved during the selected time period. Please choose another table.";
    private static final String TABLE_IS_BOOKED_BUTTON = "Ok";

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private DatePicker reservationDatePicker;

    @FXML
    private ChoiceBox<LocalTime> reservationTimeChoiceBox;

    @FXML
    private ChoiceBox<Integer> lenOfReservationTimeChoiceBox;

    private int userId;

    private Tables tables;

    private Floor activeFloor;

    /**
     * This initializes the controller for the fxml.
     * 
     * @throws TextFileNotFoundException
     */
    public void initialize() throws TextFileNotFoundException {

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class
                    .getResource(BG_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage());
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

        try {

            tables = new Tables();
            tables.populateTablesToGridPane(gridPane);

            activeFloor = new Floor();

            activeFloor.updateReservationTimeChoiceBox(
                    reservationTimeChoiceBox);
            activeFloor.updateBookingLengthChoiceBox(
                    lenOfReservationTimeChoiceBox);

        } catch (TextFileNotFoundException | URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage());
            e.printStackTrace();
        }

        // This is used during tables design only
        // gridPane.setGridLinesVisible(true);

        gridPane.setOnMouseClicked(e -> {

            Object selectedObject = e.getTarget();

            boolean isImageViewSelected = selectedObject instanceof ImageView;
            boolean isStackPaneSelected = selectedObject instanceof StackPane;

            if (reservationDatePicker.getValue() == null
                    || reservationTimeChoiceBox.getValue() == null
                    || lenOfReservationTimeChoiceBox.getValue() == null) {

                AlertPopUpWindow.displayErrorWindow(
                        INCOMPLETE_FIELDS);
            }

            // To handle "filled" tables
            if (isImageViewSelected) {
                AlertPopUpWindow.displayInformationWindow(TABLE_IS_BOOKED_TITLE, TABLE_IS_BOOKED_CONTENT,
                        TABLE_IS_BOOKED_BUTTON);
            }

            // If the selected table is empty
            if (isStackPaneSelected) {

                updateUserId();
                LocalDate bookingDate = reservationDatePicker.getValue();
                LocalTime bookingTime = reservationTimeChoiceBox.getValue();
                int bookingLength = lenOfReservationTimeChoiceBox.getValue();

                String currentImageUrl = ((ImageView) ((StackPane) e.getTarget()).getChildren().getFirst()).getImage()
                        .getUrl();

                String labelTableName = ((Label) ((StackPane) e.getTarget()).getChildren().getLast()).getText();

                Table returnedTable = tables.getTableByTablename(labelTableName);

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                            ADD_BOOKING_WINDOW));

                    VBox vbox = fxmlLoader.load();

                    CustomerAddBookingController controller = fxmlLoader.getController();

                    controller.prepareNewBooking(userId);
                    controller.addExistingTableToBooking(
                            bookingDate,
                            bookingTime,
                            bookingLength,
                            returnedTable);

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle(ADD_BOOKING_TITLE);

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    resetsBookingForm();

                    clearGrid();

                } catch (IOException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage());
                    ex.printStackTrace();
                }

            }

        });

        reservationDatePicker.setOnAction(e -> {
            if (reservationDatePicker.getValue() != null && reservationDatePicker.getValue().compareTo(
                    LocalDate.now()) < 0) {
                AlertPopUpWindow.displayErrorWindow(
                        INVALID_DATE);
                reservationDatePicker.setValue(LocalDate.now());
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
                            .ofPattern(DATE_FORMAT);

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

        lenOfReservationTimeChoiceBox.setOnAction(e -> {

            if (reservationDatePicker != null && reservationDatePicker.getValue() != null
                    && reservationTimeChoiceBox.getValue() != null
                    && lenOfReservationTimeChoiceBox.getValue() != null) {

                refreshGridPaneWithActiveBookings();

            }
        });

    }

    /**
     * This resets the gridpane with active bookings, if any.
     */
    public void refreshGridPaneWithActiveBookings() {

        LocalDate bookingDate = null;
        LocalTime bookingTime = null;
        int bookingLength = 0;
        LocalTime bookingTimePlusBookingLength = null;

        if (reservationDatePicker != null) {
            bookingDate = reservationDatePicker.getValue();
        }

        if (reservationTimeChoiceBox != null) {
            bookingTime = reservationTimeChoiceBox.getValue();
        }

        if (lenOfReservationTimeChoiceBox != null) {
            bookingLength = lenOfReservationTimeChoiceBox.getValue();

        }

        if (reservationTimeChoiceBox != null && bookingLength != 0) {
            bookingTimePlusBookingLength = reservationTimeChoiceBox.getValue().plusHours(bookingLength);
        }

        List<Booking> bookingsMade = activeFloor.getBookingsByDateAndTimeRange(
                bookingDate,
                bookingTime,
                bookingTimePlusBookingLength);
        /**
         * The above third parameter subtracts a minute from the
         * search to exclude bookings that starts
         * at the same time the user booking
         * ends.
         */

        if (bookingsMade != null && !bookingsMade.isEmpty()) {
            gridPane.getChildren().clear();
            tables.resetsAllTablesStatus();
            tables.updateTablesFromExistingBookings(bookingsMade);

            try {
                tables.populateTablesToGridPane(gridPane);
            } catch (TextFileNotFoundException | URISyntaxException error) {
                AlertPopUpWindow.displayErrorWindow(error.getMessage());
            }

        } else {
            clearGrid();
        }
    }

    /**
     * This clears the grid with all the tables reset.
     */
    public void clearGrid() {
        gridPane.getChildren().clear();
        tables.resetsAllTablesStatus();

        try {
            tables.populateTablesToGridPane(gridPane);
        } catch (TextFileNotFoundException | URISyntaxException e1) {
            AlertPopUpWindow.displayErrorWindow(e1.getMessage());
        }
    }

    /**
     * This sets all the form fields to null values.
     */
    public void resetsBookingForm() {
        this.reservationDatePicker.setValue(null);
        ;
        this.reservationTimeChoiceBox.setValue(null);
        this.lenOfReservationTimeChoiceBox.setValue(null);
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
                    e.getMessage());
            e.printStackTrace();
        }
    }
}
