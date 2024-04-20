package org.group.project.test.generators;

import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataLineGenerator {

    public static List<String> createCustomerLine1() throws FileNotFoundException {
        return new ArrayList<>(Arrays.asList(
                Integer.toString(HelperMethods.getNewUserId()),
                "john",
                "doe",
                "john.doe",
                "customer",
                HelperMethods.formatAddressToWrite("43 Swan Street, Swansea," +
                        " SA2 8EW"),
                "0", "0", "false", "false",
                "0"));
    }
}
