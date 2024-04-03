package org.group.project.test.generators;

import org.group.project.classes.Booking;
import org.group.project.classes.Customer;
import org.group.project.classes.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingGenerator {

    static Table tableA = TableGenerator.createTableOfFourSeats1();
    static Table tableB = TableGenerator.createTableOfFourSeats2();
    static Table tableC = TableGenerator.createTableOfFourSeats3();

    public static Booking createBooking1() {
        int bookingId = 1;
        Customer customer = CustomerGenerator.createCustomer1();

        LocalDate bookingDate1 = LocalDate.of(2024, 2, 20);
        LocalTime bookingTime1 = LocalTime.of(7, 30);

        int numGuests = 4;

        List<Table> tableRequests = new ArrayList<>();

        tableRequests.add(tableA);
        tableRequests.add(tableB);

        return new Booking(bookingId, customer, bookingDate1, bookingTime1,
                numGuests, tableRequests);
    }

    public static Booking createBooking2() {
        int bookingId = 2;
        Customer customer = CustomerGenerator.createCustomer2();

        LocalDate bookingDate2 = LocalDate.of(2024, 2, 21);
        LocalTime bookingTime2 = LocalTime.of(5, 30);

        int numGuests2 = 2;

        List<Table> tableRequests = new ArrayList<>();

        tableRequests.add(tableB);
        tableRequests.add(tableC);

        return new Booking(bookingId, customer, bookingDate2, bookingTime2,
                numGuests2, tableRequests);
    }

}
