package org.group.project.controller.customer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.*;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerBookingsHistoryViewController {

    @FXML
    private TableColumn<Booking, Customer> customerColumn;

    @FXML
    private TableColumn<Booking, String> bookingDateColumn;

    @FXML
    private TableColumn<Booking, LocalTime> bookingTimeColumn;

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


    private List<String> tableReservations;

    @FXML
    private TableView<Booking> reservationTable = new TableView<>();
    private ObservableList<Booking> data = FXCollections.observableArrayList();

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView bgImage;

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

        tableReservations = DataManager.allDataFromFile("BOOKINGS");

        for (String booking : tableReservations) {
            List<String> bookingDetails = List.of(booking.split(","));
            List<String> bookingDateDetails =
                    List.of(bookingDetails.get(2).split("-"));
            List<String> bookingTimeDetails =
                    List.of(bookingDetails.get(3).split("-"));
//            System.out.println(bookingDetails);
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
        bookingTimeColumn.setCellValueFactory(
                new PropertyValueFactory<>("bookingTime"));

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
            // TODO create a method to handle view details by booking
            editButton.setOnMousePressed(e -> {
                System.out.println(cellData.getValue().getCustomer().getFirstName());
            });
            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(65);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button cancelButton = new Button();
            ImageLoader.setUpGraphicButton(cancelButton, 15, 15, "cancel");
            // TODO create a method to handle table reservation cancellation
            cancelButton.setOnMousePressed(e -> {
                System.out.println(cellData.getValue().getCustomer().getFirstName());
            });
            return new SimpleObjectProperty<>(cancelButton);
        });

        reservationTable.setItems(data);

    }

}
