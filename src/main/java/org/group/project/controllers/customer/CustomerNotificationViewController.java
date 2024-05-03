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
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class enables the customer to view their list of notifications.
 *
 * @author azmi_maz
 */
public class CustomerNotificationViewController {
    private static final String BG_IMAGE = "images" +
            "/background/main-bg" +
            ".jpg";
    private static final int COLUMN_WIDTH_60 = 60;
    private static final int COLUMN_WIDTH_150 = 150;
    private static final int COLUMN_WIDTH_372 = 372;
    private static final String DATE_COLUMN = "Date";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String TIME_COLUMN = "Time";
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final String TYPE_COLUMN = "Type";
    private static final String NOTIFICATION_TYPE = "notificationType";
    private static final String MESSAGE_BODY = "messageBody";
    private static final String ACTION_COLUMN = "Action";
    private static final String MESSAGE_BODY_COLUMN = "Message Body";
    private static final String VIEW_DETAILS_TOOLTIP = "View details";
    private static final String READ_BUTTON = "read";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String UNREAD_BUTTON = "unread";
    private static final String NOTIFICATION_DETAILS_WINDOW = "smallwindows/" +
            "customer-notification-details" +
            ".fxml";
    private static final String NOTIFICATION_DETAIL_TITLE = "View " +
            "Notification Details";
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

        refreshNotificationList();

        dateColumn.setText(DATE_COLUMN);
        dateColumn.setMinWidth(COLUMN_WIDTH_150);
        dateColumn.setStyle(CENTERED);
        dateColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getNotificationDateInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        timeColumn.setText(TIME_COLUMN);
        timeColumn.setMinWidth(COLUMN_WIDTH_150);
        timeColumn.setStyle(CENTERED);
        timeColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getNotificationTimeInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        typeColumn.setText(TYPE_COLUMN);
        typeColumn.setMinWidth(COLUMN_WIDTH_150);
        typeColumn.setStyle(CENTERED);
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>(NOTIFICATION_TYPE));

        bodyColumn.setText(MESSAGE_BODY_COLUMN);
        bodyColumn.setMinWidth(COLUMN_WIDTH_372);
        bodyColumn.setStyle(CENTER_LEFT);
        bodyColumn.setCellValueFactory(
                new PropertyValueFactory<>(MESSAGE_BODY));

        actionButtonColumn.setText(ACTION_COLUMN);
        actionButtonColumn.setMinWidth(COLUMN_WIDTH_60);
        actionButtonColumn.setStyle(CENTERED);
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            viewButton.setTooltip(new Tooltip(VIEW_DETAILS_TOOLTIP));
            boolean readStatus = cellData.getValue().getReadStatus();
            if (readStatus) {
                ImageLoader.setUpGraphicButton(viewButton,
                        BUTTON_WIDTH, BUTTON_HEIGHT, READ_BUTTON);
            } else {
                ImageLoader.setUpGraphicButton(viewButton,
                        BUTTON_WIDTH, BUTTON_HEIGHT, UNREAD_BUTTON);
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
                Customer customer = (Customer) MainScenesMap.getCurrentUser();

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    NOTIFICATION_DETAILS_WINDOW));

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

                    editStage.setTitle(NOTIFICATION_DETAIL_TITLE);

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
