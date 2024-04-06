package org.group.project.controllers.waiter;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.Optional;

public class WaiterApproveDeliveryViewController {

    @FXML
    private TableColumn<Order, String> customerColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, String> orderTimeColumn;

    @FXML
    private TableColumn<Order, String> orderListColumn;

    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private TableColumn<Order, Button> actionButtonColumn;

    @FXML
    private TableColumn<Order, Button> actionButtonColumn1;

    @FXML
    private TableColumn<Order, Button> actionButtonColumn2;

    @FXML
    private BorderPane borderPane;

    // TODO Delete all of these bgImage declared here as they're redundant
    @FXML
    private ImageView bgImage;

    private List<String> pendingDeliveryList;

    @FXML
    private TableView<Order> pendingDeliveryTable = new TableView<>();
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

        refreshPendingDeliveryList();

        customerColumn.setText("Customer");
        customerColumn.setMinWidth(150);
        customerColumn.setStyle("-fx-alignment: CENTER;");
        customerColumn.setCellValueFactory(
                new PropertyValueFactory<>("customer"));

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

        orderListColumn.setText("Item Ordered");
        orderListColumn.setMinWidth(200);
        orderListColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        orderListColumn.setCellValueFactory(cellData -> {
            String displayList = cellData.getValue().getListOfItemsForDisplay();
            return new SimpleObjectProperty<>(displayList);
        });

        orderStatusColumn.setText("Status");
        orderStatusColumn.setMinWidth(150);
        orderStatusColumn.setStyle("-fx-alignment: CENTER;");
        orderStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderStatus"));

        actionButtonColumn.setText("Action");

        actionButtonColumn1.setMinWidth(35);
        actionButtonColumn1.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            // TODO use tool tips for other buttons, where necessary
            viewButton.setTooltip(new Tooltip("View details"));
            ImageLoader.setUpGraphicButton(viewButton, 15, 15, "view-details");
            Customer customer = cellData.getValue().getCustomer();
//            String customerId = String.valueOf(cellData.getValue().getCustomer().getCustomerId());
//            String firstName = cellData.getValue().getCustomer().getFirstName();
//            String lastName = cellData.getValue().getCustomer().getLastName();
//            String customerAddress = cellData.getValue().getCustomer().getDeliveryAddress();
            int orderId = cellData.getValue().getOrderId();
            String orderStatus = cellData.getValue().getOrderStatus();
            DeliveryOrder deliveryOrder = (DeliveryOrder) cellData.getValue();
            LocalTime deliveryTime = deliveryOrder.getDeliveryTime();

            viewButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/waiter-editdeliveryorder" +
                                            ".fxml"));

                    VBox vbox = fxmlLoader.load();

                    WaiterEditDeliveryOrderController controller =
                            fxmlLoader.getController();

                    controller.populateOrderDetails(
                            orderId,
                            customer,
                            orderStatus,
                            deliveryTime
                    );
                    Scene editScene = new Scene(vbox,
                            WindowSize.MEDIUM.WIDTH,
                            WindowSize.MEDIUM.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    // TODO Should final variable this
                    editStage.setTitle("Edit Delivery Order");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshPendingDeliveryList();

                } catch (IOException ex) {
                    // TODO catch error
                    throw new RuntimeException(ex);
                }

            });

            return new SimpleObjectProperty<>(viewButton);
        });

        actionButtonColumn2.setMinWidth(35);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button cancelButton = new Button();
            // TODO use tool tips for other buttons, where necessary
            cancelButton.setTooltip(new Tooltip("Cancel"));
            ImageLoader.setUpGraphicButton(cancelButton, 15, 15, "cancel");
            int orderId = cellData.getValue().getOrderId();

            cancelButton.setOnAction(e -> {
                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        "Delivery Order Cancellation",
                        "Do you want to cancel this delivery order?"
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {
                    // TODO try catch
                    try {
                        DataManager.editColumnDataByUniqueId("ORDERS",
                                orderId, "orderStatus",
                                "failed");
                        // TODO notify customer here after delivery order cancellation

                        refreshPendingDeliveryList();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });


            return new SimpleObjectProperty<>(cancelButton);
        });

        pendingDeliveryTable.setItems(data);

    }

    // TODO made public so this list can be refreshed once pending delivery approved
    public void refreshPendingDeliveryList() throws FileNotFoundException {

        // TODO comment
        pendingDeliveryTable.getItems().clear();
        data.clear();

        // TODO to filter based on userid
        pendingDeliveryList = DataManager.allDataFromFile("ORDERS");

        for (String order : pendingDeliveryList) {
            List<String> orderDetails = List.of(order.split(","));

            // orderId
            int orderId = Integer.parseInt(orderDetails.get(DataFileStructure.getIndexByColName("ORDERS", "orderId")));

            // user
            Customer customer;

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

            if (customerString != null) {
                customer = new Customer(
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "firstName")),
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "lastName")),
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "username")),
                        Integer.parseInt(customerString.get(DataFileStructure.getIndexByColName("USERS", "userId"))),
                        HelperMethods.formatAddressToRead(customerString.get(DataFileStructure.getIndexByColName("USERS", "address")))
                );

                // TODO here's the filter for pending yet-to-be-approved deliveries
                // TODO to filer in "pending-approval" only?
                if (orderType.equalsIgnoreCase("delivery")
                        && orderStatus.equalsIgnoreCase("pending-approval")) {
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

                }
            }
        }
    }

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
