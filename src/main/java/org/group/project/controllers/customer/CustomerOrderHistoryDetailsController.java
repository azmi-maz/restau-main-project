package org.group.project.controllers.customer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class enables the customer to view their order history details.
 *
 * @author azmi_maz
 */
public class CustomerOrderHistoryDetailsController {

    @FXML
    private TableColumn<FoodDrink, String> noColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemNameColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemTypeColumn;

    @FXML
    private TableColumn<FoodDrink, String> quantityColumn;

    @FXML
    private Button returnButton;

    @FXML
    private VBox vbox;

    @FXML
    private TextArea orderDetailsTextArea;

    @FXML
    private TableView<FoodDrink> orderHistoryTable = new TableView<>();
    private ObservableList<FoodDrink> data =
            FXCollections.observableArrayList();

    private List<FoodDrink> orderList = new ArrayList<>();

    private int orderId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private String orderType;
    private String orderStatus;
    private Driver assignedDriver;
    private LocalTime deliveryTime;
    private LocalTime estimatedPickupTime;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        noColumn.setText("No.");
        noColumn.setMinWidth(40);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems()
                            .indexOf(cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        itemNameColumn.setText("Item");
        itemNameColumn.setMinWidth(217);
        itemNameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        itemNameColumn.setCellValueFactory(cellData -> {
            String itemName = cellData.getValue().getItemNameForDisplay();
            return new SimpleObjectProperty<>(itemName);
        });

        itemTypeColumn.setText("Type");
        itemTypeColumn.setMinWidth(75);
        itemTypeColumn.setStyle("-fx-alignment: CENTER;");
        itemTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("itemType"));

        quantityColumn.setText("Quantity");
        quantityColumn.setMinWidth(75);
        quantityColumn.setStyle("-fx-alignment: CENTER;");
        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        orderHistoryTable.setItems(data);

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    /**
     * This method populates the selected order details.
     *
     * @param selectedOrder - the selected order.
     */
    public void populateOrderDetails(
            Order selectedOrder
    ) {
        orderList.addAll(selectedOrder.getOrderedFoodDrinkLists());
        orderId = selectedOrder.getOrderId();
        orderDate = selectedOrder.getOrderDate();
        orderTime = selectedOrder.getOrderTime();
        orderType = selectedOrder.getOrderType();
        orderStatus = selectedOrder.getOrderStatus();
        if (orderType.equalsIgnoreCase("delivery")) {
            DeliveryOrder deliveryOrder = (DeliveryOrder) selectedOrder;
            assignedDriver = deliveryOrder.getDriver();
            deliveryTime = deliveryOrder.getDeliveryTime();
        } else if (orderType.equalsIgnoreCase("takeaway")) {
            TakeawayOrder takeawayOrder = (TakeawayOrder) selectedOrder;
            estimatedPickupTime = takeawayOrder.getPickupTime();
        }

        refreshList();
    }

    private void refreshList() {
        orderHistoryTable.getItems().clear();
        data.clear();

        for (FoodDrink item : orderList) {
            data.add(item);
        }

        if (orderDate != null) {

            // Delivery order
            String deliveryOrderTemplate =
                    "Order no. " +
                            orderId + System.lineSeparator() +
                            "Order date: " +
                            orderDate.format(DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy")) + "  " +
                            orderTime.format(DateTimeFormatter.ofPattern(
                                    "hh:mm a")) + System.lineSeparator() +
                            "Status: " +
                            orderStatus + System.lineSeparator() +
                            "Driver: " +
                            assignedDriver + System.lineSeparator() +
                            "Est. delivery time: ";

            if (deliveryTime != null) {
                deliveryOrderTemplate += deliveryTime.format(
                        DateTimeFormatter.ofPattern("hh:mm a"))
                        + System.lineSeparator();
            }

            // Takeaway order
            String takeawayOrderTemplate =
                    "Order no. " +
                            orderId + System.lineSeparator() +
                            "Order date: " +
                            orderDate.format(DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy")) + "  " +
                            orderTime.format(DateTimeFormatter.ofPattern(
                                    "hh:mm a")) + System.lineSeparator() +
                            "Status: " +
                            orderStatus + System.lineSeparator() +
                            "Pickup time: ";

            if (estimatedPickupTime != null) {
                takeawayOrderTemplate += estimatedPickupTime.format(
                        DateTimeFormatter.ofPattern("hh:mm " +
                                "a")) + System.lineSeparator();
            }

            // Dinein order
            String dineinOrderTemplate =
                    "Order date: " +
                            orderDate.format(DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy")) + "  " +
                            orderTime.format(DateTimeFormatter.ofPattern(
                                    "hh:mm a")) + System.lineSeparator() +
                            "Status: " +
                            orderStatus + System.lineSeparator();

            if (orderType.equalsIgnoreCase("delivery")) {
                orderDetailsTextArea.setText(deliveryOrderTemplate);
            } else if (orderType.equalsIgnoreCase("takeaway")) {
                orderDetailsTextArea.setText(takeawayOrderTemplate);
            } else {
                orderDetailsTextArea.setText(dineinOrderTemplate);
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
