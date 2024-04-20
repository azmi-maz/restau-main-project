package org.group.project.controllers.chef;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChefOutstandingOrdersViewController {

    @FXML
    private TableColumn<Order, String> orderNoColumn;

    @FXML
    private TableColumn<Order, String> customerColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, String> orderTimeColumn;

    @FXML
    private TableColumn<Order, String> orderTypeColumn;

    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private TableColumn<Order, Button> actionButtonColumn;

    @FXML
    private BorderPane borderPane;

    private List<String> outstandingOrdersList;

    @FXML
    private TableView<Order> outstandingOrdersTable = new TableView<>();
    private ObservableList<Order> data =
            FXCollections.observableArrayList();

    // TODO comment
    public void initialize() throws URISyntaxException, FileNotFoundException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/chef-main" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        refreshOutstandingOrdersList();

        orderNoColumn.setText("Order No.");
        orderNoColumn.setMinWidth(65);
        orderNoColumn.setStyle("-fx-alignment: CENTER;");
        orderNoColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderId"));

        customerColumn.setText("Customer");
        customerColumn.setMinWidth(150);
        customerColumn.setStyle("-fx-alignment: CENTER;");
        customerColumn.setCellValueFactory(cellData -> {
            String customerName =
                    cellData.getValue().getCustomer().getDataForListDisplay();
            return new SimpleObjectProperty<>(customerName);
        });

        orderDateColumn.setText("Date");
        orderDateColumn.setMinWidth(150);
        orderDateColumn.setStyle("-fx-alignment: CENTER;");
        orderDateColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getOrderDateInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        orderTimeColumn.setText("Time");
        orderTimeColumn.setMinWidth(150);
        orderTimeColumn.setStyle("-fx-alignment: CENTER;");
        orderTimeColumn.setCellValueFactory(cellData -> {
            String formattedTime = cellData.getValue().getOrderTimeInFormat();
            return new SimpleObjectProperty<>(formattedTime);
        });

        orderTypeColumn.setText("Type");
        orderTypeColumn.setMinWidth(150);
        orderTypeColumn.setStyle("-fx-alignment: CENTER;");
        orderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderType"));

        itemsColumn.setText("Items");
        itemsColumn.setMinWidth(200);
        itemsColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        itemsColumn.setCellValueFactory(cellData -> {
            String orderList = cellData.getValue().getListOfItemsForDisplay();
            return new SimpleObjectProperty<>(orderList);
        });

        orderStatusColumn.setText("Status");
        orderStatusColumn.setMinWidth(150);
        orderStatusColumn.setStyle("-fx-alignment: CENTER;");
        orderStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderStatus"));

        actionButtonColumn.setText("Action");
        actionButtonColumn.setMinWidth(45);
        actionButtonColumn.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button markAsCompleteButton = new Button();
            markAsCompleteButton.setTooltip(new Tooltip("Mark as complete"));
            ImageLoader.setUpGraphicButton(markAsCompleteButton, 15, 15, "confirm");
            Order selectedOrder = cellData.getValue();

            markAsCompleteButton.setOnAction(e -> {

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        "Update Pending Order",
                        "Do you want to mark this order as complete?"
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {
                    // TODO try catch
                    try {
                        selectedOrder.markOffOrderAsComplete();
                        if (selectedOrder.getOrderType().equalsIgnoreCase("takeaway")) {
                            TakeawayOrder takeawayOrder = (TakeawayOrder) selectedOrder;
                            System.out.println("reached here");
//                            System.out.println(takeawayOrder.getPickupTimeInFormat());
                            takeawayOrder.setEstimatedPickupTime();
                            System.out.println(takeawayOrder.getPickupTimeInFormat());
                            takeawayOrder.notifyCustomer(
                                    takeawayOrder.getCustomer(),
                                    true
                            );
                        }

                        refreshOutstandingOrdersList();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            });

            return new SimpleObjectProperty<>(markAsCompleteButton);
        });

        outstandingOrdersTable.setItems(data);
    }

    // TODO comment
    public void refreshOutstandingOrdersList() throws FileNotFoundException {

        // TODO comment
        outstandingOrdersTable.getItems().clear();
        data.clear();

        outstandingOrdersList = DataManager.allDataFromFile("ORDERS");

        for (String order : outstandingOrdersList) {
            List<String> orderDetails = List.of(order.split(","));
            // orderId
            int orderId = Integer.parseInt(orderDetails.get(DataFileStructure.getIndexByColName("ORDERS", "orderId")));

            // user
            Customer customer;

            // TODO filter by current userId
            List<String> customerString = HelperMethods.getDataById("USERS",
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "BOOKINGS", "userId")));

            // orderDate
            List<String> orderDateDetails = List.of(orderDetails.get(2).split("-"));

            LocalDate orderDate =
                    LocalDate.of(Integer.parseInt(orderDateDetails.get(0)),
                            Integer.parseInt(orderDateDetails.get(1)),
                            Integer.parseInt(orderDateDetails.get(2)));

            // orderTime
            List<String> orderTimeDetails = List.of(orderDetails.get(3).split("-"));

            LocalTime orderTime =
                    LocalTime.of(Integer.parseInt(orderTimeDetails.get(0)),
                            Integer.parseInt(orderTimeDetails.get(1)));

            // orderType
            String orderType =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderType"));

            // orderStatus
            String orderStatus =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderStatus"));

            // orderedFoodDrinkLists
            String[] orderListArray =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderedLists")).split(";");
            List<FoodDrink> orderFoodDrinkList = new ArrayList<>();
            for (String item : orderListArray) {
                // TODO find item type by item name
                String itemType = "food";
                FoodDrink newItem = new FoodDrink(item, itemType);
                boolean isNewItem = true;
                for (FoodDrink currentItem : orderFoodDrinkList) {
                    if (currentItem.getItemName().equalsIgnoreCase(item)) {
                        currentItem.incrementQuantity();
                        isNewItem = false;
                    }
                }
                if (isNewItem) {
                    orderFoodDrinkList.add(newItem);
                }
            }

            // assignedDriver and deliveryTime
            LocalTime deliveryTime = null;
            Driver assignedDriver = null;

            if (orderType.equalsIgnoreCase("delivery")) {
                List<String> deliveryTimeDetails =
                        List.of(orderDetails.get(DataFileStructure.getIndexByColName(
                                "ORDERS", "deliveryTime")).split(
                                "-"));
                if (deliveryTimeDetails.size() == 2) {
                    deliveryTime = LocalTime.of(Integer.parseInt(deliveryTimeDetails.get(0)),
                            Integer.parseInt(deliveryTimeDetails.get(1)));
                }
                List<String> driverString = HelperMethods.getDataById("USERS",
                        orderDetails.get(DataFileStructure.getIndexByColName(
                                "ORDERS", "assignedDriver")));

                if (driverString != null) {
                    assignedDriver = new Driver(
                            Integer.parseInt(driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "userId"))),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "firstName")),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "lastName")),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "username"))
                    );
                }
            }

            // estimatedPickupTime
            LocalTime pickupTime = null;

            if (orderType.equalsIgnoreCase("takeaway")) {
                List<String> pickupTimeDetails =
                        List.of(orderDetails.get(DataFileStructure.getIndexByColName(
                                "ORDERS", "estimatedPickupTime")).split(
                                "-"));
                if (pickupTimeDetails.size() == 2) {
                    pickupTime =
                            LocalTime.of(Integer.parseInt(pickupTimeDetails.get(0)),
                                    Integer.parseInt(pickupTimeDetails.get(1)));
                }
            }

            // TODO comment here the filter applies to pending-kitchen
            if (customerString != null
                    && orderStatus.equalsIgnoreCase("pending-kitchen")) {
                customer = new Customer(
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "firstName")),
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "lastName")),
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "username")),
                        Integer.parseInt(customerString.get(DataFileStructure.getIndexByColName("USERS", "userId"))),
                        HelperMethods.formatAddressToRead(customerString.get(DataFileStructure.getIndexByColName("USERS", "address")))
                );
                if (orderType.equalsIgnoreCase("delivery")) {
                    data.add(new DeliveryOrder(
                            orderId,
                            customer,
                            orderDate,
                            orderTime,
                            deliveryTime,
                            orderStatus,
                            assignedDriver,
                            orderFoodDrinkList
                    ));

                } else if (orderType.equalsIgnoreCase("takeaway")) {
                    data.add(new TakeawayOrder(
                            orderId,
                            customer,
                            orderDate,
                            orderTime,
                            pickupTime,
                            orderStatus,
                            orderFoodDrinkList
                    ));

                } else {
                    data.add(new Order(
                            orderId,
                            customer,
                            orderDate,
                            orderTime,
                            orderType,
                            orderStatus,
                            orderFoodDrinkList
                    ));

                }
            }
        }
    }

    // TODO make this private or send it up to AlertType class
    public Optional<ButtonType> promptForUserAcknowledgement(
            String header,
            String message
    ) {
        return AlertPopUpWindow.displayConfirmationWindow(
                header,
                message
        );
    }
}
