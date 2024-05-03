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
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * This class loads the chef menu view.
 *
 * @author azmi_maz
 */
public class ChefMenuViewController {
    private static final String BG_IMAGE = "images" +
            "/background/chef-main" +
            ".jpg";
    private static final int COLUMN_WIDTH_40 = 40;
    private static final int COLUMN_WIDTH_75 = 75;
    private static final int COLUMN_WIDTH_110 = 110;
    private static final int COLUMN_WIDTH_200 = 200;
    private static final String NO_COLUMN = "No.";
    private static final String ITEM_NAME_COLUMN = "Item Name";
    private static final String TYPE_COLUMN_NAME = "Type";
    private static final String TYPE_COLUMN = "itemType";
    private static final String SPECIAL_COLUMN = "Is Daily Special?";
    private static final String FAVOURITE_IMAGE = "images/icons/favourite.png";
    private static final String FAVOURITE_FILLED_IMAGE =
            "images/icons/favourite-filled.png";
    private static final String DAILY_SPECIAL_TITLE = "Daily Special";
    private static final String DAILY_SPECIAL_MESSAGE =
            "%s status was updated successfully.";
    private static final String OK = "Ok";
    private static final String ADD_NEW_ITEM_WINDOW = "smallwindows/" +
            "chef-add-newmenuitem" +
            ".fxml";
    private static final String NEW_ITEM_WINDOW_TITLE = "New Item";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String CENTER_LEFT = "-fx-alignment: CENTER-LEFT;";
    private static final String SPECIAL_TOOLTIP = "Mark as Daily Special";
    private static final String FAVOURITE_FILLED_BUTTON = "favourite-filled";
    private static final String FAVOURITE_BUTTON = "favourite";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String EXIT_STYLE = "exit";
    private static final String ENTER_STYLE = "enter";
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

        refreshMenuItemList();

        noColumn.setText(NO_COLUMN);
        noColumn.setMinWidth(COLUMN_WIDTH_40);
        noColumn.setStyle(CENTERED);
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(
                            cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        itemNameColumn.setText(ITEM_NAME_COLUMN);
        itemNameColumn.setMinWidth(COLUMN_WIDTH_200);
        itemNameColumn.setStyle(CENTER_LEFT);
        itemNameColumn.setCellValueFactory(cellData -> {
            String itemName = cellData.getValue().getItemNameForDisplay();
            return new SimpleObjectProperty<>(itemName);
        });

        itemTypeColumn.setText(TYPE_COLUMN_NAME);
        itemTypeColumn.setMinWidth(COLUMN_WIDTH_75);
        itemTypeColumn.setStyle(CENTERED);
        itemTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>(TYPE_COLUMN));

        Image favourite = new Image(Objects.requireNonNull
                (Main.class.getResourceAsStream(
                        FAVOURITE_IMAGE)));
        ImageView empty = new ImageView(favourite);
        empty.setFitWidth(15);
        empty.setFitHeight(15);
        Image favouriteFilled = new Image(Objects.requireNonNull(
                Main.class.getResourceAsStream(
                        FAVOURITE_FILLED_IMAGE)));
        ImageView filled = new ImageView(favouriteFilled);
        filled.setFitWidth(15);
        filled.setFitHeight(15);

        dailySpecialColumn.setText(SPECIAL_COLUMN);
        dailySpecialColumn.setMinWidth(COLUMN_WIDTH_110);
        dailySpecialColumn.setStyle(CENTERED);
        dailySpecialColumn.setCellValueFactory(cellData -> {
            FoodDrink item = cellData.getValue();
            boolean isDailySpecial = item.isItemDailySpecial();
            Button favouriteButton = new Button();
            favouriteButton.setTooltip(
                    new Tooltip(SPECIAL_TOOLTIP));

            if (isDailySpecial) {
                ImageLoader.setUpGraphicButton(favouriteButton,
                        BUTTON_WIDTH, BUTTON_HEIGHT, FAVOURITE_FILLED_BUTTON);
            } else {
                ImageLoader.setUpGraphicButton(favouriteButton,
                        BUTTON_WIDTH, BUTTON_HEIGHT, FAVOURITE_BUTTON);
            }

            favouriteButton.getStyleClass().add(EXIT_STYLE);

            favouriteButton.setOnMouseEntered(e -> {
                favouriteButton.getStyleClass().remove(EXIT_STYLE);
                favouriteButton.getStyleClass().add(ENTER_STYLE);
            });

            favouriteButton.setOnMouseExited(e -> {
                favouriteButton.getStyleClass().remove(ENTER_STYLE);
                favouriteButton.getStyleClass().add(EXIT_STYLE);
            });

            favouriteButton.setOnAction(e -> {
                Chef chef = (Chef) MainScenesMap.getCurrentUser();

                try {
                    boolean isSuccessful = chef.chooseDailySpecial(
                            item
                    );
                    if (isSuccessful) {
                        AlertPopUpWindow.displayInformationWindow(
                                DAILY_SPECIAL_TITLE,
                                String.format(
                                        DAILY_SPECIAL_MESSAGE,
                                        item.getItemName()
                                ), OK
                        );
                    }

                    refreshMenuItemList();

                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
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
                                ADD_NEW_ITEM_WINDOW));

                VBox vbox = fxmlLoader.load();

                ChefAddNewItemController controller =
                        fxmlLoader.getController();

                controller.setItemList(menuItemList);

                Scene editScene = new Scene(vbox,
                        WindowSize.SMALL.WIDTH,
                        WindowSize.SMALL.HEIGHT);

                Stage editStage = new Stage();
                editStage.setScene(editScene);

                editStage.setTitle(NEW_ITEM_WINDOW_TITLE);

                editStage.initModality(Modality.APPLICATION_MODAL);

                editStage.showAndWait();

                refreshMenuItemList();

            } catch (IOException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }

        });
    }

    // Refreshes the menu table.
    private void refreshMenuItemList() {

        menuItemTable.getItems().clear();
        data.clear();

        try {

            Menu menu = new Menu();
            menu.getMenuData(data);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }
}
