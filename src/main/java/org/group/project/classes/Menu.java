package org.group.project.classes;

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


}
