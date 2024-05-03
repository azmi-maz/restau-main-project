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
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.*;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class enables the waiter to serve a dine-in customer.
 *
 * @author azmi_maz
 */
public class WaiterDineinOrderController {
    private static final String BG_IMAGE = "images" +
            "/background/waiter-main" +
            ".jpg";
    private static final String PLUS_BUTTON = "circle-plus";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String ADD_ORDER_WINDOW = "smallwindows/" +
            "add-dineinorder-item" +
            ".fxml";
    private static final String ADD_ORDER_TITLE = "Add Order Item";
    private static final String INCOMPLETE = "Please complete table info.";
    private static final String MIN_ONE_REQUIRED = "Please select at " +
            "least one item.";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final int COLUMN_WIDTH_35 = 35;
    private static final int COLUMN_WIDTH_40 = 40;
    private static final int COLUMN_WIDTH_75 = 75;
    private static final int COLUMN_WIDTH_150 = 150;
    private static final String NO_COLUMN = "No.";
    private static final String ITEM_COLUMN = "Item";
    private static final String TYPE_COLUMN = "Type";
    private static final String TYPE = "type";
    private static final String QUANTITY_COLUMN = "Quantity";
    private static final String QUANTITY = "quantity";
    private static final String ACTION_COLUMN = "Action";
    private static final String EDIT_BUTTON = "edit";
    private static final String EDIT_ORDER_WINDOW = "smallwindows/" +
            "edit-dineinorder-item" +
            ".fxml";
    private static final String EDIT_ORDER_TITLE = "Edit Order Item";
    private static final String DELETE_BUTTON = "delete";
    private static final String DELETE_ITEM_TITLE = "Delete Item";
    private static final String DELETE_ITEM_MESSAGE = "Do you want to " +
            "delete %s?";
    private static final String OK_DONE = "OK_DONE";
    private static final String OK = "Ok";
    private static final String ORDER_SUCCESS_TITLE = "Order Successful";
    private static final String ORDER_SUCCESS_MESSAGE = "Thank you! Your " +
            "request is being processed now.";
    private static final String CANCEL_ORDER_TITLE = "Cancel Order Request";
    private static final String CANCEL_ORDER_MESSAGE = "Do you want to " +
            "cancel this order?";
    @FXML
    private BorderPane borderPane;

    @FXML
    private ChoiceBox<Table> tableChoiceBox;

    @FXML
    private ChoiceBox<Customer> customerChoiceBox;

    @FXML
    private TableColumn<FoodDrink, String> noColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemNameColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemTypeColumn;

    @FXML
    private TableColumn<FoodDrink, String> quantityColumn;

    @FXML
    private TableColumn<FoodDrink, Button> actionButtonColumn;

    @FXML
    private TableColumn<FoodDrink, Button> actionButtonColumn1;

    @FXML
    private TableColumn<FoodDrink, Button> actionButtonColumn2;

    @FXML
    private Button newItemButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelOrderButton;

    private List<Customer> customerList = new ArrayList<>();

    private List<FoodDrink> orderList = new ArrayList<>();

    @FXML
    private TableView<FoodDrink> orderDetailsTable =
            new TableView<>();

    private ObservableList<FoodDrink> data =
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

        try {

            Floor floor = new Floor();
            floor.updateTableChoiceBox(
                    tableChoiceBox
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        refreshCustomerList();

        updateCustomerChoiceBox();

        ImageLoader.setUpGraphicButton(newItemButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, PLUS_BUTTON);

        newItemButton.setOnAction(e -> {

            if (
                    tableChoiceBox.getValue() != null
                            && customerChoiceBox.getValue() != null
            ) {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    ADD_ORDER_WINDOW));

                    VBox vbox = fxmlLoader.load();

                    WaiterDineinAddOrderController controller =
                            fxmlLoader.getController();

                    controller.getCurrentOrderList(
                            orderList
                    );

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle(ADD_ORDER_TITLE);

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshOrderList();

                } catch (IOException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

            } else {
                AlertPopUpWindow.displayErrorWindow(
                        INCOMPLETE
                );
            }

        });

