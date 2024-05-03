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
import org.group.project.classes.DeliveryOrder;
import org.group.project.classes.Kitchen;
import org.group.project.classes.Waiter;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * This class enables the waiter to view pending delivery orders for
 * approval and driver assignment.
 *
 * @author azmi_maz
 */
public class WaiterApproveDeliveryViewController {
    private static final String BG_IMAGE = "images" +
            "/background/waiter-main" +
            ".jpg";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final int COLUMN_WIDTH_65 = 65;
    private static final int COLUMN_WIDTH_35 = 35;
    private static final int COLUMN_WIDTH_150 = 150;
    private static final int COLUMN_WIDTH_200 = 200;
    private static final String NO_COLUMN = "Order No.";
    private static final String ORDER_ID = "orderId";
    private static final String DATE_COLUMN = "Date";
    private static final String TIME_COLUMN = "Time";
    private static final String ITEMS_COLUMN = "Item Ordered";
    private static final String STATUS_COLUMN = "Status";
    private static final String ORDER_STATUS = "orderStatus";
    private static final String CUSTOMER_COLUMN = "Customer";
    private static final String CUSTOMER = "customer";
    private static final String ACTION_COLUMN = "Action";
    private static final String VIEW_DETAILS_TOOLTIP = "View details";
    private static final String VIEW_DETAILS_BUTTON = "view-details";
    private static final String CANCEL_TOOLTIP = "Cancel";
    private static final String CANCEL_BUTTON = "cancel";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String OK_DONE = "OK_DONE";
    private static final String CANCEL_UPDATE_TITLE = "Delivery Order Update";
    private static final String CANCEL_UPDATE_MESSAGE = "Delivery order " +
            "no.%d was cancelled successfully.";
    private static final String OK = "Ok";
    private static final String EDIT_DELIVERY_WINDOW = "smallwindows/" +
            "waiter-editdeliveryorder" +
            ".fxml";
    private static final String EDIT_DELIVERY_TITLE = "Edit Delivery Order";
    private static final String CANCEL_DELIVERY_TITLE = "Delivery Order " +
            "Cancellation";
    private static final String CANCEL_DELIVERY_MESSAGE = "Do you want " +
            "to cancel this delivery order?";
    @FXML
    private TableColumn<DeliveryOrder, String> orderNoColumn;

    @FXML
    private TableColumn<DeliveryOrder, String> customerColumn;

    @FXML
    private TableColumn<DeliveryOrder, String> orderDateColumn;

    @FXML
    private TableColumn<DeliveryOrder, String> orderTimeColumn;

    @FXML
    private TableColumn<DeliveryOrder, String> orderListColumn;

    @FXML
    private TableColumn<DeliveryOrder, String> orderStatusColumn;

    @FXML
    private TableColumn<DeliveryOrder, Button> actionButtonColumn;

    @FXML
    private TableColumn<DeliveryOrder, Button> actionButtonColumn1;

    @FXML
    private TableColumn<DeliveryOrder, Button> actionButtonColumn2;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<DeliveryOrder> pendingDeliveryTable = new TableView<>();
    private ObservableList<DeliveryOrder> data =
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

        refreshPendingDeliveryList();

        orderNoColumn.setText(NO_COLUMN);
        orderNoColumn.setMinWidth(COLUMN_WIDTH_65);
        orderNoColumn.setStyle(CENTERED);
        orderNoColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_ID));

        customerColumn.setText(CUSTOMER_COLUMN);
        customerColumn.setMinWidth(COLUMN_WIDTH_150);
        customerColumn.setStyle(CENTERED);
        customerColumn.setCellValueFactory(
                new PropertyValueFactory<>(CUSTOMER));

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
            String formattedTime = cellData.getValue().getOrderTimeInFormat();
            return new SimpleObjectProperty<>(formattedTime);
        });

        orderListColumn.setText(ITEMS_COLUMN);
        orderListColumn.setMinWidth(COLUMN_WIDTH_200);
        orderListColumn.setStyle(CENTER_LEFT);
        orderListColumn.setCellValueFactory(cellData -> {
            String displayList = cellData.getValue().getListOfItemsForDisplay();
            return new SimpleObjectProperty<>(displayList);
        });

        orderStatusColumn.setText(STATUS_COLUMN);
        orderStatusColumn.setMinWidth(COLUMN_WIDTH_150);
        orderStatusColumn.setStyle(CENTERED);
        orderStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_STATUS));

        actionButtonColumn.setText(ACTION_COLUMN);

        actionButtonColumn1.setMinWidth(COLUMN_WIDTH_35);
        actionButtonColumn1.setStyle(CENTERED);
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            viewButton.setTooltip(new Tooltip(VIEW_DETAILS_TOOLTIP));
            ImageLoader.setUpGraphicButton(viewButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, VIEW_DETAILS_BUTTON);
            DeliveryOrder selectedOrder = cellData.getValue();

            viewButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    EDIT_DELIVERY_WINDOW));

                    VBox vbox = fxmlLoader.load();

                    WaiterEditDeliveryOrderController controller =
                            fxmlLoader.getController();

                    controller.populateOrderDetails(
                            selectedOrder
                    );
                    Scene editScene = new Scene(vbox,
                            WindowSize.MEDIUM.WIDTH,
                            WindowSize.MEDIUM.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle(EDIT_DELIVERY_TITLE);

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshPendingDeliveryList();

                } catch (IOException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

            });

            return new SimpleObjectProperty<>(viewButton);
        });

        actionButtonColumn2.setMinWidth(COLUMN_WIDTH_35);
        actionButtonColumn2.setStyle(CENTERED);
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button cancelButton = new Button();
            cancelButton.setTooltip(new Tooltip(CANCEL_TOOLTIP));
            ImageLoader.setUpGraphicButton(cancelButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, CANCEL_BUTTON);
            DeliveryOrder selectedOrder = cellData.getValue();

            cancelButton.setOnAction(e -> {
                Waiter waiter = (Waiter) MainScenesMap.getCurrentUser();

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        CANCEL_DELIVERY_TITLE,
                        CANCEL_DELIVERY_MESSAGE
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase(OK_DONE)) {

                    try {
                        boolean isSuccessful = waiter.cancelDeliveryOrder(
                                selectedOrder
                        );
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    CANCEL_UPDATE_TITLE,
                                    String.format(
                                            CANCEL_UPDATE_MESSAGE,
                                            selectedOrder.getOrderId()
                                    ), OK
                            );
                        }

                        refreshPendingDeliveryList();

                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }
                }
            });

            return new SimpleObjectProperty<>(cancelButton);
        });

        pendingDeliveryTable.setItems(data);

    }

    /**
     * This method refreshes the table of pending delivery orders.
     */
    public void refreshPendingDeliveryList() {

        pendingDeliveryTable.getItems().clear();
        data.clear();

        try {

            Kitchen kitchen = new Kitchen();

            kitchen.getPendingApprovalOrderData(data);

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
