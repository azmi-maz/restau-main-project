package org.group.project.controllers.driver;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
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

/**
 * This class enables the driver to view their pending delivery orders.
 *
 * @author azmi_maz
 */
public class DriverPendingDeliveryViewController {
    private static final String BG_IMAGE = "images" +
            "/background/driver-main" +
            ".jpg";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final String NO_COLUMN = "Order no.";
    private static final String ORDER_ID = "orderId";
    private static final String CUSTOMER_COLUMN = "Customer";
    private static final String TIME_COLUMN = "Est. Delivery Time";
    private static final String ADDRESS_COLUMN = "Address";
    private static final String ACTION_COLUMN = "Action";
    private static final int COLUMN_WIDTH_45 = 45;
    private static final int COLUMN_WIDTH_65 = 65;
    private static final int COLUMN_WIDTH_274 = 274;
    private static final int COLUMN_WIDTH_115 = 115;
    private static final int COLUMN_WIDTH_130 = 130;
    private static final String VIEW_DETAILS_TOOLTIP = "View details";
    private static final String VIEW_DETAILS_BUTTON = "view-details";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String CONFIRM_DELIVERY_WINDOW = "smallwindows/" +
            "driver-confirmdelivery" +
            ".fxml";
    private static final String CONFIRM_DELIVERY_TITLE = "View Order Details";
    @FXML
    private TableColumn<DeliveryOrder, String> noColumn;

    @FXML
    private TableColumn<DeliveryOrder, Customer> customerColumn;

    @FXML
    private TableColumn<DeliveryOrder, String> deliveryTimeColumn;

    @FXML
    private TableColumn<DeliveryOrder, String> addressColumn;

    @FXML
    private TableColumn<DeliveryOrder, Button> actionButtonColumn;

    @FXML
    private BorderPane borderPane;

    private int userId;

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

        noColumn.setText(NO_COLUMN);
        noColumn.setMinWidth(COLUMN_WIDTH_45);
        noColumn.setStyle(CENTERED);
        noColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_ID));

        customerColumn.setText(CUSTOMER_COLUMN);
        customerColumn.setMinWidth(COLUMN_WIDTH_130);
        customerColumn.setStyle(CENTERED);
        customerColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue().getCustomer();
            return new SimpleObjectProperty<>(customer);
        });

        deliveryTimeColumn.setText(TIME_COLUMN);
        deliveryTimeColumn.setMinWidth(COLUMN_WIDTH_115);
        deliveryTimeColumn.setStyle(CENTERED);
        deliveryTimeColumn.setCellValueFactory(cellData -> {
            String formattedTime = cellData.getValue().getOrderTimeInFormat();
            return new SimpleObjectProperty<>(formattedTime);
        });

        addressColumn.setText(ADDRESS_COLUMN);
        addressColumn.setMinWidth(COLUMN_WIDTH_274);
        addressColumn.setStyle(CENTER_LEFT);
        addressColumn.setCellValueFactory(cellData -> {
            String customerAddress = cellData.getValue()
                    .getCustomer().getDeliveryAddress();
            return new SimpleObjectProperty<>(customerAddress);
        });

        actionButtonColumn.setText(ACTION_COLUMN);
        actionButtonColumn.setMinWidth(COLUMN_WIDTH_65);
        actionButtonColumn.setStyle(CENTERED);
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            viewButton.setTooltip(new Tooltip(VIEW_DETAILS_TOOLTIP));
            ImageLoader.setUpGraphicButton(viewButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, VIEW_DETAILS_BUTTON);
            Order currentOrder = cellData.getValue();

            viewButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    CONFIRM_DELIVERY_WINDOW));

                    VBox vbox = fxmlLoader.load();

                    DriverPendingDeliveryDetailsController controller =
                            fxmlLoader.getController();

                    controller.setOrderDetails(
                            currentOrder
                    );

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle(CONFIRM_DELIVERY_TITLE);

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

        pendingDeliveryTable.setItems(data);

    }

    /**
     * This method refreshes the table of pending delivery orders.
     */
    public void refreshPendingDeliveryList() {

        updateUserId();

        pendingDeliveryTable.getItems().clear();
        data.clear();

        try {

            Kitchen kitchen = new Kitchen();
            kitchen.getPendingDeliveryDataByDriverId(
                    data,
                    userId
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }

    private void updateUserId() {

        if (MainScenesMap.getCurrentUser() == null) {
            return;
        }

        try {
            User user = MainScenesMap.getCurrentUser();
            userId = user.getUserId();
        } catch (TextFileNotFoundException ex) {
            AlertPopUpWindow.displayErrorWindow(
                    ex.getMessage()
            );
            ex.printStackTrace();
        }
    }
}
