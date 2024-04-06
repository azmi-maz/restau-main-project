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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.*;
import org.group.project.scenes.WindowSize;
import org.group.project.scenes.customer.stackViews.MenuController;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;
import org.group.project.test.generators.CustomerGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class CustomerOrderDetailsViewController {

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView bgImage;

    private List<FoodDrink> orderList;

    private List<Order> newOrder;

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
    private TableView<FoodDrink> orderDetailsTable =
            new TableView<>();

    private ObservableList<FoodDrink> data =
            FXCollections.observableArrayList();

    public CustomerOrderDetailsViewController(List<FoodDrink> orderList,
                                              List<Order> newOrder) {
        this.orderList = orderList;
        this.newOrder = newOrder;
    }

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

        choiceBox.getItems().add("Delivery Order");
        choiceBox.getItems().add("Takeaway Order");

        choiceBox.setValue("Delivery Order");

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

        // TODO this doesn't work
        actionButtonColumn1.setStyle("-fx-padding: 0; -fx-margin: 0; " +
                "-fx-background-color: transparent; -fx-text-fill: transparent;");


        actionButtonColumn1.setMinWidth(35);
        actionButtonColumn1.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button editButton = new Button();
            editButton.setTooltip(new Tooltip("Edit Order"));
            ImageLoader.setUpGraphicButton(editButton, 15, 15, "edit");
            String imageFile = cellData.getValue().getImageFileName();
            String labelName = cellData.getValue().getItemNameForDisplay();

            editButton.setOnMousePressed(e -> {
                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/edit-order-item" +
                                            ".fxml"));

                    BorderPane borderPane = fxmlLoader.load();

                    CustomerMenuOrderEditItemController controller =
                            fxmlLoader.getController();

                    controller.setItemToEdit("images/menu/" + imageFile,
                            labelName, orderList);
                    Scene editScene = new Scene(borderPane,
                            WindowSize.MEDIUM.WIDTH,
                            WindowSize.MEDIUM.HEIGHT);

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
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }

            });

            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setText("");
        actionButtonColumn2.setMinWidth(35);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            deleteButton.setTooltip(new Tooltip("Delete Order"));
            ImageLoader.setUpGraphicButton(deleteButton, 15, 15, "delete");
            FoodDrink item = cellData.getValue();

            deleteButton.setOnMousePressed(e -> {
                orderList.remove(item);
                refreshOrderList();
            });

            return new SimpleObjectProperty<>(deleteButton);
        });

        confirmButton.setOnMousePressed(e -> {
            newOrder.clear();
            // TODO try catch
            try {
                newOrder.add(createNewOrder());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            OrderDetailsController.presenter.goToOrderConfirmation();
        });

        // TODO cancel button returns to menu with orderlist erased
        cancelButton.setOnMousePressed(e -> {

            Optional<ButtonType> userChoice = promptForUserAcknowledgement();

            if (userChoice.get()
                    .getButtonData().toString()
                    .equalsIgnoreCase("OK_DONE")) {
                cancelConfirmationAndGoBackToMenu();
            }
        });

        refreshOrderList();

        // TODO to delete this if not in use
//        addTableViewStyle();

    }

    // TODO comment
    private void refreshOrderList() {
        orderDetailsTable.getItems().clear();
        data.clear();
        data.addAll(orderList);
        orderDetailsTable.setItems(data);
    }

    // TODO this works but has warning errors
    private void addTableViewStyle() {
//        orderDetailsTable.skinProperty().addListener((a, b, newSkin) ->
//        {
//            TableView table = (TableView) orderDetailsTable.lookup(
//                    "TableView");
//
//            System.out.println(table);
//            System.out.println(table.getStylesheets().add(String.valueOf(Main.class.getResource("css/table-view" +
//                ".css"))));
//
//
//
//        });
    }

    public Order createNewOrder() throws FileNotFoundException {

        // TODO try catch upstream
        int orderId = HelperMethods.getNewIdByFile("ORDERS");

        // TODO get user from main homepage, temp using dummy user
        Customer newCustomer = CustomerGenerator.createCustomer1();

        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();

        // TODO choicebox must not be empty
        // TODO constant variables, where to final static
        String orderType = choiceBox.getValue();
        String orderStatus = "";
        if (orderType.equalsIgnoreCase("delivery order")) {
            orderType = "delivery";
            orderStatus = "pending-approval";
        } else {
            orderType = "takeaway";
            orderStatus = "pending";
        }

        Order orderDetails = new Order(
                orderId,
                newCustomer,
                dateNow,
                timeNow,
                orderType,
                orderStatus
        );

        for (FoodDrink item : orderList) {
            orderDetails.addItemToOrderList(item);
        }

        return orderDetails;
    }

    public Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                "Cancel Order Request",
                "Do you want to cancel this order?"
        );
    }

    public void cancelConfirmationAndGoBackToMenu() {
        OrderDetailsController.presenter.returnToMenu();
        MenuController.orderList.clear();
    }
}
