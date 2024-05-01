package org.group.project.classes;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.group.project.Main;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class stores menu items to display the restaurant menu.
 *
 * @author azmi_maz
 */
public class Menu {
    private List<FoodDrink> menuOfItems;

    private final int DAILY_SPECIAL_TAG_HEIGHT = 55;
    private final int DAILY_SPECIAL_TAG_WIDTH = 55;
    private static final List<String> foodTypes =
            new ArrayList<>(Arrays.asList(
                    "Food",
                    "Drink"
            ));

    /**
     * The constructor sets up the menu and updates its data
     * from the database.
     *
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public Menu() throws TextFileNotFoundException {

        menuOfItems = new ArrayList<>();

        try {
            menuOfItems = getFoodDrinkFromDatabase();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Getter method to get the current menu list.
     *
     * @return the menu items list.
     */
    public List<FoodDrink> getMenuOfItems() {
        return menuOfItems;
    }

    /**
     * This method add one item to the menu list.
     *
     * @param newItem - a new item for the menu list.
     * @return true if the new item was added successfully.
     */
    public boolean addItemToMenu(FoodDrink newItem) {
        menuOfItems.add(newItem);
        return true;
    }

    /**
     * This method select one item as the daily special.
     *
     * @param selectedItem - the chosen item to be made as daily special.
     * @return true if the selection was done successfully.
     */
    public boolean selectItemAsDailySpecial(FoodDrink selectedItem) {
        for (FoodDrink item : menuOfItems) {
            if (item.getItemName().equalsIgnoreCase(
                    selectedItem.getItemName())) {
                item.setItemDailySpecial(true);
            }
        }
        return true;
    }

    /**
     * This method select one item and change its daily special status to false.
     *
     * @param selectedItem - the item being selected.
     * @return true if item daily special status was updated successfully.
     */
    public boolean deselectItemAsDailySpecial(FoodDrink selectedItem) {
        for (FoodDrink item : menuOfItems) {
            if (item.getItemName().equalsIgnoreCase(
                    selectedItem.getItemName()) &&
                    item.isItemDailySpecial()) {
                item.setItemDailySpecial(false);
            }
        }
        return true;
    }

    /**
     * This method gets the menu items from the database.
     *
     * @return the list of all menu items in the restaurant.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public List<FoodDrink> getFoodDrinkFromDatabase()
            throws TextFileNotFoundException {

        try {
            List<FoodDrink> foodDrinkList = new ArrayList<>();
            List<String> allMenuItemsFromDatabase = DataManager
                    .allDataFromFile("MENU");

            for (String item : allMenuItemsFromDatabase) {
                foodDrinkList.add(
                        getFoodDrinkFromString(item)
                );
            }
            return foodDrinkList;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets the food/drink item from a string.
     *
     * @param item - the string of food/drink item.
     * @return the food/drink object.
     */
    public FoodDrink getFoodDrinkFromString(
            String item
    ) {
        List<String> itemDetails = List.of(item.split(","));
        String itemName = itemDetails.get(
                DataFileStructure.getIndexByColName(
                        "MENU",
                        "itemName"
                ));
        String itemType = itemDetails.get(
                DataFileStructure.getIndexByColName(
                        "MENU",
                        "itemType"
                ));
        boolean isDailySpecial = Boolean
                .parseBoolean(itemDetails
                        .get(DataFileStructure
                                .getIndexByColName("MENU",
                                        "isDailySpecial")));
        return new FoodDrink(
                itemName,
                itemType,
                isDailySpecial
        );
    }

