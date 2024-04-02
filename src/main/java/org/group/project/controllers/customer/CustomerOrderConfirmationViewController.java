package org.group.project.controllers.customer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.AlertPopUpWindow;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Order;
import org.group.project.scenes.customer.stackViews.MenuController;
import org.group.project.scenes.customer.stackViews.OrderConfirmationController;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    private ImageView bgImage;

    private List<Order> newOrder;

    private Order orderDetails;

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

    public CustomerOrderConfirmationViewController(List<Order> newOrder) {
        this.newOrder = newOrder;
    }

    public void initialize() throws URISyntaxException {

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

        orderDetails = newOrder.getFirst();

        data.addAll(orderDetails.getOrderedFoodDrinkLists());

        noColumn.setText("No.");
        noColumn.setMinWidth(40);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(cellData.getValue());
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

        String deliveryOrderTemplate =
                "Order date: " +
                        orderDate.format(DateTimeFormatter.ofPattern("dd/MM" +
                                "/yyyy")) + "  " +
                        orderTime.format(DateTimeFormatter.ofPattern("HH:mm " +
                                "a")) + "\n" +
                        "Customer id: " +
                        customerId + "\n" +
                        "Customer name: " +
                        customerName + "\n" +
                        "Delivery address: " +
                        customerDeliveryAddress;

        String takeawayOrderTemplate =
                "Order date: " +
                        orderDate.format(DateTimeFormatter.ofPattern("dd/MM" +
                                "/yyyy")) + "  " +
                        orderTime.format(DateTimeFormatter.ofPattern("HH:mm " +
                                "a")) + "\n" +
                        "Customer id: " +
                        customerId + "\n" +
                        "Customer name: " +
                        customerName;

        if (orderType.equalsIgnoreCase("delivery")) {
            orderTypeLabel.setText("Delivery Order");
            orderDetailsTextArea.setText(deliveryOrderTemplate);
        } else {
            orderTypeLabel.setText("Takeaway Order");
            orderDetailsTextArea.setText(takeawayOrderTemplate);
        }

        // TODO write to database
        cardButton.setOnAction(e -> {
//            addOrderToDatabase();
//            cancelConfirmationAndGoBackToMenu();
            promptOrderSuccessful();
        });

        // TODO fix order, check if it works
        cashButton.setOnAction(e -> {
//            addOrderToDatabase();
//            cancelConfirmationAndGoBackToMenu();
            promptOrderSuccessful();

        });

        // TODO fix order, check if it works
        cancelButton.setOnAction(e -> {

            Optional<ButtonType> userChoice = promptForUserAcknowledgement();

            if (userChoice.get()
                    .getButtonData().toString()
                    .equalsIgnoreCase("OK_DONE")) {
                cancelConfirmationAndGoBackToMenu();
            }

        });

    }

    // TODO add order to database
    public void addOrderToDatabase() {

    }

    public void promptOrderSuccessful() {
        AlertPopUpWindow.displayInformationWindow(
                "Order Request Successful",
                "Thank you for your order!",
                "Ok"
        );
    }

    public Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                "Cancel Order Request",
                "Do you want to cancel this order?"
        );
    }

    public void cancelConfirmationAndGoBackToMenu() {
        OrderConfirmationController.presenter.returnToOrderDetails();
        OrderDetailsController.presenter.returnToMenu();
        MenuController.orderList.clear();
    }
}
