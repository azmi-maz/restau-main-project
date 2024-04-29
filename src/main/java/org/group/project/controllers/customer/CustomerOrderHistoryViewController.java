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
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;

public class CustomerOrderHistoryViewController {

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

    public void initialize() throws URISyntaxException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/main-bg" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false,
                false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        refreshOrderHistoryList();

        orderNoColumn.setText("Order No.");
        orderNoColumn.setMinWidth(65);
        orderNoColumn.setStyle("-fx-alignment: CENTER;");
        orderNoColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderId"));

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
            String formattedTime = cellData.getValue().getOrderTimeInFormat();
            return new SimpleObjectProperty<>(formattedTime);
        });

        orderTypeColumn.setText("Type");
        orderTypeColumn.setMinWidth(150);
        orderTypeColumn.setStyle("-fx-alignment: CENTER;");
        orderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderType"));

        orderStatusColumn.setText("Status");
        orderStatusColumn.setMinWidth(150);
        orderStatusColumn.setStyle("-fx-alignment: CENTER;");
        orderStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderStatus"));

        actionButtonColumn.setText("Action");
        actionButtonColumn.setMinWidth(60);
        actionButtonColumn.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            // TODO use tool tips for other buttons, where necessary
            viewButton.setTooltip(new Tooltip("View details"));
            ImageLoader.setUpGraphicButton(viewButton,
                    15, 15, "view-details");
            Order getOrder = cellData.getValue();

            viewButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/" +
                                            "customer-order-details" +
                                            ".fxml"));

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
                    // TODO Should final variable this
                    editStage.setTitle("View Order Details");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshOrderHistoryList();

                } catch (IOException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            "Error",
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }
            });
            return new SimpleObjectProperty<>(viewButton);
        });

        orderHistoryTable.setItems(data);

    }

    // TODO comment
    public void refreshOrderHistoryList() {

        updateUserId();

        // TODO comment
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
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }

    private void updateUserId() {

        if (Main.getCurrentUser() == null) {
            return;
        }

        try {
            UserManagement userManagement = new UserManagement();
            userId = userManagement.getUserIdByUsername(
                    Main.getCurrentUser().getUsername());
        } catch (TextFileNotFoundException ex) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    ex.getMessage()
            );
            ex.printStackTrace();
        }
    }
}
