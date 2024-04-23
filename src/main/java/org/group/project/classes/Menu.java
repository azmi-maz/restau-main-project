package org.group.project.classes;

import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores menu items to display the restaurant menu.
 *
 * @author azmi_maz
 */
public class Menu {
    private List<FoodDrink> menuOfItems;

    /**
     * The constructor that sets up an empty menu list.
     */
    public Menu() {

        menuOfItems = new ArrayList<FoodDrink>();

        try {
            menuOfItems = getFoodDrinkFromDatabase();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
            if (item.getItemName().equalsIgnoreCase(selectedItem.getItemName())) {
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
            if (item.getItemName().equalsIgnoreCase(selectedItem.getItemName()) &&
                    item.isItemDailySpecial()) {
                item.setItemDailySpecial(false);
            }
        }
        return true;
    }

    // TODO
    public List<FoodDrink> getFoodDrinkFromDatabase() throws FileNotFoundException {

        List<FoodDrink> foodDrinkList = new ArrayList<>();
        List<String> allMenuItemsFromDatabase = DataManager.allDataFromFile("MENU");

        for (String item : allMenuItemsFromDatabase) {
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
            foodDrinkList.add(
                    new FoodDrink(
                            itemName,
                            itemType
                    )
            );
        }
        return foodDrinkList;
    }

    // TODO
    public String findTypeByItemName(String name) {
        for (FoodDrink item : menuOfItems) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                return item.getItemType();
            }
        }
        return null;
    }


}
