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
import org.group.project.classes.Chef;
import org.group.project.classes.Kitchen;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;

import java.net.URISyntaxException;
import java.util.Optional;

/**
 * This class loads up the outstanding orders view for chef.
 */
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

    @FXML
    private TableView<Order> outstandingOrdersTable = new TableView<>();
    private ObservableList<Order> data =
            FXCollections.observableArrayList();

    /**
     * This initializes the controller for the fxml.
     *
     * @throws URISyntaxException - the image uri did not work.
     */
    public void initialize() throws URISyntaxException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/chef-main" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false,
                false, true, true);

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
            String customerName = cellData.getValue()
                    .getCustomer().getDataForListDisplay();
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
            String formattedTime = cellData.getValue()
                    .getOrderTimeInFormat();
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
            String orderList = cellData.getValue()
                    .getListOfItemsForDisplay();
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
            markAsCompleteButton.setTooltip(
                    new Tooltip("Mark as complete"));
            ImageLoader.setUpGraphicButton(markAsCompleteButton,
                    15, 15, "confirm");
            Order selectedOrder = cellData.getValue();

            markAsCompleteButton.setOnAction(e -> {
                Chef chef = (Chef) Main.getCurrentUser();

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        "Update Pending Order",
                        "Do you want to mark this order as complete?"
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {

                    boolean isSuccessful = false;
                    try {
                        isSuccessful = chef.updateOrderStatus(selectedOrder);
                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                "Error",
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }
                    if (isSuccessful) {
                        AlertPopUpWindow.displayInformationWindow(
                                "Order Status",
                                "Thank you for completing this order.",
                                "Ok"
                        );
                    }

                    refreshOutstandingOrdersList();


                }

            });

            return new SimpleObjectProperty<>(markAsCompleteButton);
        });

        outstandingOrdersTable.setItems(data);
    }

    /**
     * This method refreshes the outstanding orders list.
     */
    public void refreshOutstandingOrdersList() {

        // TODO comment
        outstandingOrdersTable.getItems().clear();
        data.clear();

        try {

            Kitchen kitchen = new Kitchen();
            kitchen.getPendingKitchenOrderData(data);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    private Optional<ButtonType> promptForUserAcknowledgement(
            String header,
            String message
    ) {
        return AlertPopUpWindow.displayConfirmationWindow(
                header,
                message
        );
    }
}
