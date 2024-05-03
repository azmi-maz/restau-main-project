package org.group.project.controllers.customer;

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
import org.group.project.classes.Kitchen;
import org.group.project.classes.Order;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * This class enables the customer to view table of previous orders made.
 *
 * @author azmi_maz
 */
public class CustomerOrderHistoryViewController {
    private static final String BG_IMAGE = "images" +
            "/background/main-bg" +
            ".jpg";
    private static final String NO_COLUMN = "Order No.";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final int COLUMN_WIDTH_65 = 65;
    private static final int COLUMN_WIDTH_60 = 60;
    private static final int COLUMN_WIDTH_150 = 150;
    private static final String DATE_COLUMN = "Date";
    private static final String ORDER_ID = "orderId";
    private static final String TIME_COLUMN = "Time";
    private static final String TYPE_COLUMN = "Type";
    private static final String STATUS_COLUMN = "Status";
    private static final String ORDER_TYPE = "orderType";
    private static final String ORDER_STATUS = "orderStatus";
    private static final String ACTION_COLUMN = "Action";
    private static final String VIEW_DETAILS_TOOLTIP = "View details";
    private static final String VIEW_DETAILS_BUTTON = "view-details";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String ORDER_DETAILS_WINDOW = "smallwindows/" +
            "customer-order-details.fxml";
    private static final String ORDER_DETAILS_TITLE = "View Order Details";
    @FXML
    private TableColumn<Order, String> orderNoColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, String> orderTimeColumn;

    @FXML
    private TableColumn<Order, String> orderTypeColumn;

    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private TableColumn<Order, Button> actionButtonColumn;

    @FXML
    private BorderPane borderPane;

    private int userId;

    @FXML
    private TableView<Order> orderHistoryTable = new TableView<>();
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

        refreshOrderHistoryList();

        orderNoColumn.setText(NO_COLUMN);
        orderNoColumn.setMinWidth(COLUMN_WIDTH_65);
        orderNoColumn.setStyle(CENTERED);
        orderNoColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_ID));

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

        orderTypeColumn.setText(TYPE_COLUMN);
        orderTypeColumn.setMinWidth(COLUMN_WIDTH_150);
        orderTypeColumn.setStyle(CENTERED);
        orderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_TYPE));

        orderStatusColumn.setText(STATUS_COLUMN);
        orderStatusColumn.setMinWidth(COLUMN_WIDTH_150);
        orderStatusColumn.setStyle(CENTERED);
        orderStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>(ORDER_STATUS));

        actionButtonColumn.setText(ACTION_COLUMN);
        actionButtonColumn.setMinWidth(COLUMN_WIDTH_60);
        actionButtonColumn.setStyle(CENTERED);
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            viewButton.setTooltip(new Tooltip(VIEW_DETAILS_TOOLTIP));
            ImageLoader.setUpGraphicButton(viewButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, VIEW_DETAILS_BUTTON);
            Order getOrder = cellData.getValue();

            viewButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    ORDER_DETAILS_WINDOW));

                    VBox vbox = fxmlLoader.load();

                    CustomerOrderHistoryDetailsController controller =
                            fxmlLoader.getController();

                    controller.populateOrderDetails(
                            getOrder
                    );

                    Scene editScene = new Scene(vbox,
                            WindowSize.MEDIUM.WIDTH,
                            WindowSize.MEDIUM.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle(ORDER_DETAILS_TITLE);

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshOrderHistoryList();

                } catch (IOException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }
            });
            return new SimpleObjectProperty<>(viewButton);
        });

        orderHistoryTable.setItems(data);

    }

    /**
     * This method refreshes the table to get the updated data.
     */
    public void refreshOrderHistoryList() {

        updateUserId();

        orderHistoryTable.getItems().clear();
        data.clear();

        try {
            Kitchen kitchen = new Kitchen();
            kitchen.getOrderDataByUserId(
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
            UserManagement userManagement = new UserManagement();
            userId = userManagement.getUserIdByUsername(
                    MainScenesMap.getCurrentUser().getUsername());
        } catch (TextFileNotFoundException ex) {
            AlertPopUpWindow.displayErrorWindow(
                    ex.getMessage()
            );
            ex.printStackTrace();
        }
    }
}
