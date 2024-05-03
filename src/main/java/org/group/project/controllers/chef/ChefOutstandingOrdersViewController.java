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
import org.group.project.scenes.MainScenesMap;

import java.net.URISyntaxException;
import java.util.Optional;

/**
 * This class loads up the outstanding orders view for chef.
 *
 * @author azmi_maz
 */
public class ChefOutstandingOrdersViewController {
    private static final String BG_IMAGE = "images" +
            "/background/chef-main" +
            ".jpg";
    private static final int COLUMN_WIDTH_45 = 45;
    private static final int COLUMN_WIDTH_65 = 65;
    private static final int COLUMN_WIDTH_150 = 150;
    private static final int COLUMN_WIDTH_200 = 200;
    private static final String NO_COLUMN = "Order No.";
    private static final String CUSTOMER_COLUMN = "Customer";
    private static final String DATE_COLUMN = "Date";
    private static final String TIME_COLUMN = "Time";
    private static final String TYPE_COLUMN = "Type";
    private static final String ITEMS_COLUMN = "Items";
    private static final String STATUS_COLUMN = "Status";
    private static final String ACTION_COLUMN = "Action";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final String ORDER_ID = "orderId";
    private static final String ORDER_TYPE = "orderType";
    private static final String ORDER_STATUS = "orderStatus";
    private static final String MARK_COMPLETE = "Mark as complete";
    private static final String CONFIRM = "confirm";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String UPDATE_PENDING_TITLE = "Update Pending Order";
    private static final String UPDATE_PENDING_MESSAGE = "Do you want " +
            "to mark this order as complete?";
    private static final String OK_DONE = "OK_DONE";
    private static final String ORDER_STATUS_TITLE = "Order Status";
    private static final String ORDER_STATUS_MESSAGE = "Thank you for " +
            "completing this order.";
    private static final String OK = "Ok";
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

        refreshOutstandingOrdersList();

        orderNoColumn.setText(NO_COLUMN);
        orderNoColumn.setMinWidth(COLUMN_WIDTH_65);
        orderNoColumn.setStyle(CENTERED);
        orderNoColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_ID));

        customerColumn.setText(CUSTOMER_COLUMN);
        customerColumn.setMinWidth(COLUMN_WIDTH_150);
        customerColumn.setStyle(CENTERED);
        customerColumn.setCellValueFactory(cellData -> {
            String customerName = cellData.getValue()
                    .getCustomer().getDataForListDisplay();
            return new SimpleObjectProperty<>(customerName);
        });

        orderDateColumn.setText(DATE_COLUMN);
        orderDateColumn.setMinWidth(COLUMN_WIDTH_150);
        orderDateColumn.setStyle(CENTERED);
        orderDateColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getOrderDateInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        orderTimeColumn.setText(TIME_COLUMN);
        orderTimeColumn.setMinWidth(COLUMN_WIDTH_150);
        orderTimeColumn.setStyle(CENTERED);
        orderTimeColumn.setCellValueFactory(cellData -> {
            String formattedTime = cellData.getValue()
                    .getOrderTimeInFormat();
            return new SimpleObjectProperty<>(formattedTime);
        });

        orderTypeColumn.setText(TYPE_COLUMN);
        orderTypeColumn.setMinWidth(COLUMN_WIDTH_150);
        orderTypeColumn.setStyle(CENTERED);
        orderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_TYPE));

        itemsColumn.setText(ITEMS_COLUMN);
        itemsColumn.setMinWidth(COLUMN_WIDTH_200);
        itemsColumn.setStyle(CENTER_LEFT);
        itemsColumn.setCellValueFactory(cellData -> {
            String orderList = cellData.getValue()
                    .getListOfItemsForDisplay();
            return new SimpleObjectProperty<>(orderList);
        });

        orderStatusColumn.setText(STATUS_COLUMN);
        orderStatusColumn.setMinWidth(COLUMN_WIDTH_150);
        orderStatusColumn.setStyle(CENTERED);
        orderStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_STATUS));

        actionButtonColumn.setText(ACTION_COLUMN);
        actionButtonColumn.setMinWidth(COLUMN_WIDTH_45);
        actionButtonColumn.setStyle(CENTERED);
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button markAsCompleteButton = new Button();
            markAsCompleteButton.setTooltip(
                    new Tooltip(MARK_COMPLETE));
            ImageLoader.setUpGraphicButton(markAsCompleteButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, CONFIRM);
            Order selectedOrder = cellData.getValue();

            markAsCompleteButton.setOnAction(e -> {
                Chef chef = (Chef) MainScenesMap.getCurrentUser();

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        UPDATE_PENDING_TITLE,
                        UPDATE_PENDING_MESSAGE
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase(OK_DONE)) {

                    boolean isSuccessful = false;
                    try {
                        isSuccessful = chef.updateOrderStatus(selectedOrder);
                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }
                    if (isSuccessful) {
                        AlertPopUpWindow.displayInformationWindow(
                                ORDER_STATUS_TITLE,
                                ORDER_STATUS_MESSAGE,
                                OK
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

        outstandingOrdersTable.getItems().clear();
        data.clear();

        try {

            Kitchen kitchen = new Kitchen();
            kitchen.getPendingKitchenOrderData(data);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
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
