package org.group.project.classes;

public class Chef extends Staff {

    /**
     * The constructor to create new chef.
     *
     * @param firstName - the first name of the staff.
     * @param lastName  - the last name of the staff.
     * @param username  - the username chosen by the staff.
     */
    public Chef(String firstName, String lastName, String username) {
        super(firstName, lastName, username, false);
    }

    /**
     * This method select an item as daily special.
     *
     * @param item - the food or drink item selected.
     * @return true if the item was updated successfully.
     */
    public boolean chooseDailySpecial(FoodDrink item) {
        item.setItemDailySpecial(true);
        return true;
    }

    /**
     * This method creates a new item and add it to the menu.
     *
     * @param newItem - a new food or drink.
     * @return true if the new item was added sucessfully.
     */
    public boolean createNewMenuItem(FoodDrink newItem) {
        // to code
        return true;
    }
}
