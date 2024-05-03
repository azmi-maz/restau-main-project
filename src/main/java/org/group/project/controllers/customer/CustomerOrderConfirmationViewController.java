package org.group.project.controllers.customer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.Customer;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.customer.stackViews.MenuController;
import org.group.project.scenes.customer.stackViews.OrderConfirmationController;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * This class enables the customer to confirm the order made.
 *
 * @author azmi_maz
 */
public class CustomerOrderConfirmationViewController {
    private static final String BG_IMAGE = "images" +
            "/background/main-bg" +
            ".jpg";
    private static final String NO_COLUMN = "No.";
    private static final int COLUMN_WIDTH_150 = 150;
    private static final int COLUMN_WIDTH_75 = 75;
    private static final int COLUMN_WIDTH_40 = 40;
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String ITEM_COLUMN = "Item";
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final String TYPE_COLUMN = "Type";
    private static final String QUANTITY_COLUMN = "Quantity";
    private static final String ITEM_TYPE = "itemType";
    private static final String QUANTITY = "quantity";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String DELIVERY_ORDER_TEMPLATE = "Order date: %s  %s"
            + System.lineSeparator() +
            "Customer id: %d"
            + System.lineSeparator() +
            "Customer name: %s"
            + System.lineSeparator() +
            "Delivery address: %s";
    private static final String TAKEAWAY_ORDER_TEMPLATE = "Order date: %s  %s"
            + System.lineSeparator() +
            "Customer id: %d"
            + System.lineSeparator() +
            "Customer name: %s";
    private static final String DELIVERY = "delivery";
    private static final String DELIVERY_ORDER = "Delivery Order";
    private static final String TAKEAWAY_ORDER = "Takeaway Order";
    private static final String OK_DONE = "OK_DONE";
    private static final String ORDER_SUCCESS = "Order Request Successful";
    private static final String ORDER_SUCCESS_MESSAGE = "Thank you! " +
            "Your request is being processed now.";
    private static final String OK = "Ok";
    private static final String CANCEL_ORDER = "Cancel Order Request";
    private static final String CANCEL_ORDER_MESSAGE = "Do you want " +
            "to cancel this order?";
    @FXML
    private Label orderTypeLabel;

    @FXML
    private Button cardButton;

    @FXML
    private Button cashButton;

    @FXML
    private Button cancelButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextArea orderDetailsTextArea;

    @FXML
    private TableColumn<FoodDrink, String> noColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemNameColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemTypeColumn;

    @FXML
    private TableColumn<FoodDrink, String> quantityColumn;

    @FXML
    private TableView<FoodDrink> orderDetailsTable =
            new TableView<>();

    private ObservableList<FoodDrink> data =
            FXCollections.observableArrayList();

    private List<Order> newOrder;

    private Order orderDetails;

    /**
     * This loads up the list of items the customer made.
     *
     * @param newOrder - the list of items to be confirmed.
     */
    public CustomerOrderConfirmationViewController(List<Order> newOrder) {
        this.newOrder = newOrder;
    }

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class
                    .getResource(BG_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
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

        orderDetails = newOrder.getFirst();

        data.addAll(orderDetails.getOrderedFoodDrinkLists());

        noColumn.setText(NO_COLUMN);
        noColumn.setMinWidth(COLUMN_WIDTH_40);
        noColumn.setStyle(CENTERED);
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(
                            cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        itemNameColumn.setText(ITEM_COLUMN);
        itemNameColumn.setMinWidth(COLUMN_WIDTH_150);
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

        orderDetailsTable.setItems(data);

        LocalDate orderDate = orderDetails.getOrderDate();
        LocalTime orderTime = orderDetails.getOrderTime();
        String orderType = orderDetails.getOrderType();
        int customerId = orderDetails.getCustomer().getCustomerId();
        String customerName = orderDetails.getCustomer().toString();
        String customerDeliveryAddress =
                orderDetails.getCustomer().getDeliveryAddress();

        // DeliveryOrder
        String deliveryOrderTemplate =
                String.format(
                        DELIVERY_ORDER_TEMPLATE,
                        orderDate.format(DateTimeFormatter.ofPattern(
                                DATE_FORMAT)),
                        orderTime.format(DateTimeFormatter.ofPattern(
                                TIME_FORMAT)),
                        customerId,
                        customerName,
                        customerDeliveryAddress
                );

        // TakeawayOrder
        String takeawayOrderTemplate =
                String.format(
                        TAKEAWAY_ORDER_TEMPLATE,
                        orderDate.format(DateTimeFormatter.ofPattern(
                                DATE_FORMAT)),
                        orderTime.format(DateTimeFormatter.ofPattern(
                                TIME_FORMAT)),
                        customerId,
                        customerName
                );

        if (orderType.equalsIgnoreCase(DELIVERY)) {
            orderTypeLabel.setText(DELIVERY_ORDER);
            orderDetailsTextArea.setText(deliveryOrderTemplate);
        } else {
            orderTypeLabel.setText(TAKEAWAY_ORDER);
            orderDetailsTextArea.setText(takeawayOrderTemplate);
        }

        // Updates database
        cardButton.setOnAction(e -> {
            addOrderToDatabase();
        });

        // Updates database
        cashButton.setOnAction(e -> {
            addOrderToDatabase();
        });

        // Cancels the whole order and return back to menu.
        cancelButton.setOnAction(e -> {

            Optional<ButtonType> userChoice = promptForUserAcknowledgement();

            if (userChoice.get()
                    .getButtonData().toString()
                    .equalsIgnoreCase(OK_DONE)) {
                cancelConfirmationAndGoBackToMenu();
            }

        });

    }

    /**
     * This method adds the new order to the database.
     */
    public void addOrderToDatabase() {

        Customer customer = (Customer) MainScenesMap.getCurrentUser();

        try {
            boolean isSuccessful = customer
                    .addNewOrder(
                            newOrder.getFirst()
                    );
            if (isSuccessful) {
                cancelConfirmationAndGoBackToMenu();
                promptOrderSuccessful();
            }
        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    private void promptOrderSuccessful() {
        AlertPopUpWindow.displayInformationWindow(
                ORDER_SUCCESS,
                ORDER_SUCCESS_MESSAGE,
                OK
        );
    }

    private Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                CANCEL_ORDER,
                CANCEL_ORDER_MESSAGE
        );
    }

    private void cancelConfirmationAndGoBackToMenu() {
        OrderConfirmationController.presenter.returnToOrderDetails();
        OrderDetailsController.presenter.returnToMenu();
        MenuController.orderList.clear();
    }
}
