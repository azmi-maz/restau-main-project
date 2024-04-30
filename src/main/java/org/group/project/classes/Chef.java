package org.group.project.classes;

import org.group.project.exceptions.TextFileNotFoundException;

import java.util.List;

/**
 * This class represents Chef role in the restaurant.
 *
 * @author azmi_maz
 */
public class Chef extends Staff {

    /**
     * The constructor to create new chef with basic info.
     *
     * @param firstName - the first name of the staff.
     * @param lastName  - the last name of the staff.
     * @param username  - the username chosen by the staff.
     */
    public Chef(String firstName, String lastName, String username) {
        super(firstName, lastName, username, false);
    }

    /**
     * The constructor to create new chef from the database.
     *
     * @param firstName             - the first name of the staff.
     * @param lastName              - the last name of the staff.
     * @param username              - the username chosen by the staff.
     * @param hasAdminRight         - the right to admin access.
     * @param numOfHoursToWork      - the num of hours remaining.
     * @param numOfTotalHoursWorked - the total hours worked by chef.
     */
    public Chef(String firstName, String lastName, String username,
                boolean hasAdminRight, int numOfHoursToWork,
                int numOfTotalHoursWorked) {
        super(firstName, lastName, username, hasAdminRight,
                numOfHoursToWork, numOfTotalHoursWorked);
    }

    /**
     * This method select an item as daily special.
     *
     * @param item - the food or drink item selected.
     * @return true if the item was updated successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean chooseDailySpecial(FoodDrink item)
            throws TextFileNotFoundException {

        try {

            Menu menu = new Menu();
            String itemName = item.getItemName();
            boolean isDailySpecial = item.isItemDailySpecial();
            String newStatus = String.valueOf(!isDailySpecial);
            boolean isSuccessful = menu.editItemDailySpecialStatus(
                    itemName,
                    newStatus
            );
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
     * This method creates a new item and add it to the menu.
     *
     * @param newItem - a new food or drink.
     * @return true if the new item was added successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean createNewMenuItem(String newItem)
            throws TextFileNotFoundException {
        try {
            Menu menu = new Menu();
            List<String> newMenuItem = menu.getPresetItem(newItem);
            boolean isSuccessful = menu.addNewItemToDatabase(newMenuItem);
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
     * This method allows chefs to update orders as complete.
     *
     * @param selectedOrder - the order to be marked off as complete.
     * @return true if order was updated successfully.
     * @throws TextFileNotFoundException - if text file is non-existent
     */
    public boolean updateOrderStatus(Order selectedOrder)
            throws TextFileNotFoundException {

        try {

            if (selectedOrder.getOrderType().equalsIgnoreCase(
                    "takeaway")) {
                TakeawayOrder takeawayOrder = (TakeawayOrder) selectedOrder;
                takeawayOrder.setEstimatedPickupTime();
                takeawayOrder.notifyCustomer(
                        takeawayOrder.getCustomer(),
                        true
                );
            }
            return selectedOrder.markOffOrderAsComplete();

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
