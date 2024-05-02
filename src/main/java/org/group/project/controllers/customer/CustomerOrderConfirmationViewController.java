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
            bgImage = new Image(Main.class.getResource("images" +
                    "/background/main-bg" +
                    ".jpg").toURI().toString());
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

        noColumn.setText("No.");
        noColumn.setMinWidth(40);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(
                            cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        itemNameColumn.setText("Item");
        itemNameColumn.setMinWidth(150);
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
                "Order date: " +
                        orderDate.format(DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy")) + "  " +
                        orderTime.format(DateTimeFormatter.ofPattern(
                                "hh:mm a")) + System.lineSeparator() +
                        "Customer id: " +
                        customerId + System.lineSeparator() +
                        "Customer name: " +
                        customerName + System.lineSeparator() +
                        "Delivery address: " +
                        customerDeliveryAddress;

        // TakeawayOrder
        String takeawayOrderTemplate =
                "Order date: " +
                        orderDate.format(DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy")) + "  " +
                        orderTime.format(DateTimeFormatter.ofPattern(
                                "hh:mm a")) + System.lineSeparator() +
                        "Customer id: " +
                        customerId + System.lineSeparator() +
                        "Customer name: " +
                        customerName;

        if (orderType.equalsIgnoreCase("delivery")) {
            orderTypeLabel.setText("Delivery Order");
            orderDetailsTextArea.setText(deliveryOrderTemplate);
        } else {
            orderTypeLabel.setText("Takeaway Order");
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
                    .equalsIgnoreCase("OK_DONE")) {
                cancelConfirmationAndGoBackToMenu();
            }

        });

    }

    /**
     * This method adds the new order to the database.
     */
    public void addOrderToDatabase() {

        Customer customer = (Customer) Main.getCurrentUser();

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
                "Order Request Successful",
                "Thank you! Your request is being processed now.",
                "Ok"
        );
    }

    private Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                "Cancel Order Request",
                "Do you want to cancel this order?"
        );
    }

    private void cancelConfirmationAndGoBackToMenu() {
        OrderConfirmationController.presenter.returnToOrderDetails();
        OrderDetailsController.presenter.returnToMenu();
        MenuController.orderList.clear();
    }
}