    /**
     * This method finds the type of item based on its name.
     *
     * @param name - the name of the item.
     * @return the type of the item.
     */
    public String findTypeByItemName(String name) {
        for (FoodDrink item : menuOfItems) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                return item.getItemType();
            }
        }
        return null;
    }

    /**
     * This method populates a table view list with the menu data.
     *
     * @param data - the table view list to be updated.
     */
    public void getMenuData(
            ObservableList<FoodDrink> data
    ) {

        List<FoodDrink> menuData = getMenuOfItems();
        for (FoodDrink item : menuData) {
            data.add(item);
        }
    }

    /**
     * This method populates the grid pane of the customer menu.
     *
     * @param gridPane - the grid pane to be updated.
     * @throws URISyntaxException        - if the image uri does not work.
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public void populateCustomerMenuFromDatabase(
            GridPane gridPane
    ) throws URISyntaxException, TextFileNotFoundException {

        try {
            List<String> imageDataList = DataManager
                    .allDataFromFile("MENU");

            Image dailySpecial = new Image(Main.class
                    .getResource("images" +
                            "/icons/daily-special-stamp.png").toURI().toString());

            for (String imageData : imageDataList) {
                getImageStackFromString(
                        gridPane,
                        dailySpecial,
                        imageData
                );
            }

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method prepares an image of a single food/drink item from a string.
     *
     * @param gridPane     - the grid pane to be updated.
     * @param dailySpecial - if the image needs to be tagged as daily special.
     * @param imageData    - the string that contains all the data for that item.
     * @throws URISyntaxException - if the image uri does not work.
     */
    public void getImageStackFromString(
            GridPane gridPane,
            Image dailySpecial,
            String imageData
    ) throws URISyntaxException {
        List<String> imageDataDetails = List.of(
                imageData.split(","));
        boolean isDailySpecial = Boolean.parseBoolean(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "isDailySpecial")));
        String url = imageDataDetails.get(
                DataFileStructure.getIndexByColName("MENU",
                        "imageurl"));
        double heightSub = Double.parseDouble(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "height-sub")));
        double heightDiv = Double.parseDouble(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "height-div")));
        double widthSub = Double.parseDouble(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "width-sub")));
        double widthDiv = Double.parseDouble(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "width-div")));
        int colIdx = Integer.parseInt(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "colIdx")));
        int rowIdx = Integer.parseInt(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "rowIdx")));
        int colSpan = Integer.parseInt(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "colSpan")));
        int rowSpan = Integer.parseInt(
                imageDataDetails.get(DataFileStructure
                        .getIndexByColName("MENU",
                                "rowSpan")));
        String imgAlign = imageDataDetails.get(
                DataFileStructure.getIndexByColName("MENU",
                        "imgAlign"));
        String tagAlign = imageDataDetails.get(
                DataFileStructure.getIndexByColName("MENU",
                        "tagAlign"));
        String stackAlign = imageDataDetails.get(
                DataFileStructure.getIndexByColName("MENU",
                        "stackAlign"));
        double maxHeight = Double.parseDouble(
                imageDataDetails.get(
                        DataFileStructure.getIndexByColName("MENU",
                                "maxHeight")));
        double maxWidth = Double.parseDouble(
                imageDataDetails.get(
                        DataFileStructure.getIndexByColName("MENU",
                                "maxWidth")));

        Image image = new Image(Main.class.getResource(url)
                .toURI().toString());

        ImageView imageView = new ImageView(image);
        imageView.fitHeightProperty().bind(
                gridPane.heightProperty().subtract(heightSub)
                        .divide(heightDiv));
        imageView.fitWidthProperty().bind(
                gridPane.widthProperty().subtract(widthSub)
                        .divide(widthDiv));
        imageView.setPreserveRatio(true);

        StackPane imageViewFirstStack = new StackPane();
        StackPane imageViewSecondStack = new StackPane();
        imageViewFirstStack.getChildren().add(imageView);
        ImageView dailySpecialImg = new ImageView(dailySpecial);
        dailySpecialImg.setFitHeight(DAILY_SPECIAL_TAG_HEIGHT);
        dailySpecialImg.setFitWidth(DAILY_SPECIAL_TAG_WIDTH);
        dailySpecialImg.setPreserveRatio(true);

        if (isDailySpecial) {
            imageViewSecondStack.getChildren().add(dailySpecialImg);
        }
        imageViewFirstStack.getChildren().add(imageViewSecondStack);

        gridPane.add(imageViewFirstStack, colIdx, rowIdx, colSpan, rowSpan);

        imageViewFirstStack.setAlignment(imageView,
                Pos.valueOf(imgAlign));
        imageViewFirstStack.setAlignment(dailySpecialImg,
                Pos.valueOf(tagAlign));
        imageViewFirstStack.setAlignment(imageViewSecondStack,
                Pos.valueOf(stackAlign));
        imageViewSecondStack.setMaxHeight(maxHeight);
        imageViewSecondStack.setMaxWidth(maxWidth);
    }

    /**
     * This method populates a choice box with menu items for dine-in order.
     *
     * @param comboItemName - the choice box to be updated.
     */
    public void updateDineinMenuChoiceBox(
            ComboBox<String> comboItemName
    ) {
        List<FoodDrink> menuList = getMenuOfItems();
        List<String> itemNames = new ArrayList<>();
        for (FoodDrink item : menuList) {
            if (!itemNames.contains(
                    item.getItemName()
            )) {
                itemNames.add(
                        item.getItemNameForDisplay()
                );
            }
        }
        Collections.sort(itemNames);
        for (String itemName : itemNames) {
            comboItemName.getItems()
                    .add(itemName);
        }
    }

    /**
     * This method gets a preset item to add to the menu.
     *
     * @param itemName - the name of the preset item.
     * @return the list of string containing the preset item info.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public List<String> getPresetMenuItem(
            String itemName
    ) throws TextFileNotFoundException {
        try {
            List<String> presetMenuList = DataManager
                    .allDataFromFile("PRESET_ITEMS");
            for (String item : presetMenuList) {
                List<String> itemDetails = List.of(item.split(","));
                String currentItemName = itemDetails.get(
                        DataFileStructure
                                .getIndexColOfUniqueId(
                                        "PRESET_ITEMS"
                                )
                );
                if (currentItemName
                        .equalsIgnoreCase(itemName)) {
                    return itemDetails;
                }
            }
            return null;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets a fixed preset item based on user input. Default is
     * pizza.
     *
     * @param itemName - the name of the item.
     * @return - the list of string of the preset item.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public List<String> getPresetItem(String itemName)
            throws TextFileNotFoundException {

        try {

            List<String> presetItem = new ArrayList<>();
            switch (itemName) {
                case "pizza", "pepsi", "coke", "spaghetti", "soup":
                    presetItem = getPresetMenuItem(
                            itemName
                    );
                    break;
                default:
                    presetItem = getPresetMenuItem(
                            "pizza"
                    );
                    break;
            }
            return presetItem;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method adds a new item to the database.
     *
     * @param newItem - the new item string to be added.
     * @return true if the database was updated successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean addNewItemToDatabase(
            List<String> newItem
    ) throws TextFileNotFoundException {
        try {
            boolean isSuccessful = DataManager
                    .appendDataToFile("MENU", newItem);
            if (isSuccessful) {
                return true;
            }
            return false;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method edits an existing item as daily special.
     *
     * @param itemName  - the selected item name.
     * @param newStatus - the new status of that item.
     * @return true if the update was made successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean editItemDailySpecialStatus(
            String itemName,
            String newStatus
    ) throws TextFileNotFoundException {
        boolean isSuccessful = false;
        try {
            isSuccessful = DataManager.editColumnDataByUniqueId(
                    "MENU",
                    itemName, "isDailySpecial",
                    newStatus);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        if (isSuccessful) {
            return true;
        }
        return false;
    }

    /**
     * This method populates a choice box with item types for users to
     * select from.
     *
     * @param choiceBox - the choice box to be updated.
     */
    public void updateItemTypeChoiceBox(
            ChoiceBox<String> choiceBox
    ) {
        for (String foodType : foodTypes) {
            choiceBox.getItems()
                    .add(foodType);
        }
        choiceBox.setValue(foodTypes.getFirst());
    }


}
