package org.group.project.classes;

/**
 * This class handles the menu item to represent food or drink.
 *
 * @author azmi_maz
 */
public class FoodDrink {
    private String itemName;
    private String itemType;

    private int quantity;

    // TODO image url

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
        this.quantity = 1;
    }

    /**
     * This constructor creates a new menu item with quantity.
     *
     * @param itemName - the name of the food or drink.
     * @param itemType - the type of the item, either food or drink.
     * @param quantity - the quantity of item.
     */
    public FoodDrink(String itemName, String itemType, int quantity) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.quantity = quantity;
    }

    /**
     * This method increment item quantity by one.
     */
    public void incrementQuantity() {
        quantity += 1;
    }

    /**
     * Setter method to set the quantity to a new one.
     * @param newQuantity - the quantity of the item.
     */
    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    /**
     * Getter method to get the item name.
     *
     * @return the name of the item.
     */
    public String getItemName() {
        return itemName;
    }

    // TODO to check if this is needed vs ImageUrl attribute
    public String getImageFileName() {
        return itemName.trim().replaceAll(" ", "-")
                .toLowerCase() + ".png";
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
     * Getter method to get the item quantity.
     *
     * @return the quantity of the item.
     */
    public int getQuantity() {
        return quantity;
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

    // TODO comment
    public String getItemNameForDisplay() {
        return itemName.substring(0,1).toUpperCase() + itemName.substring(1);
    }

    /**
     * This prints out FoodDrink item in "Item name: (item type)" format.
     *
     * @return the food item in specified format.
     */
    @Override
    public String toString() {
        return getItemName() + " (" + getItemType() + ") quantity: " + getQuantity();
    }

}
