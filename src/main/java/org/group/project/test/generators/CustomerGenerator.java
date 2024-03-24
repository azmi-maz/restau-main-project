package org.group.project.test.generators;

import org.group.project.classes.Customer;

public class CustomerGenerator {

    public static Customer createCustomer1() {
        return new Customer("john", "doe", "john.doe", 1, "41 " +
                "Pine Street, Swansea");
    }

    public static Customer createCustomer2() {
        return new Customer("jane", "doe", "jane.doe", 2, "83 " +
                "Agent Street, Swansea");
    }

    public static Customer createCustomer3() {
        return new Customer("Eddie", "Redmayne", "ed.redmayne", 3, "12 " +
                "Apple Street, London");
    }
}
