package org.group.project.test.generators;

import org.group.project.classes.Customer;
import org.group.project.classes.DeliveryOrder;

import java.time.LocalDate;
import java.time.LocalTime;

public class DeliveryOrderGenerator {

    public static DeliveryOrder createDeliverOrder1() {

        int orderId = 1;

        Customer customer = CustomerGenerator.createCustomer1();

        LocalDate orderDate = LocalDate.of(2024, 2, 20);
        LocalTime orderTime = LocalTime.of(7, 30);

        return new DeliveryOrder(orderId, customer, orderDate, orderTime);
    }
}
