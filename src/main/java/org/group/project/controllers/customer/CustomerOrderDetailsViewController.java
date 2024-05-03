package org.group.project.controllers.customer;

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
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Kitchen;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;
import org.group.project.scenes.customer.stackViews.MenuController;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * This class enables the customer to make any edits to the item quantity.
 *
 * @author azmi_maz
 */
public class CustomerOrderDetailsViewController {
    private static final String BG_IMAGE = "images" +
            "/background/main-bg" +
            ".jpg";
    private static final String NO_COLUMN = "No.";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final int COLUMN_WIDTH_35 = 35;
    private static final int COLUMN_WIDTH_75 = 75;
    private static final int COLUMN_WIDTH_40 = 40;
    private static final int COLUMN_WIDTH_150 = 150;
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final String ITEM_COLUMN = "Item";
    private static final String ITEM_TYPE = "itemType";
    private static final String TYPE_COLUMN = "Type";
    private static final String QUANTITY_COLUMN = "Quantity";
    private static final String QUANTITY = "quantity";
    private static final String ACTION_COLUMN = "Action";
    private static final String EDIT_ORDER_TOOLTIP = "Edit Order";
    private static final String EDIT_BUTTON = "edit";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String EDIT_ORDER_WINDOW = "smallwindows/" +
            "edit-order-item.fxml";
    private static final String IMAGE_FILE = "images/menu/%s";
    private static final String EDIT_ORDER_TITLE = "Edit Order Item";
    private static final String DELETE_ORDER_TOOLTIP = "Delete Order";
    private static final String DELETE_BUTTON = "delete";
    private static final String OK_DONE = "OK_DONE";
    private static final String CANCEL_ORDER_TITLE = "Cancel Order Request";
    private static final String CANCEL_ORDER_MESSAGE = "Do you want to " +
            "cancel this order?";
    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private BorderPane borderPane;

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
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    private TableView<FoodDrink> orderDetailsTable =
            new TableView<>();

    private ObservableList<FoodDrink> data =
            FXCollections.observableArrayList();

    private List<FoodDrink> orderList;

    private List<Order> newOrder;


    /**
     * This loads up the customer current order made.
     *
     * @param orderList - the list of items put in cart.
     * @param newOrder  - the current order being viewed.
     */
    public CustomerOrderDetailsViewController(List<FoodDrink> orderList,
                                              List<Order> newOrder) {
        this.orderList = orderList;
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

        try {

            Kitchen kitchen = new Kitchen();
            kitchen.updateOrderTypeChoiceBox(
                    choiceBox
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

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

        actionButtonColumn.setText(ACTION_COLUMN);

        actionButtonColumn1.setMinWidth(COLUMN_WIDTH_35);
        actionButtonColumn1.setStyle(CENTERED);
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button editButton = new Button();
            editButton.setTooltip(new Tooltip(EDIT_ORDER_TOOLTIP));
            ImageLoader.setUpGraphicButton(editButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, EDIT_BUTTON);
            String imageFile = cellData.getValue().getImageFileName();
            String labelName = cellData.getValue().getItemNameForDisplay();

            editButton.setOnMousePressed(e -> {
                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    EDIT_ORDER_WINDOW));

                    BorderPane borderPane = fxmlLoader.load();

                    CustomerMenuOrderEditItemController controller =
                            fxmlLoader.getController();

                    controller.setItemToEdit(String.format(
                            IMAGE_FILE,
                            imageFile
                    ), labelName, orderList);
                    Scene editScene = new Scene(borderPane,
                            WindowSize.MEDIUM.WIDTH,
                            WindowSize.MEDIUM.HEIGHT);

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

        actionButtonColumn2.setText("");
        actionButtonColumn2.setMinWidth(35);
        actionButtonColumn2.setStyle(CENTERED);
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            deleteButton.setTooltip(new Tooltip(DELETE_ORDER_TOOLTIP));
            ImageLoader.setUpGraphicButton(deleteButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, DELETE_BUTTON);
            FoodDrink item = cellData.getValue();

            deleteButton.setOnMousePressed(e -> {
                orderList.remove(item);
                refreshOrderList();
            });

            return new SimpleObjectProperty<>(deleteButton);
        });

        confirmButton.setOnMousePressed(e -> {
            newOrder.clear();

            newOrder.add(createNewOrder());
            OrderDetailsController.presenter.goToOrderConfirmation();
        });

        // Cancel order and returns customer to menu with orderlist erased.
        cancelButton.setOnMousePressed(e -> {

            Optional<ButtonType> userChoice = promptForUserAcknowledgement();

            if (userChoice.get()
                    .getButtonData().toString()
                    .equalsIgnoreCase(OK_DONE)) {
                cancelConfirmationAndGoBackToMenu();
            }
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
     * This method creates an order once customer confirms the current order.
     *
     * @return the confirmed order to proceed to the next confirmation page.
     */
    public Order createNewOrder() {

        try {

            Kitchen kitchen = new Kitchen();
            String orderType = choiceBox.getValue();
            int customerId = MainScenesMap.getCurrentUser().getUserId();

            return kitchen.createNewOrder(
                    orderType,
                    customerId,
                    orderList
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        return null;
    }

    private Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                CANCEL_ORDER_TITLE,
                CANCEL_ORDER_MESSAGE
        );
    }

    private void cancelConfirmationAndGoBackToMenu() {
        OrderDetailsController.presenter.returnToMenu();
        MenuController.orderList.clear();
    }
}
