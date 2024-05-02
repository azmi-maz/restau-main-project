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
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * This class enables the driver to view their pending delivery orders.
 *
 * @author azmi_maz
 */
public class DriverPendingDeliveryViewController {

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
            bgImage = new Image(Main.class.getResource("images" +
                    "/background/driver-main" +
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

        refreshPendingDeliveryList();

        noColumn.setText("Order no.");
        noColumn.setMinWidth(45);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderId"));

        customerColumn.setText("Customer");
        customerColumn.setMinWidth(130);
        customerColumn.setStyle("-fx-alignment: CENTER;");
        customerColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue().getCustomer();
            return new SimpleObjectProperty<>(customer);
        });

        deliveryTimeColumn.setText("Est. Delivery Time");
        deliveryTimeColumn.setMinWidth(115);
        deliveryTimeColumn.setStyle("-fx-alignment: CENTER;");
        deliveryTimeColumn.setCellValueFactory(cellData -> {
            String formattedTime = cellData.getValue().getOrderTimeInFormat();
            return new SimpleObjectProperty<>(formattedTime);
        });

        addressColumn.setText("Address");
        addressColumn.setMinWidth(274);
        addressColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        addressColumn.setCellValueFactory(cellData -> {
            String customerAddress = cellData.getValue()
                    .getCustomer().getDeliveryAddress();
            return new SimpleObjectProperty<>(customerAddress);
        });

        actionButtonColumn.setText("Action");
        actionButtonColumn.setMinWidth(65);
        actionButtonColumn.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            viewButton.setTooltip(new Tooltip("View details"));
            ImageLoader.setUpGraphicButton(viewButton,
                    15, 15, "view-details");
            Order currentOrder = cellData.getValue();

            viewButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/" +
                                            "driver-confirmdelivery" +
                                            ".fxml"));

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

                    editStage.setTitle("View Order Details");

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

        if (Main.getCurrentUser() == null) {
            return;
        }

        try {
            User user = Main.getCurrentUser();
            userId = user.getUserId();
        } catch (TextFileNotFoundException ex) {
            AlertPopUpWindow.displayErrorWindow(
                    ex.getMessage()
            );
            ex.printStackTrace();
        }
    }
}
