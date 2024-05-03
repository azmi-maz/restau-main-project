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
    private static final String NO_COLUMN = "No.";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final int COLUMN_WIDTH_75 = 75;
    private static final int COLUMN_WIDTH_40 = 40;
    private static final int COLUMN_WIDTH_217 = 217;
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final String ITEM_COLUMN = "Item";
    private static final String ITEM_TYPE = "itemType";
    private static final String TYPE_COLUMN = "Type";
    private static final String QUANTITY_COLUMN = "Quantity";
    private static final String QUANTITY = "quantity";
    private static final String DELIVERY = "delivery";
    private static final String TAKEAWAY = "takeaway";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String DELIVERY_ORDER_TEMPLATE = "Order no. %d"
            + System.lineSeparator() +
            "Order date: %s  %s"
            + System.lineSeparator() +
            "Status: %s"
            + System.lineSeparator() +
            "Driver: %s"
            + System.lineSeparator() +
            "Est. delivery time: ";

    private static final String TAKEAWAY_ORDER_TEMPLATE = "Order no. %d"
            + System.lineSeparator() +
            "Order date: %s  %s"
            + System.lineSeparator() +
            "Status: %s"
            + System.lineSeparator() +
            "Pickup time: ";

    private static final String DINEIN_ORDER_TEMPLATE = "Order date: %s  %s"
            + System.lineSeparator() +
            "Status: %s"
            + System.lineSeparator();

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

        noColumn.setText(NO_COLUMN);
        noColumn.setMinWidth(COLUMN_WIDTH_40);
        noColumn.setStyle(CENTERED);
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems()
                            .indexOf(cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        itemNameColumn.setText(ITEM_COLUMN);
        itemNameColumn.setMinWidth(COLUMN_WIDTH_217);
        itemNameColumn.setStyle(CENTER_LEFT);
        itemNameColumn.setCellValueFactory(cellData -> {
            String itemName = cellData.getValue().getItemNameForDisplay();
            return new SimpleObjectProperty<>(itemName);
        });

        itemTypeColumn.setText(TYPE_COLUMN);
        itemTypeColumn.setMinWidth(COLUMN_WIDTH_75);
        itemTypeColumn.setStyle(CENTERED);
        itemTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>(ITEM_TYPE));

        quantityColumn.setText(QUANTITY_COLUMN);
        quantityColumn.setMinWidth(COLUMN_WIDTH_75);
        quantityColumn.setStyle(CENTERED);
        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>(QUANTITY));

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
        if (orderType.equalsIgnoreCase(DELIVERY)) {
            DeliveryOrder deliveryOrder = (DeliveryOrder) selectedOrder;
            assignedDriver = deliveryOrder.getDriver();
            deliveryTime = deliveryOrder.getDeliveryTime();
        } else if (orderType.equalsIgnoreCase(TAKEAWAY)) {
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
                    String.format(
                            DELIVERY_ORDER_TEMPLATE,
                            orderId,
                            orderDate.format(DateTimeFormatter.ofPattern(
                                    DATE_FORMAT)),
                            orderTime.format(DateTimeFormatter.ofPattern(
                                    TIME_FORMAT)),
                            orderStatus,
                            assignedDriver
                    );

            if (deliveryTime != null) {
                deliveryOrderTemplate += deliveryTime.format(
                        DateTimeFormatter.ofPattern(TIME_FORMAT))
                        + System.lineSeparator();
            }

            // Takeaway order
            String takeawayOrderTemplate =
                    String.format(
                            TAKEAWAY_ORDER_TEMPLATE,
                            orderId,
                            orderDate.format(DateTimeFormatter.ofPattern(
                                    DATE_FORMAT)),
                            orderTime.format(DateTimeFormatter.ofPattern(
                                    TIME_FORMAT)),
                            orderStatus
                    );

            if (estimatedPickupTime != null) {
                takeawayOrderTemplate += estimatedPickupTime.format(
                        DateTimeFormatter.ofPattern(TIME_FORMAT))
                        + System.lineSeparator();
            }

            // Dinein order
            String dineinOrderTemplate =
                    String.format(
                            DINEIN_ORDER_TEMPLATE,
                            orderDate.format(DateTimeFormatter.ofPattern(
                                    DATE_FORMAT)),
                            orderTime.format(DateTimeFormatter.ofPattern(
                                    TIME_FORMAT)),
                            orderStatus
                    );

            if (orderType.equalsIgnoreCase(DELIVERY)) {
                orderDetailsTextArea.setText(deliveryOrderTemplate);
            } else if (orderType.equalsIgnoreCase(TAKEAWAY)) {
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
