package org.group.project.classes;

import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class reports on most popular menu items and outstanding orders.
 *
 * @author azmi_maz
 */
public class KitchenReport extends Report {

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportType - the type of the report.
     * @param user       - the user who generated the report.
     */
    public KitchenReport(String reportType,
                         User user) {
        super(reportType, user);
        int nextReportId = 0;
        try {
            nextReportId = HelperMethods.getNewIdByFile("REPORTS");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        super.setReportId(nextReportId);
        if (reportType.equalsIgnoreCase("most popular item")) {
            super.setReportData(getMostPopularMenuItemsReport());
        } else if (reportType.equalsIgnoreCase("outstanding orders")) {
            super.setReportData(getOutstandingOrdersReport());
        } else if (reportType.equalsIgnoreCase("most active customer")) {
            super.setReportData(getMostActiveCustomerReport());
        }
    }

    /**
     * This method generates the most popular menu items report.
     *
     * @return the report for most popular menu items.
     */
    public String getMostPopularMenuItemsReport() {
        return prepareReportData("most popular item");
    }

    /**
     * This method generates the outstanding orders report.
     *
     * @return the report for outstanding orders.
     */
    public String getOutstandingOrdersReport() {
        return prepareReportData("outstanding orders");
    }

    // TODO
    public String getMostActiveCustomerReport() {
        return prepareReportData("most active customer");
    }

    // TODO
    public String prepareReportData(
            String reportChoice
    ) {

        Kitchen kitchen = new Kitchen();
        List<Order> orderList = kitchen.getAllOrderTickets();

        // Most popular item
        Map<String, Integer> itemCountMap = new HashMap<>();
        // Most active customer
        Map<Integer, Integer> customerCountMap = new HashMap<>();
        // Most ordered food
        Map<String, Integer> foodOnlyMap = new HashMap<>();
        // Most ordered drink
        Map<String, Integer> drinkOnlyMap = new HashMap<>();
        // Most ordered item in takeaway orders
        Map<String, Integer> takeAwayOrderItemMap = new HashMap<>();
        // Most ordered item in delivery orders
        Map<String, Integer> deliveryOrderItemMap = new HashMap<>();
        // Most ordered item in dinein orders
        Map<String, Integer> dineOrderItemMap = new HashMap<>();
        // Number of outstanding orders by type
        Map<String, Integer> outstandingOrderMap = new HashMap<>();
        for (Order order : orderList) {
            List<FoodDrink> foodsOrdered = order.getOrderedFoodDrinkLists();
            String orderType = order.getOrderType();
            for (FoodDrink item : foodsOrdered) {
                String itemName = item.getItemName();
                if (itemCountMap.containsKey(itemName)) {
                    int currentCount = itemCountMap.get(itemName);
                    currentCount++;
                    itemCountMap.put(itemName, currentCount);
                } else {
                    // TODO magic
                    itemCountMap.put(itemName, 1);
                }

                String itemType = item.getItemType();
                if (foodOnlyMap.containsKey(itemName)
                        && itemType.equalsIgnoreCase("food")) {
                    int getFoodCount = foodOnlyMap.get(itemName);
                    getFoodCount++;
                    foodOnlyMap.put(itemName, getFoodCount);
                } else {
                    foodOnlyMap.put(itemName, 1);
                }

                if (drinkOnlyMap.containsKey(itemName)
                        && itemType.equalsIgnoreCase("drink")) {
                    int getFoodCount = foodOnlyMap.get(itemName);
                    getFoodCount++;
                    drinkOnlyMap.put(itemName, getFoodCount);
                } else {
                    drinkOnlyMap.put(itemName, 1);
                }

                if (takeAwayOrderItemMap.containsKey(itemName)
                        && orderType.equalsIgnoreCase("takeaway")) {
                    int getFoodCount = takeAwayOrderItemMap.get(itemName);
                    getFoodCount++;
                    takeAwayOrderItemMap.put(itemName, getFoodCount);
                } else {
                    takeAwayOrderItemMap.put(itemName, 1);
                }

                if (deliveryOrderItemMap.containsKey(itemName)
                        && orderType.equalsIgnoreCase("delivery")) {
                    int getFoodCount = deliveryOrderItemMap.get(itemName);
                    getFoodCount++;
                    deliveryOrderItemMap.put(itemName, getFoodCount);
                } else {
                    deliveryOrderItemMap.put(itemName, 1);
                }

                if (dineOrderItemMap.containsKey(itemName)
                        && orderType.equalsIgnoreCase("dinein")) {
                    int getFoodCount = dineOrderItemMap.get(itemName);
                    getFoodCount++;
                    dineOrderItemMap.put(itemName, getFoodCount);
                } else {
                    dineOrderItemMap.put(itemName, 1);
                }
            }

            int customerId = order.getCustomer().getCustomerId();
            if (customerCountMap.containsKey(customerId)) {
                int currentCustomer = customerCountMap.get(customerId);
                currentCustomer++;
                customerCountMap.put(customerId, currentCustomer);
            } else {
                // TODO magic
                customerCountMap.put(customerId, 1);
            }

            if (outstandingOrderMap.containsKey(orderType)) {
                int currentCount = outstandingOrderMap.get(orderType);
                currentCount++;
                outstandingOrderMap.put(orderType, currentCount);
            } else {
                // TODO magic
                outstandingOrderMap.put(orderType, 1);
            }

        }

        String mostPopularItem = "";
        int numOfMostPopularItem = 0;
        for (Map.Entry<String, Integer> entry : itemCountMap.entrySet()) {
            String currentItemName = entry.getKey();
            int currentItemCounter = entry.getValue();
            if (numOfMostPopularItem < currentItemCounter) {
                numOfMostPopularItem = currentItemCounter;
                mostPopularItem = currentItemName;
            } else if (numOfMostPopularItem == 0) {
                numOfMostPopularItem = currentItemCounter;
                mostPopularItem = currentItemName;
            }
        }

        int mostActiveCustomerId = 0;
        int mostActiveCustomerCounter = 0;
        for (Map.Entry<Integer, Integer> entry : customerCountMap.entrySet()) {
            int currentId = entry.getKey();
            int currentCounter = entry.getValue();
            if (mostActiveCustomerCounter < currentCounter) {
                mostActiveCustomerCounter = currentCounter;
                mostActiveCustomerId = currentId;
            } else if (mostActiveCustomerCounter == 0) {
                mostActiveCustomerCounter = currentCounter;
                mostActiveCustomerId = currentId;
            }
        }

        String mostOrderedFoodItem = "";
        int mostOrderedFoodItemCounter = 0;
        for (Map.Entry<String, Integer> entry : foodOnlyMap.entrySet()) {
            String currentFood = entry.getKey();
            int currentCounter = entry.getValue();
            if (mostOrderedFoodItemCounter < currentCounter) {
                mostOrderedFoodItemCounter = currentCounter;
                mostOrderedFoodItem = currentFood;
            } else if (mostOrderedFoodItemCounter == 0) {
                mostOrderedFoodItemCounter = currentCounter;
                mostOrderedFoodItem = currentFood;
            }
        }

        String mostOrderedDrinkItem = "";
        int mostOrderedDrinkCounter = 0;
        for (Map.Entry<String, Integer> entry : drinkOnlyMap.entrySet()) {
            String currentDrink = entry.getKey();
            int currentCounter = entry.getValue();
            if (mostOrderedDrinkCounter < currentCounter) {
                mostOrderedDrinkCounter = currentCounter;
                mostOrderedDrinkItem = currentDrink;
            } else if (mostOrderedDrinkCounter == 0) {
                mostOrderedDrinkCounter = currentCounter;
                mostOrderedDrinkItem = currentDrink;
            }
        }

        int numOfTakeawayOrders = 0;
        int numOfDeliveryOrders = 0;
        int numOfDineinOrders = 0;

        String mostOrderedTakeawayItem = "";
        int mostOrderedTakeawayItemCounter = 0;
        for (Map.Entry<String, Integer> entry : takeAwayOrderItemMap.entrySet()) {
            String currentItem = entry.getKey();
            int currentCounter = entry.getValue();
            numOfTakeawayOrders++;
            if (mostOrderedTakeawayItemCounter < currentCounter) {
                mostOrderedTakeawayItemCounter = currentCounter;
                mostOrderedTakeawayItem = currentItem;
            } else if (mostOrderedTakeawayItemCounter == 0) {
                mostOrderedTakeawayItemCounter = currentCounter;
                mostOrderedTakeawayItem = currentItem;
            }
        }

        String mostOrderedDeliveryItem = "";
        int mostOrderedDeliveryItemCounter = 0;
        for (Map.Entry<String, Integer> entry : deliveryOrderItemMap.entrySet()) {
            String currentItem = entry.getKey();
            int currentCounter = entry.getValue();
            numOfDeliveryOrders++;
            if (mostOrderedDeliveryItemCounter < currentCounter) {
                mostOrderedDeliveryItemCounter = currentCounter;
                mostOrderedDeliveryItem = currentItem;
            } else if (mostOrderedDeliveryItemCounter == 0) {
                mostOrderedDeliveryItemCounter = currentCounter;
                mostOrderedDeliveryItem = currentItem;
            }
        }

        String mostOrderedDineinItem = "";
        int mostOrderedDineinItemCounter = 0;
        for (Map.Entry<String, Integer> entry : dineOrderItemMap.entrySet()) {
            String currentItem = entry.getKey();
            int currentCounter = entry.getValue();
            numOfDineinOrders++;
            if (mostOrderedDineinItemCounter < currentCounter) {
                mostOrderedDineinItemCounter = currentCounter;
                mostOrderedDineinItem = currentItem;
            } else if (mostOrderedDineinItemCounter == 0) {
                mostOrderedDineinItemCounter = currentCounter;
                mostOrderedDineinItem = currentItem;
            }
        }

        int numOfTotalOrders = numOfTakeawayOrders
                + numOfDeliveryOrders
                + numOfDineinOrders;

        int totalNumOfOutstandingOrders = 0;
        int numOfOutstandingDinein = 0;
        int numOfOutstandingTakeaway = 0;
        int numOfOutstandingDelivery = 0;
        for (Map.Entry<String, Integer> entry : outstandingOrderMap.entrySet()) {
            String currentItemType = entry.getKey();
            int currentCounter = entry.getValue();
            if (currentItemType.equalsIgnoreCase("dinein")) {
                numOfOutstandingDinein = currentCounter;
            } else if (currentItemType.equalsIgnoreCase("takeaway")) {
                numOfOutstandingTakeaway = currentCounter;
            } else {
                numOfOutstandingDelivery = currentCounter;
            }
        }

        totalNumOfOutstandingOrders = numOfOutstandingDinein
                + numOfOutstandingTakeaway
                + numOfOutstandingDelivery;

        Menu menu = new Menu();
        String typeOfPopularItem = menu.findTypeByItemName(mostPopularItem);
        String mostPopularItemReport = String.format(
                "The %s is a highly sought-after %s, with a remarkable %d orders.",
                mostPopularItem,
                typeOfPopularItem.equalsIgnoreCase("food") ?
                        "dish" : "cocktail",
                numOfMostPopularItem
        );

        String mostActiveCustomerName = "";
        try {
            String userName = HelperMethods.getDataById(
                    "USERS",
                    String.valueOf(mostActiveCustomerId)
            ).get(DataFileStructure.getIndexByColName(
                    "USERS",
                    "username"
            ));
            User user = HelperMethods.getUserByUsername(userName);
            mostActiveCustomerName = user.getDataForListDisplay();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String mostActiveCustomerReport = String.format(
                "%s is a loyal customer who has placed a total of %d orders.",
                mostActiveCustomerName,
                mostActiveCustomerCounter
        );

        String mostOrderedFoodReport = String.format(
                "The restaurant's most popular dish is %s, while the bar's top choice during happy hours is the %s.",
                mostOrderedFoodItem,
                mostOrderedDrinkItem
        );

        String analysisOnOrderTypeReport = String.format(
                "The total number of orders is %d. Customers in the area have " +
                        "been placing a large number of " +
                        "takeaway orders, with a total of %d orders so far, " +
                        "and %s is the top choice. During " +
                        "the evenings, the number of delivery orders reaches " +
                        "a total of %d, and it seems that %s is a popular " +
                        "option among customers. On weekends, our customers " +
                        "who have a passion for food love to come in and " +
                        "savor a delicious meal with us. We usually get " +
                        "about %d dine-in orders, and it appears that %s" +
                        " is the preferred choice among our customers.",
                numOfTotalOrders,
                numOfTakeawayOrders,
                mostOrderedTakeawayItem,
                numOfDeliveryOrders,
                mostOrderedDeliveryItem,
                numOfDineinOrders,
                mostOrderedDineinItem
        );

        String outstandingOrdersByType = String.format(
                "The kitchen is bustling, with %d tickets to fulfil. " +
                        "Let's make sure the kitchen doesn't experience " +
                        "burnout. Give priority to %d dine-in orders as " +
                        "they are our top sellers, followed by %d takeaways, " +
                        "particularly during lunch hours. Lastly, focus " +
                        "on increasing our sales through %d deliveries.",
                totalNumOfOutstandingOrders,
                numOfOutstandingDinein,
                numOfOutstandingTakeaway,
                numOfOutstandingDelivery
        );

        if (reportChoice.equalsIgnoreCase("most popular item")) {
            return mostPopularItemReport
                    + mostOrderedFoodReport
                    + analysisOnOrderTypeReport;
        } else if (reportChoice.equalsIgnoreCase("outstanding orders")) {
            return outstandingOrdersByType;
        } else if (reportChoice.equalsIgnoreCase("most active customer")) {
            return mostActiveCustomerReport;
        }
        return null;
    }
}
