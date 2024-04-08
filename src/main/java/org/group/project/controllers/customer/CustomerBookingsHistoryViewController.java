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
import org.group.project.classes.*;
import org.group.project.scenes.WindowSize;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private List<String> tableReservations;

    @FXML
    private TableView<Booking> reservationTable = new TableView<>();
    private ObservableList<Booking> data =
            FXCollections.observableArrayList();

    @FXML
    private BorderPane borderPane;

    private String userId;

    public void initialize() throws FileNotFoundException, URISyntaxException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/main-bg" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);

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
            String formattedTime = cellData.getValue().getBookingTimeInFormat();
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

        tablePreferenceColumn.setText("Tables");
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
            ImageLoader.setUpGraphicButton(editButton, 15, 15, "view-details");
            int bookingId =
                    cellData.getValue().getBookingId();
            LocalDate reservationDate = cellData.getValue().getBookingDate();
            LocalTime reservationTime = cellData.getValue().getBookingTime();
            int numOfGuests = cellData.getValue().getNumOfGuests();
            String tablePreference =
                    cellData.getValue().getTableNames().toString();
            int bookingLength = cellData.getValue().getBookingLengthInHour();

            editButton.setOnMousePressed(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/customer-view-booking" +
                                            ".fxml"));

                    VBox vbox = fxmlLoader.load();

                    CustomerEditBookingController controller =
                            fxmlLoader.getController();

                    controller.setBookingToView(bookingId, reservationDate,
                            reservationTime, numOfGuests,
                            tablePreference, bookingLength);

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    // TODO Should final variable this
                    editStage.setTitle("View Table Reservation");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshReservationList();

                } catch (IOException ex) {
                    // TODO catch error
                    throw new RuntimeException(ex);
                }
            });
            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(65);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            ImageLoader.setUpGraphicButton(deleteButton, 15, 15, "delete");
            int bookingId =
                    cellData.getValue().getBookingId();

            // TODO comment - delete the reservation and refresh list
            deleteButton.setOnMousePressed(e -> {
                Optional<ButtonType> userChoice =
                        promptForUserAcknowledgement();

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {
                    deleteBooking(bookingId);

                    // TODO try catch
                    try {
                        refreshReservationList();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            });
            return new SimpleObjectProperty<>(deleteButton);
        });

        reservationTable.setItems(data);

        ImageLoader.setUpGraphicButton(newReservationButton, 25, 25, "edit");

        newReservationButton.setOnAction(e -> {

            // TODO open new form
            try {
                FXMLLoader fxmlLoader =
                        new FXMLLoader(Main.class.getResource(
                                "smallwindows/customer-add-booking" +
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
                // TODO Should final variable this
                editStage.setTitle("New Reservation Request");

                editStage.initModality(Modality.APPLICATION_MODAL);

                editStage.showAndWait();

                refreshReservationList();

            } catch (IOException ex) {
                // TODO catch error
                throw new RuntimeException(ex);
            }
        });

    }

    // TODO comment
    public void refreshReservationList() throws FileNotFoundException {
        updateUserId();

        // TODO comment that this clears up the list everytime it refresh
        reservationTable.getItems().clear();
        data.clear();

        // TODO to filter based on userid
        tableReservations = DataManager.allDataFromFile("BOOKINGS");

        for (String booking : tableReservations) {
            List<String> bookingDetails = List.of(booking.split(","));
            String currentUserId = bookingDetails
                    .get(DataFileStructure
                            .getIndexColOfUniqueId("USERS"));

            if (currentUserId.equalsIgnoreCase(userId)) {

                int bookingId = Integer.parseInt(bookingDetails.get(0));
                List<String> bookingDateDetails =
                        List.of(bookingDetails.get(2).split("-"));
                List<String> bookingTimeDetails =
                        List.of(bookingDetails.get(3).split("-"));
                Customer customer;
                LocalDate bookingDate =
                        LocalDate.of(Integer.parseInt(bookingDateDetails.get(0)),
                                Integer.parseInt(bookingDateDetails.get(1)),
                                Integer.parseInt(bookingDateDetails.get(2)));
                LocalTime bookingTime =
                        LocalTime.of(Integer.parseInt(bookingTimeDetails.get(0)),
                                Integer.parseInt(bookingTimeDetails.get(1)));
                int numOfGuests = Integer.parseInt(bookingDetails.get(4));
                int bookingLength = Integer.parseInt(bookingDetails.get(5));
                String[] bookingTables = bookingDetails.get(6).split(";");
                String bookingStatus = bookingDetails.get(7);
                List<Table> tablePreference = new ArrayList<>();

                // TODO filter by current userId
                List<String> customerString = HelperMethods.getDataById("USERS",
                        bookingDetails.get(DataFileStructure.getIndexByColName(
                                "BOOKINGS", "userId")));
                for (String rawTable : bookingTables) {
                    List<String> rawTableDetails = HelperMethods.getDataById(
                            "TABLES", rawTable);
                    tablePreference.add(new Table(rawTableDetails.get(0),
                            Integer.parseInt(rawTableDetails.get(1))));
                }
                if (customerString != null) {
                    customer = new Customer(
                            customerString.get(DataFileStructure.getIndexByColName("USERS", "firstName")),
                            customerString.get(DataFileStructure.getIndexByColName("USERS", "lastName")),
                            customerString.get(DataFileStructure.getIndexByColName("USERS", "username")),
                            Integer.parseInt(customerString.get(DataFileStructure.getIndexByColName("USERS", "userId"))),
                            HelperMethods.formatAddressToRead(customerString.get(DataFileStructure.getIndexByColName("USERS", "address")))
                    );
                    data.add(new Booking(
                            bookingId,
                            customer,
                            bookingDate,
                            bookingTime,
                            numOfGuests,
                            tablePreference,
                            bookingLength,
                            bookingStatus
                    ));
                }
            }
        }
    }

    public Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                "Cancel Table Reservation Request",
                "Do you want to cancel this reservation?"
        );
    }

    private void updateUserId() {
        
        if (Main.getCurrentUser() == null) {
            return;
        }

        // TODO try catch
        try {
            userId = String.valueOf(
                    HelperMethods
                            .findUserIdByUsername(
                                    Main.getCurrentUser().getUsername()));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteBooking(int bookingId) {
        // TODO try catch
        try {
            DataManager.deleteUniqueIdFromFile("BOOKINGS",
                    bookingId);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
