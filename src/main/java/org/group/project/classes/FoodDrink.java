package org.group.project.classes;

/**
 * This class handles the menu item to represent food or drink.
 *
 * @author azmi_maz
 */
public class FoodDrink {
    private String itemName;
    private String itemType;

    private boolean isDailySpecial;


    /**
     * This constructor creates a new menu item.
     *
     * @param itemName - the name of the food or drink.
     * @param itemType - the type of the item, either food or drink.
     */
    public FoodDrink(String itemName, String itemType) {
        this.itemName = itemName;
        this.itemType = itemType;
    }

    /**
     * Getter method to get the item name.
     *
     * @return the name of the item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Getter method to get the item type.
     *
     * @return the type of the item.
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Getter method to check if the item is daily special or not.
     *
     * @return true if the item is daily special.
     */
    public boolean isItemDailySpecial() {
        return isDailySpecial;
    }

    /**
     * This method updates the daily special status of the item.
     *
     * @param isSpecial - the new update.
     * @return true if the update was made successfully.
     */
    public void setItemDailySpecial(boolean isSpecial) {
        isDailySpecial = isSpecial;
    }

}
