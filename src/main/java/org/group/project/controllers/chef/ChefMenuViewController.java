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
import org.group.project.classes.Chef;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Menu;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * This class loads the chef menu view.
 */
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class.getResource("images" +
                    "/background/chef-main" +
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

        refreshMenuItemList();

        noColumn.setText("No.");
        noColumn.setMinWidth(40);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(
                            cellData.getValue());
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

        Image favourite = new Image(Objects.requireNonNull
                (Main.class.getResourceAsStream(
                        "images/icons/favourite.png")));
        ImageView empty = new ImageView(favourite);
        empty.setFitWidth(15);
        empty.setFitHeight(15);
        Image favouriteFilled = new Image(Objects.requireNonNull(
                Main.class.getResourceAsStream(
                        "images/icons/favourite-filled.png")));
        ImageView filled = new ImageView(favouriteFilled);
        filled.setFitWidth(15);
        filled.setFitHeight(15);

        dailySpecialColumn.setText("Is Daily Special?");
        dailySpecialColumn.setMinWidth(110);
        dailySpecialColumn.setStyle("-fx-alignment: CENTER;");
        dailySpecialColumn.setCellValueFactory(cellData -> {
            FoodDrink item = cellData.getValue();
            boolean isDailySpecial = item.isItemDailySpecial();
            Button favouriteButton = new Button();
            favouriteButton.setTooltip(
                    new Tooltip("Mark as Daily Special"));

            if (isDailySpecial) {
                ImageLoader.setUpGraphicButton(favouriteButton,
                        15, 15, "favourite-filled");
            } else {
                ImageLoader.setUpGraphicButton(favouriteButton,
                        15, 15, "favourite");
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

            favouriteButton.setOnAction(e -> {
                Chef chef = (Chef) Main.getCurrentUser();

                try {
                    boolean isSuccessful = chef.chooseDailySpecial(
                            item
                    );
                    if (isSuccessful) {
                        AlertPopUpWindow.displayInformationWindow(
                                "Daily Special",
                                String.format(
                                        "%s status was updated successfully.",
                                        item.getItemName()
                                ),
                                "Ok"
                        );
                    }

                    refreshMenuItemList();

                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            "Error",
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

            });

            return new SimpleObjectProperty<>(favouriteButton);
        });

        menuItemTable.setItems(data);

        newItemButton.setOnAction(e -> {

            try {
                FXMLLoader fxmlLoader =
                        new FXMLLoader(Main.class.getResource(
                                "smallwindows/" +
                                        "chef-add-newmenuitem" +
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
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        ex.getMessage()
                );
                ex.printStackTrace();
            }

        });
    }

    // Refreshes the menu table.
    private void refreshMenuItemList() {

        // TODO comment
        menuItemTable.getItems().clear();
        data.clear();

        try {

            Menu menu = new Menu();
            menu.getMenuData(data);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }
}
