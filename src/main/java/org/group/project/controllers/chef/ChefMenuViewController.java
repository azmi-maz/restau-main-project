package org.group.project.controllers.chef;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.DataFileStructure;
import org.group.project.classes.DataManager;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.WindowSize;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

public class ChefMenuViewController {

    @FXML
    private TableColumn<FoodDrink, String> noColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemNameColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemTypeColumn;

    @FXML
    private TableColumn<FoodDrink, Button> dailySpecialColumn;

    @FXML
    private Button newItemButton;

    @FXML
    private BorderPane borderPane;

    private List<String> menuItemList;

    @FXML
    private TableView<FoodDrink> menuItemTable = new TableView<>();
    private ObservableList<FoodDrink> data =
            FXCollections.observableArrayList();

    // TODO comment
    public void initialize() throws URISyntaxException, FileNotFoundException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/chef-main" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        refreshMenuItemList();

        noColumn.setText("No.");
        noColumn.setMinWidth(40);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        itemNameColumn.setText("Item Name");
        itemNameColumn.setMinWidth(200);
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

        Image favourite = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(
                "images/icons/favourite.png")));
        ImageView empty = new ImageView(favourite);
        empty.setFitWidth(15);
        empty.setFitHeight(15);
        Image favouriteFilled = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(
                "images/icons/favourite-filled.png")));
        ImageView filled = new ImageView(favouriteFilled);
        filled.setFitWidth(15);
        filled.setFitHeight(15);

        dailySpecialColumn.setText("Is Daily Special?");
        dailySpecialColumn.setMinWidth(110);
        dailySpecialColumn.setStyle("-fx-alignment: CENTER;");
        dailySpecialColumn.setCellValueFactory(cellData -> {
            String itemName = cellData.getValue().getItemName();
            boolean isDailySpecial = cellData.getValue().isItemDailySpecial();
            Button favouriteButton = new Button();
            favouriteButton.setTooltip(new Tooltip("Mark as Daily Special"));

            if (isDailySpecial) {
                ImageLoader.setUpGraphicButton(favouriteButton, 15, 15, "favourite-filled");
            } else {
                ImageLoader.setUpGraphicButton(favouriteButton, 15, 15, "favourite");
            }

            favouriteButton.getStyleClass().add("exit");

            favouriteButton.setOnMouseEntered(e -> {
                favouriteButton.getStyleClass().remove("exit");
                favouriteButton.getStyleClass().add("enter");
            });

            favouriteButton.setOnMouseExited(e -> {
                favouriteButton.getStyleClass().remove("enter");
                favouriteButton.getStyleClass().add("exit");
            });

            String newStatus = String.valueOf(!isDailySpecial);
            favouriteButton.setOnAction(e -> {

                // TODO try catch
                try {
                    DataManager.editColumnDataByUniqueId("MENU",
                            itemName, "isDailySpecial",
                            newStatus);

                    refreshMenuItemList();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            });

            return new SimpleObjectProperty<>(favouriteButton);
        });

        menuItemTable.setItems(data);

        newItemButton.setOnAction(e -> {

            try {
                FXMLLoader fxmlLoader =
                        new FXMLLoader(Main.class.getResource(
                                "smallwindows/chef-add-newmenuitem" +
                                        ".fxml"));

                VBox vbox = fxmlLoader.load();

                ChefAddNewItemController controller =
                        fxmlLoader.getController();

                controller.setItemList(menuItemList);

                Scene editScene = new Scene(vbox,
                        WindowSize.SMALL.WIDTH,
                        WindowSize.SMALL.HEIGHT);

                Stage editStage = new Stage();
                editStage.setScene(editScene);
                // TODO Should final variable this
                editStage.setTitle("New Item");

                editStage.initModality(Modality.APPLICATION_MODAL);

                editStage.showAndWait();

                refreshMenuItemList();

            } catch (IOException ex) {
                // TODO catch error
                throw new RuntimeException(ex);
            }

        });
    }

    // TODO need this public so can refresh after adding new item
    public void refreshMenuItemList() throws FileNotFoundException {

        // TODO comment
        menuItemTable.getItems().clear();
        data.clear();

        menuItemList = DataManager.allDataFromFile("MENU");

        for (String item : menuItemList) {

            List<String> itemDetails = List.of(item.split(","));
            String itemName = itemDetails.get(DataFileStructure
                    .getIndexByColName("MENU", "itemName"));
            String itemType = itemDetails.get(DataFileStructure
                    .getIndexByColName("MENU", "itemType"));
            boolean isDailySpecial = Boolean
                    .parseBoolean(itemDetails
                            .get(DataFileStructure
                                    .getIndexByColName("MENU", "isDailySpecial")));

            data.add(new FoodDrink(
                    itemName,
                    itemType,
                    isDailySpecial
            ));

        }
    }
}