        confirmButton.setOnAction(e -> {
            Waiter waiter = (Waiter) MainScenesMap.getCurrentUser();

            if (
                    tableChoiceBox.getValue() != null
                            && customerChoiceBox.getValue() != null
                            && !orderList.isEmpty()
            ) {
                int customerId = customerChoiceBox
                        .getValue().getCustomerId();


                boolean isSuccessful = false;

                try {

                    isSuccessful = waiter.takeDineInOrders(
                            customerId,
                            orderList
                    );

                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

                if (isSuccessful) {
                    promptOrderSuccessful();
                }


                orderList.clear();
                refreshOrderList();
                customerChoiceBox.setValue(null);
                tableChoiceBox.setValue(null);

            } else {
                AlertPopUpWindow.displayErrorWindow(
                        MIN_ONE_REQUIRED
                );
            }

        });

        cancelOrderButton.setOnAction(e -> {
            deleteAllOrder();
        });

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
                new PropertyValueFactory<>(TYPE));

        quantityColumn.setText(QUANTITY_COLUMN);
        quantityColumn.setMinWidth(COLUMN_WIDTH_75);
        quantityColumn.setStyle(CENTERED);
        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>(QUANTITY));

        actionButtonColumn.setText(ACTION_COLUMN);

        actionButtonColumn1.setMinWidth(COLUMN_WIDTH_35);
        actionButtonColumn1.setStyle(CENTERED);
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button editButton = new Button();
            ImageLoader.setUpGraphicButton(editButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, EDIT_BUTTON);
            String itemName = cellData.getValue()
                    .getItemNameForDisplay();
            String itemQuantity = String.valueOf(
                    cellData.getValue().getQuantity());

            editButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    EDIT_ORDER_WINDOW));

                    VBox vbox = fxmlLoader.load();

                    WaiterDineinEditOrderController controller =
                            fxmlLoader.getController();

                    controller.setItemDetails(
                            itemName,
                            itemQuantity,
                            orderList
                    );

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle(EDIT_ORDER_TITLE);

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshOrderList();

                } catch (IOException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

            });

            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(COLUMN_WIDTH_35);
        actionButtonColumn2.setStyle(CENTERED);
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            ImageLoader.setUpGraphicButton(deleteButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, DELETE_BUTTON);
            String selectedItemName = cellData.getValue().getItemName();
            FoodDrink selectedItem = cellData.getValue();

            deleteButton.setOnAction(e -> {

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        DELETE_ITEM_TITLE,
                        String.format(
                                DELETE_ITEM_MESSAGE,
                                selectedItemName
                        )
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase(OK_DONE)) {
                    orderList.remove(selectedItem);
                    refreshOrderList();
                }
            });

            return new SimpleObjectProperty<>(deleteButton);
        });

        refreshOrderList();
    }

    private void refreshOrderList() {
        orderDetailsTable.getItems().clear();
        data.clear();
        data.addAll(orderList);
        orderDetailsTable.setItems(data);
    }

    /**
     * This refreshes the table of items in the current order.
     */
    public void refreshCustomerList() {

        try {

            UserManagement userManagement = new UserManagement();
            if (customerList != null && !customerList.isEmpty()) {
                customerList.clear();
                userManagement.updateCustomerList(customerList);
                updateCustomerChoiceBox();
            } else {
                userManagement.updateCustomerList(customerList);
                updateCustomerChoiceBox();
            }

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    private void updateCustomerChoiceBox() {
        if (customerList != null && !customerList.isEmpty()) {
            customerChoiceBox.getItems().clear();
            for (Customer customer : customerList) {
                customerChoiceBox.getItems().add(customer);
            }
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

    private void promptOrderSuccessful() {
        AlertPopUpWindow.displayInformationWindow(
                ORDER_SUCCESS_TITLE,
                ORDER_SUCCESS_MESSAGE,
                OK
        );
    }

    private void deleteAllOrder() {

        Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                CANCEL_ORDER_TITLE,
                CANCEL_ORDER_MESSAGE
        );

        if (userChoice.get()
                .getButtonData().toString()
                .equalsIgnoreCase(OK_DONE)) {
            orderList.clear();
            refreshOrderList();
        }
    }

}
