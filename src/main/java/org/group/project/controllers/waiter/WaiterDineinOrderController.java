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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.AlertPopUpWindow;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WaiterDineinOrderController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView bgImage;

    @FXML
    private ChoiceBox tableChoiceBox;

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

    private List<FoodDrink> orderList = new ArrayList<>();

    @FXML
    private TableView<FoodDrink> orderDetailsTable =
            new TableView<>();

    private ObservableList<FoodDrink> data =
            FXCollections.observableArrayList();

    public void initialize() throws URISyntaxException {

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

        // TODO enum or get from database
        tableChoiceBox.getItems().add("Table A");
        tableChoiceBox.getItems().add("Table B");
        tableChoiceBox.getItems().add("Table C");
        tableChoiceBox.getItems().add("Table D");
        tableChoiceBox.getItems().add("Table E");
        tableChoiceBox.getItems().add("Table F");
        tableChoiceBox.getItems().add("Table G");
        tableChoiceBox.getItems().add("Table H");
        tableChoiceBox.getItems().add("Table I");
        tableChoiceBox.getItems().add("Table J");
        tableChoiceBox.getItems().add("Table K");

        tableChoiceBox.setValue("Choose Table");

        ImageLoader.setUpGraphicButton(newItemButton, 15, 15, "circle-plus");

        newItemButton.setOnAction(e -> {

            try {
                FXMLLoader fxmlLoader =
                        new FXMLLoader(Main.class.getResource(
                                "smallwindows/add-dineinorder-item" +
                                        ".fxml"));

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
                // TODO Should final variable this
                editStage.setTitle("Add Order Item");

                editStage.initModality(Modality.APPLICATION_MODAL);

                editStage.showAndWait();

                refreshOrderList();

            } catch (IOException ex) {
                // TODO catch error
                throw new RuntimeException(ex);
            }

        });

        confirmButton.setOnAction(e -> {


        });

        cancelOrderButton.setOnAction(e -> {
            deleteAllOrder();
        });

        noColumn.setText("No.");
        noColumn.setMinWidth(40);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        itemNameColumn.setText("Item");
        itemNameColumn.setMinWidth(150);
        itemNameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        itemNameColumn.setCellValueFactory(cellData -> {
            String itemName = cellData.getValue().getItemNameForDisplay();
            return new SimpleObjectProperty<>(itemName);
        });

        itemTypeColumn.setText("Type");
        itemTypeColumn.setMinWidth(75);
        itemTypeColumn.setStyle("-fx-alignment: CENTER;");
        itemTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("itemType"));

        quantityColumn.setText("Quantity");
        quantityColumn.setMinWidth(75);
        quantityColumn.setStyle("-fx-alignment: CENTER;");
        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        actionButtonColumn.setText("Action");

        actionButtonColumn1.setMinWidth(35);
        actionButtonColumn1.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button editButton = new Button();
            ImageLoader.setUpGraphicButton(editButton, 15, 15, "edit");
            String itemName = cellData.getValue().getItemNameForDisplay();
            String itemQuantity = String.valueOf(cellData.getValue().getQuantity());

            editButton.setOnAction(e -> {

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/edit-dineinorder-item" +
                                            ".fxml"));

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
                    // TODO Should final variable this
                    editStage.setTitle("Edit Order Item");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();

                    refreshOrderList();

                } catch (IOException ex) {
                    // TODO catch error
                    throw new RuntimeException(ex);
                }

            });

            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(35);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            ImageLoader.setUpGraphicButton(deleteButton, 15, 15, "delete");
            String selectedItemName = cellData.getValue().getItemName();
            FoodDrink selectedItem = cellData.getValue();

            deleteButton.setOnAction(e -> {

                Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                        "Delete Item",
                        "Do you want to delete " + selectedItemName + "?"
                );

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {
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

    public Optional<ButtonType> promptForUserAcknowledgement(
            String header,
            String message
    ) {
        return AlertPopUpWindow.displayConfirmationWindow(
                header,
                message
        );
    }

    public void deleteAllOrder() {

        Optional<ButtonType> userChoice = promptForUserAcknowledgement(
                "Cancel Order Request",
                "Do you want to cancel this order?"
        );

        if (userChoice.get()
                .getButtonData().toString()
                .equalsIgnoreCase("OK_DONE")) {
            orderList.clear();
            refreshOrderList();
        }
    }

}
