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
import org.group.project.classes.Customer;
import org.group.project.classes.Notification;
import org.group.project.classes.NotificationBoard;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class enables the customer to view their list of notifications.
 */
public class CustomerNotificationViewController {

    @FXML
    private TableColumn<Notification, String> dateColumn;

    @FXML
    private TableColumn<Notification, String> timeColumn;

    @FXML
    private TableColumn<Notification, String> typeColumn;

    @FXML
    private TableColumn<Notification, String> bodyColumn;

    @FXML
    private TableColumn<Notification, Button> actionButtonColumn;

    @FXML
    private BorderPane borderPane;

    private int userId;

    @FXML
    private TableView<Notification> notificationTable = new TableView<>();
    private ObservableList<Notification> data =
            FXCollections.observableArrayList();

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class.getResource("images" +
                    "/background/main-bg" +
                    ".jpg").toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
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

        refreshNotificationList();

        dateColumn.setText("Date");
        dateColumn.setMinWidth(150);
        dateColumn.setStyle("-fx-alignment: CENTER;");
        dateColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getNotificationDateInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        timeColumn.setText("Time");
        timeColumn.setMinWidth(150);
        timeColumn.setStyle("-fx-alignment: CENTER;");
        timeColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getNotificationTimeInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        typeColumn.setText("Type");
        typeColumn.setMinWidth(150);
        typeColumn.setStyle("-fx-alignment: CENTER;");
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("notificationType"));

        bodyColumn.setText("Message Body");
        bodyColumn.setMinWidth(372);
        bodyColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        bodyColumn.setCellValueFactory(
                new PropertyValueFactory<>("messageBody"));

        actionButtonColumn.setText("Action");
        actionButtonColumn.setMinWidth(60);
        actionButtonColumn.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            viewButton.setTooltip(new Tooltip("View details"));
            boolean readStatus = cellData.getValue().getReadStatus();
            if (readStatus) {
                ImageLoader.setUpGraphicButton(viewButton,
                        15, 15, "read");
            } else {
                ImageLoader.setUpGraphicButton(viewButton,
                        15, 11, "unread");
            }
            int notificationId = cellData.getValue().getNotificationId();
            LocalDate notificationDate = cellData.getValue()
                    .getNotificationDate();
            LocalTime notificationTime = cellData.getValue()
                    .getNotificationTime();
            String notificationType = cellData.getValue()
                    .getNotificationType();
            String messageBody = cellData.getValue().getMessageBody();

            viewButton.setOnAction(e -> {
                Customer customer = (Customer) Main.getCurrentUser();

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/" +
                                            "customer-notification-details" +
                                            ".fxml"));

                    VBox vbox = fxmlLoader.load();

                    CustomerNotificationDetailsController controller =
                            fxmlLoader.getController();

                    controller.populateNotificationDetails(
                            notificationDate,
                            notificationTime,
                            notificationType,
                            messageBody
                    );

                    Scene editScene = new Scene(vbox,
                            WindowSize.SMALL.WIDTH,
                            WindowSize.SMALL.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle("View Notification Details");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    if (!readStatus) {
                        customer.updateNotificationReadStatus(
                                notificationId
                        );
                    }

                    refreshNotificationList();

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

        notificationTable.setItems(data);

    }

    /**
     * This refreshes the notification table after any changes.
     */
    public void refreshNotificationList() {

        updateUserId();

        notificationTable.getItems().clear();
        data.clear();

        try {

            NotificationBoard notificationBoard = new NotificationBoard();
            notificationBoard.getNotificationDataByUserId(
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
