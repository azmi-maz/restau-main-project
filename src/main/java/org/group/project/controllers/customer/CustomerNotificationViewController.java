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
import org.group.project.classes.*;
import org.group.project.scenes.WindowSize;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    private String userId;

    private List<String> notificationList;

    @FXML
    private TableView<Notification> notificationTable = new TableView<>();
    private ObservableList<Notification> data =
            FXCollections.observableArrayList();

    public void initialize() throws URISyntaxException, FileNotFoundException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/main-bg" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        // TODO get userId from the main.
        userId = "1";

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
            // TODO use tool tips for other buttons, where necessary
            viewButton.setTooltip(new Tooltip("View details"));
            ImageLoader.setUpGraphicButton(viewButton, 15, 15, "view-details");
            LocalDate notificationDate = cellData.getValue().getNotificationDate();
            LocalTime notificationTime = cellData.getValue().getNotificationTime();
            String notificationType = cellData.getValue().getNotificationType();
            String messageBody = cellData.getValue().getMessageBody();

            viewButton.setOnAction(e -> {
                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/customer-notification-details" +
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
                    // TODO Should final variable this
                    editStage.setTitle("View Notification Details");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshNotificationList();

                } catch (IOException ex) {
                    // TODO catch error
                    throw new RuntimeException(ex);
                }

            });


            return new SimpleObjectProperty<>(viewButton);
        });

        notificationTable.setItems(data);

    }

    // TODO comment
    public void refreshNotificationList() throws FileNotFoundException {

        updateUserId();

        // TODO comment
        notificationTable.getItems().clear();
        data.clear();

        notificationList = DataManager.allDataFromFile("NOTIFICATION");

        for (String notification : notificationList) {
            List<String> notificationDetails = List.of(notification.split(","));

            // userId
            int currentUserId = Integer.parseInt(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "userId")));

            // TODO filter user id here
            if (String.valueOf(currentUserId).equalsIgnoreCase(userId)) {

                // notification id
                int notifcationid = Integer.parseInt(notificationDetails.get(DataFileStructure.getIndexColOfUniqueId("NOTIFICATION")));


                // notificationDate
                List<String> notificationDateDetails = List.of(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "notificationDate")).split("-"));
                LocalDate notificationDate =
                        LocalDate.of(Integer.parseInt(notificationDateDetails.get(0)),
                                Integer.parseInt(notificationDateDetails.get(1)),
                                Integer.parseInt(notificationDateDetails.get(2)));

                // notificationTime
                List<String> notificationTimeDetails = List.of(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "notificationTime")).split("-"));

                LocalTime notificationTime =
                        LocalTime.of(Integer.parseInt(notificationTimeDetails.get(0)),
                                Integer.parseInt(notificationTimeDetails.get(1)));

                // notificationType
                String notificationType = notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "notificationType"));

                // readStatus
                boolean readStatus = Boolean.parseBoolean(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "readStatus")));

                // messageBody
                String messageBody = notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "messageBody"));

                if (notificationType.equalsIgnoreCase("booking")) {
                    // TODO this is needed to replace the ; to ,
                    messageBody = HelperMethods.formatAddressToRead(messageBody);
                }

                data.add(new Notification(
                        notifcationid,
                        currentUserId,
                        notificationDate,
                        notificationTime,
                        notificationType,
                        messageBody,
                        readStatus
                ));
            }
        }
    }

    private void updateUserId() {

        if (Main.getCurrentUser() == null) {
            return;
        }

        // TODO try catch
        try {
            userId = String.valueOf(
                    HelperMethods
                            .findUserIdByUsername(
                                    Main.getCurrentUser().getUsername()));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
