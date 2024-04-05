package org.group.project.controllers.customer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class CustomerOrderHistoryViewController {

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, String> orderTimeColumn;

    @FXML
    private TableColumn<Order, String> orderTypeColumn;

    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private TableColumn<Order, Button> actionButtonColumn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView bgImage;

    private String userId;

    private List<String> orderHistoryList;

    @FXML
    private TableView<Order> orderHistoryTable = new TableView<>();
    private ObservableList<Order> data =
            FXCollections.observableArrayList();

    public void initialize() throws URISyntaxException, FileNotFoundException {

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

        // TODO get userId from the main.
        userId = "1";

        refreshOrderHistoryList();

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

        orderStatusColumn.setText("Status");
        orderStatusColumn.setMinWidth(150);
        orderStatusColumn.setStyle("-fx-alignment: CENTER;");
        orderStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderStatus"));

        actionButtonColumn.setText("Action");
        actionButtonColumn.setMinWidth(60);
        actionButtonColumn.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            // TODO use tool tips for other buttons, where necessary
            viewButton.setTooltip(new Tooltip("View details"));
            ImageLoader.setUpGraphicButton(viewButton, 15, 15, "view-details");
            LocalDate orderDate = cellData.getValue().getOrderDate();
            LocalTime orderTime =
                    cellData.getValue().getOrderTime();
            String orderType = cellData.getValue().getOrderType();
            String orderStatus = cellData.getValue().getOrderStatus();
            List<FoodDrink> orderList =
                    cellData.getValue().getOrderedFoodDrinkLists();

            Driver assignedDriver;
            LocalTime deliveryTime;
            if (orderType.equalsIgnoreCase("delivery")) {
                Order getOrder = cellData.getValue();
                DeliveryOrder deliveryOrder = (DeliveryOrder) getOrder;
                assignedDriver = deliveryOrder.getDriver();

                deliveryTime = deliveryOrder.getDeliveryTime();
            } else {
                assignedDriver = null;
                deliveryTime = null;
            }

            LocalTime estimatedPickupTime;
            if (orderType.equalsIgnoreCase("takeaway")) {
                Order getOrder = cellData.getValue();
                TakeawayOrder takeawayOrder = (TakeawayOrder) getOrder;
                estimatedPickupTime = takeawayOrder.getPickupTime();
            } else {
                estimatedPickupTime = null;
            }

            viewButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/customer-order-details" +
                                            ".fxml"));

                    VBox vbox = fxmlLoader.load();

                    CustomerOrderHistoryDetailsController controller =
                            fxmlLoader.getController();

                    controller.populateOrderDetails(
                            orderDate,
                            orderTime,
                            orderType,
                            orderStatus,
                            orderList,
                            assignedDriver,
                            deliveryTime,
                            estimatedPickupTime
                    );

                    Scene editScene = new Scene(vbox,
                            WindowSize.MEDIUM.WIDTH,
                            WindowSize.MEDIUM.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    // TODO Should final variable this
                    editStage.setTitle("View Order Details");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshOrderHistoryList();

                } catch (IOException ex) {
                    // TODO catch error
                    throw new RuntimeException(ex);
                }
            });
            return new SimpleObjectProperty<>(viewButton);
        });

        orderHistoryTable.setItems(data);

    }

    // TODO comment
    private void refreshOrderHistoryList() throws FileNotFoundException {

        // TODO comment
        orderHistoryTable.getItems().clear();
        data.clear();

        // TODO to filter based on userid
        orderHistoryList = DataManager.allDataFromFile("ORDERS");

        for (String order : orderHistoryList) {
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

            if (customerString != null) {
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
}
