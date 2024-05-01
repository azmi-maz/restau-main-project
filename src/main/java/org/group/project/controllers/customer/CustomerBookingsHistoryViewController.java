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
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * This class allows the customer to view the booking history.
 */
public class CustomerBookingsHistoryViewController {

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
            bgImage = new Image(Main.class.getResource("images" +
                    "/background/main-bg" +
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
            String formattedTime = cellData.getValue()
                    .getBookingTimeInFormat();
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
            Button editButton = new Button();
            ImageLoader.setUpGraphicButton(editButton,
                    15, 15, "edit");
            Booking currentBooking = cellData.getValue();

            editButton.setOnMousePressed(e -> {

                if (!cellData
                        .getValue()
                        .getBookingStatus()
                        .equalsIgnoreCase("pending-approval")) {

                    AlertPopUpWindow.displayInformationWindow(
                            "Info",
                            "You cannot edit this reservation.",
                            "Ok"
                    );
                    return;
                }

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/" +
                                            "customer-view-booking" +
                                            ".fxml"));

                    VBox vbox = fxmlLoader.load();

                    CustomerEditBookingController controller =
                            fxmlLoader.getController();

                    controller.setBookingToView(currentBooking);

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle("Edit Table Reservation");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshReservationList();

                } catch (IOException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            "Error",
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }
            });

            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(65);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            ImageLoader.setUpGraphicButton(deleteButton,
                    15, 15, "delete");
            Booking selectedBooking = cellData.getValue();

            // Deletes the selected reservation and refresh list.
            deleteButton.setOnMousePressed(e -> {
                Customer customer = (Customer) Main.getCurrentUser();

                Optional<ButtonType> userChoice =
                        promptForUserAcknowledgement();

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {

                    try {
                        boolean isSuccessful = customer.deleteBooking(
                                selectedBooking
                        );
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    "Table Reservation Update",
                                    String.format(
                                            "Reservation no.%d was " +
                                                    "deleted successfully.",
                                            selectedBooking.getBookingId()
                                    ),
                                    "Ok"
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
            return new SimpleObjectProperty<>(deleteButton);
        });

        reservationTable.setItems(data);

        ImageLoader.setUpGraphicButton(newReservationButton,
                25, 25, "edit");

        newReservationButton.setOnAction(e -> {

            try {
                FXMLLoader fxmlLoader =
                        new FXMLLoader(Main.class.getResource(
                                "smallwindows/" +
                                        "customer-add-booking" +
                                        ".fxml"));

                VBox vbox = fxmlLoader.load();

                CustomerAddBookingController controller =
                        fxmlLoader.getController();

                controller.prepareNewBooking(userId);

                Scene editScene = new Scene(vbox,
                        WindowSize.SMALL.WIDTH,
                        WindowSize.SMALL.HEIGHT);

                Stage editStage = new Stage();
                editStage.setScene(editScene);

                editStage.setTitle("New Reservation Request");

                editStage.initModality(Modality.APPLICATION_MODAL);

                editStage.showAndWait();

                refreshReservationList();

            } catch (IOException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
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
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }

    private Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                "Cancel Table Reservation Request",
                "Do you want to cancel this reservation?"
        );
    }

    private void updateUserId() {

        if (Main.getCurrentUser() == null) {
            return;
        }

        try {

            UserManagement userManagement = new UserManagement();
            userId = userManagement.getUserIdByUsername(
                    Main.getCurrentUser().getUsername());

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

}
