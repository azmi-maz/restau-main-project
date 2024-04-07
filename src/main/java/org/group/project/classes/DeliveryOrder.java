package org.group.project.classes;

import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles delivery orders.
 *
 * @author azmi_maz
 */
public class DeliveryOrder extends Order implements NotifyAction {
    private Driver assignedDriver;
    private String customerAddress;
    private LocalTime deliveryTime;

    /**
     * The constructor to initiate a delivery order with empty basket.
     *
     * @param orderId   - the unique id.
     * @param customer  - the customer who is making the order.
     * @param orderDate - the date of the order.
     * @param orderTime - the time of the order.
     */
    public DeliveryOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime) {
        super(orderId, customer, orderDate, orderTime, "delivery", "pending-approval");
        customerAddress = customer.getDeliveryAddress();
        // TODO review this is needed or not
        // Default 30 mins for now - restaurant policy to deliver within 30
        // mins.
//        deliveryTime = orderTime.plusMinutes(30);
    }

    // TODO comment to get updated data
    public DeliveryOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime, LocalTime deliveryTime,
                         String orderStatus, Driver assignedDriver,
                         List<FoodDrink> orderedList) {
        super(orderId, customer, orderDate, orderTime, "delivery", orderStatus, orderedList);
        customerAddress = customer.getDeliveryAddress();
        this.deliveryTime = deliveryTime;
        this.assignedDriver = assignedDriver;
    }

    /**
     * Getter method to get the customer who made the delivery order.
     *
     * @return the customer.
     */
    public Customer getCustomer() {
        return super.getCustomer();
    }

    /**
     * Getter method to get the date of the order.
     *
     * @return the order date.
     */
    public LocalDate getOrderDate() {
        return super.getOrderDate();
    }

    /**
     * Getter method to get the time of the order.
     *
     * @return the order time.
     */
    public LocalTime getOrderTime() {
        return super.getOrderTime();
    }

    /**
     * Getter method to get the type of the order.
     *
     * @return the order type.
     */
    public String getOrderType() {
        return super.getOrderType();
    }

    /**
     * Getter method to get the status of the order.
     *
     * @return the order status.
     */
    public String getOrderStatus() {
        return super.getOrderStatus();
    }

    /**
     * Getter method to get the address of the customer.
     *
     * @return the delivery address.
     */
    public String getCustomerAddress() {
        return getCustomer().getDeliveryAddress();
    }

    /**
     * This method assigns a driver to the order for delivery arrangement.
     *
     * @param driver - the driver tasked to deliver the order.
     * @return the driver for the UI.
     */
    public Driver assignDriver(Driver driver) {
        assignedDriver = driver;
        return driver;
    }

    /**
     * Getter method to get the assigned driver.
     *
     * @return the driver.
     */
    public Driver getDriver() {
        return assignedDriver;
    }

    /**
     * This method adds delivery time in minutes.
     *
     * @param addTimeInMinutes - the time in minutes.
     * @return the new delivery time for UI.
     */
    public LocalTime addDeliveryTime(int addTimeInMinutes) {
        deliveryTime = deliveryTime.plusMinutes(addTimeInMinutes);
        return deliveryTime;
    }

    /**
     * Getter method to get the delivery time.
     *
     * @return the delivery time.
     */
    public LocalTime getDeliveryTime() {
        return deliveryTime;
    }

    // TODO comment
    public String getDeliveryTimeInFormat() {
        return deliveryTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    // TODO comment
    public String getEstimatedDeliveryTimeForDatabase() {
        // TODO final variable for added time 30 minutes
        LocalTime timeOfApproval = LocalTime.now();
        LocalTime estimatedDeliveryTime = timeOfApproval.plusMinutes(30);
        deliveryTime = estimatedDeliveryTime;
        // TODO note H-m if it can be applied elsewhere
        return estimatedDeliveryTime
                .format(DateTimeFormatter.ofPattern("H-m"));
    }

    // TODO comment
    public String getAssignedDriverId(
            String name) throws FileNotFoundException {
        return String.valueOf(HelperMethods
                .findUserIdByUsername(name));
    }

    // TODO comment
    public List<String> prepareNotificationData() throws FileNotFoundException {
        List<String> data = new ArrayList<>();
        data.add(String.valueOf(HelperMethods.getNewIdByFile("NOTIFICATION")));
        data.add(String.valueOf(super.getCustomer().getCustomerId()));
        data.add(Notification.getNotificationDateForDatabase());
        data.add(Notification.getNotificationTimeForDatabase());
        data.add("delivery");
        data.add("false");
        return data;
    }

    // TODO comment
    public void cancelDeliveryOrder() throws IOException {
        int orderId = super.getOrderId();
        DataManager.editColumnDataByUniqueId("ORDERS",
                orderId, "orderStatus",
                "failed");
    }

    // TODO comment
    public void approveDeliverOrder(
            String driverId
    ) throws IOException {
        int orderId = super.getOrderId();
        DataManager.editColumnDataByUniqueId("ORDERS",
                orderId, "orderStatus",
                "pending-kitchen");
        DataManager.editColumnDataByUniqueId("ORDERS",
                orderId, "assignedDriver",
                driverId);
        DataManager.editColumnDataByUniqueId("ORDERS",
                orderId, "deliveryTime",
                getEstimatedDeliveryTimeForDatabase());
    }

    // TODO comment
    public String approveDeliveryMessage() {
        return String.format(
                "Your order no.%d is on the way. Estimated time of delivery" +
                        "is %s.",
                super.getOrderId(),
                getDeliveryTimeInFormat()
        );
    }

    // TODO comment
    public String cancelDeliveryMessage() {
        return String.format(
                "We're sorry. Your delivery order no.%d was cancelled " +
                        "due to heavy demand in delivery orders. " +
                        "Please try again later.",
                super.getOrderId()
        );
    }

    // TODO comment
    public static void getUpdatedDeliveryOrderData(
            ObservableList<DeliveryOrder> data
    ) throws FileNotFoundException {

        // TODO comment
        List<String> pendingDeliveryList = DataManager.allDataFromFile("ORDERS");

        for (String order : pendingDeliveryList) {
            List<String> orderDetails = List.of(order.split(","));

            // orderId
            int orderId = Integer.parseInt(orderDetails.get(DataFileStructure.getIndexByColName("ORDERS", "orderId")));

            // user
            Customer customer;

            List<String> customerString = HelperMethods.getDataById("USERS",
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "BOOKINGS", "userId")));

            // orderDate
            List<String> orderDateDetails = List.of(orderDetails.get(2).split("-"));

            LocalDate orderDate =
                    LocalDate.of(Integer.parseInt(orderDateDetails.get(0)),
                            Integer.parseInt(orderDateDetails.get(1)),
                            Integer.parseInt(orderDateDetails.get(2)));

            // orderTime
            List<String> orderTimeDetails = List.of(orderDetails.get(3).split("-"));

            LocalTime orderTime =
                    LocalTime.of(Integer.parseInt(orderTimeDetails.get(0)),
                            Integer.parseInt(orderTimeDetails.get(1)));

            // orderType
            String orderType =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderType"));

            // orderStatus
            String orderStatus =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderStatus"));

            // orderedFoodDrinkLists
            String[] orderListArray =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderedLists")).split(";");
            List<FoodDrink> orderFoodDrinkList = new ArrayList<>();
            for (String item : orderListArray) {
                // TODO find item type by item name
                String itemType = "food";
                FoodDrink newItem = new FoodDrink(item, itemType);
                boolean isNewItem = true;
                for (FoodDrink currentItem : orderFoodDrinkList) {
                    if (currentItem.getItemName().equalsIgnoreCase(item)) {
                        currentItem.incrementQuantity();
                        isNewItem = false;
                    }
                }
                if (isNewItem) {
                    orderFoodDrinkList.add(newItem);
                }
            }

            // assignedDriver and deliveryTime
            LocalTime deliveryTime = null;
            Driver assignedDriver = null;

            if (orderType.equalsIgnoreCase("delivery")) {
                List<String> deliveryTimeDetails =
                        List.of(orderDetails.get(DataFileStructure.getIndexByColName(
                                "ORDERS", "deliveryTime")).split(
                                "-"));
                if (deliveryTimeDetails.size() == 2) {
                    deliveryTime = LocalTime.of(Integer.parseInt(deliveryTimeDetails.get(0)),
                            Integer.parseInt(deliveryTimeDetails.get(1)));
                }
                List<String> driverString = HelperMethods.getDataById("USERS",
                        orderDetails.get(DataFileStructure.getIndexByColName(
                                "ORDERS", "assignedDriver")));

                if (driverString != null) {
                    assignedDriver = new Driver(
                            Integer.parseInt(driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "userId"))),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "firstName")),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "lastName")),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "username"))
                    );
                }
            }

            if (customerString != null) {
                customer = new Customer(
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "firstName")),
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "lastName")),
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "username")),
                        Integer.parseInt(customerString.get(DataFileStructure.getIndexByColName("USERS", "userId"))),
                        HelperMethods.formatAddressToRead(customerString.get(DataFileStructure.getIndexByColName("USERS", "address")))
                );

                // TODO here's the filter for pending yet-to-be-approved deliveries
                // TODO to filer in "pending-approval" only?
                if (orderType.equalsIgnoreCase("delivery")
                        && orderStatus.equalsIgnoreCase("pending-approval")) {
                    data.add(new DeliveryOrder(
                            orderId,
                            customer,
                            orderDate,
                            orderTime,
                            deliveryTime,
                            orderStatus,
                            assignedDriver,
                            orderFoodDrinkList
                    ));

                }
            }
        }

    }

    @Override
    public void notifyCustomer(Customer customer,
                               boolean isSuccessfulRequest) throws FileNotFoundException {
        List<String> newNotificationMessage = prepareNotificationData();

        if (isSuccessfulRequest) {
            newNotificationMessage.add(approveDeliveryMessage());
        } else {
            newNotificationMessage.add(cancelDeliveryMessage());
        }

        // TODO try catch
        try {
            DataManager.appendDataToFile("NOTIFICATION", newNotificationMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
