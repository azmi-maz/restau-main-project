package org.group.project.controllers.driver;

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

public class DriverPendingDeliveryViewController {

    @FXML
    private TableColumn<Order, String> noColumn;

    @FXML
    private TableColumn<Order, Customer> customerColumn;

    @FXML
    private TableColumn<Order, String> deliveryTimeColumn;

    @FXML
    private TableColumn<Order, String> addressColumn;

    @FXML
    private TableColumn<Order, Button> actionButtonColumn;

    @FXML
    private BorderPane borderPane;

    private String userId;

    private List<String> pendingDeliveryList;

    @FXML
    private TableView<Order> pendingDeliveryTable = new TableView<>();
    private ObservableList<Order> data =
            FXCollections.observableArrayList();

    public void initialize() throws FileNotFoundException, URISyntaxException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/driver-main" +
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

        refreshPendingDeliveryList();

        noColumn.setText("No.");
        noColumn.setMinWidth(45);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        customerColumn.setText("Customer");
        customerColumn.setMinWidth(130);
        customerColumn.setStyle("-fx-alignment: CENTER;");
        customerColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue().getCustomer();
            return new SimpleObjectProperty<>(customer);
        });

        deliveryTimeColumn.setText("Est. Delivery Time");
        deliveryTimeColumn.setMinWidth(115);
        deliveryTimeColumn.setStyle("-fx-alignment: CENTER;");
        deliveryTimeColumn.setCellValueFactory(cellData -> {
            String formattedTime = cellData.getValue().getOrderTimeInFormat();
            return new SimpleObjectProperty<>(formattedTime);
        });

        addressColumn.setText("Address");
        addressColumn.setMinWidth(274);
        addressColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        addressColumn.setCellValueFactory(cellData -> {
            String customerAddress = cellData.getValue()
                    .getCustomer().getDeliveryAddress();
            return new SimpleObjectProperty<>(customerAddress);
        });

        actionButtonColumn.setText("Action");
        actionButtonColumn.setMinWidth(65);
        actionButtonColumn.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            // TODO use tool tips for other buttons, where necessary
            viewButton.setTooltip(new Tooltip("View details"));
            ImageLoader.setUpGraphicButton(viewButton, 15, 15, "view-details");
            Order currentOrder = cellData.getValue();

            viewButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/driver-confirmdelivery" +
                                            ".fxml"));

                    VBox vbox = fxmlLoader.load();

                    DriverPendingDeliveryDetailsController controller =
                            fxmlLoader.getController();

                    controller.setOrderDetails(
                            currentOrder
                    );

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    // TODO Should final variable this
                    editStage.setTitle("View Order Details");

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

        pendingDeliveryTable.setItems(data);

    }

    // TODO comment
    public void refreshPendingDeliveryList() throws FileNotFoundException {

        updateUserId();

        // TODO comment
        pendingDeliveryTable.getItems().clear();
        data.clear();

        // TODO to filter based on userid
        pendingDeliveryList = DataManager.allDataFromFile("ORDERS");

        for (String order : pendingDeliveryList) {
            List<String> orderDetails = List.of(order.split(","));

            String currentUserId = orderDetails.get(DataFileStructure.getIndexByColName("ORDERS", "assignedDriver"));

            if (currentUserId.equalsIgnoreCase(userId)) {

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

                if (customerString != null) {
                    customer = new Customer(
                            customerString.get(DataFileStructure.getIndexByColName("USERS", "firstName")),
                            customerString.get(DataFileStructure.getIndexByColName("USERS", "lastName")),
                            customerString.get(DataFileStructure.getIndexByColName("USERS", "username")),
                            Integer.parseInt(customerString.get(DataFileStructure.getIndexByColName("USERS", "userId"))),
                            HelperMethods.formatAddressToRead(customerString.get(DataFileStructure.getIndexByColName("USERS", "address")))
                    );

                    // TODO comment - filter is done here - need to filter the driver id
                    if (orderType.equalsIgnoreCase("delivery")
                            && orderStatus.equalsIgnoreCase("pending-delivery")) {
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
}
