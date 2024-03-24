package org.group.project.classes;

/**
 * This class represents Chef role in the restaurant.
 *
 * @author azmi_maz
 */
public class Chef extends Staff implements OrderAction {

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
    public boolean createNewMenuItem(Menu menuList, FoodDrink newItem) {

        menuList.addItemToMenu(newItem);
        return true;
    }

    /**
     * This method is not applicable for chefs.
     *
     * @param newOrder - a new order.
     * @return null.
     */
    @Override
    public Order createAnOrder(Order newOrder) {
        // Chef does not need to create a new order.
        return null;
    }

    /**
     * This method allows chefs to update orders as complete.
     *
     * @param completedOrder - the order to be marked off as complete.
     * @return true if order was updated successfully.
     */
    @Override
    public boolean updateOrderStatus(Order completedOrder) {
        return false;
    }
}
