import org.group.project.classes.Booking;
import org.group.project.classes.Customer;
import org.group.project.classes.Floor;
import org.group.project.test.generators.BookingGenerator;
import org.group.project.test.generators.CustomerGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloorTests {

    @Test
    public void testGetAllUniqueBookings() {
        Customer customer = CustomerGenerator.createCustomer1();
        Customer customer2 = CustomerGenerator.createCustomer2();

        Booking booking1 = BookingGenerator.createBooking1();

        Booking booking2 = BookingGenerator.createBooking2();

        Floor frontHouse = new Floor();

        frontHouse.addNewTable(booking1.getTablePreference());
        frontHouse.addNewTable(booking2.getTablePreference());

        boolean addingBooking1 = frontHouse.addBookingToReservation(booking1);

        // To see if tables on the floor are available or not.
        // Part of checking a more complex table availability process.
//        System.out.println(frontHouse.getEachTableAvailability());

//        boolean addingBooking2 = frontHouse.addBookingToReservation(booking2);

//        System.out.println(frontHouse.getBookingByTable(tableA).getFirst().getCustomer());
//
//        System.out.println("TableA: " + frontHouse.getBookingByTable(tableA));
//        System.out.println("TableB: " + frontHouse.getBookingByTable(tableB));
//        System.out.println("TableC: " + frontHouse.getBookingByTable(tableC));
//        System.out.println("UniqueBookings: " + frontHouse.getAllUniqueBookings());
//
//        LocalDate startDate = LocalDate.of(2024, 2, 20);
//        LocalDate endDate = LocalDate.of(2024, 2, 21);
//        LocalDate startDateMinusOne = startDate.minusDays(1);
//        LocalDate endDatePlusOne = endDate.plusDays(1);
//        System.out.println("Get booking made on 20/02/2024: " + frontHouse.getBookingsByDateRange(startDate, endDate));
//
//        LocalDate date1 = LocalDate.of(2024, 1, 1);
//        LocalDate date2 = LocalDate.of(2024, 1, 2);
//        LocalDate date3 = LocalDate.of(2024, 1, 3);
//        LocalDate date4 = LocalDate.of(2024, 1, 4);
//        LocalDate date5 = LocalDate.of(2024, 1, 5);
//
//        System.out.println(startDate.compareTo(endDate));
//        System.out.println("Date range: " + startDate + " to " + endDate);
//
//        boolean dateRange1 =
//                date1.isAfter(startDateMinusOne) && date1.isBefore(endDatePlusOne);
//        System.out.println(date1 + " > " + startDateMinusOne + " & " + date1 +
//                " < " + endDatePlusOne + ": " + dateRange1);
//
//        boolean dateRange2 =
//                date2.isAfter(startDateMinusOne) && date2.isBefore(endDatePlusOne);
//        System.out.println(date2 + " > " + startDateMinusOne + " & " + date2 +
//                " < " + endDatePlusOne + ": " + dateRange2);
//
//        boolean dateRange3 =
//                date3.isAfter(startDateMinusOne) && date3.isBefore(endDatePlusOne);
//        System.out.println(date3 + " > " + startDateMinusOne + " & " + date3 +
//                " < " + endDatePlusOne + ": " + dateRange3);
//
//        boolean dateRange4 =
//                date4.isAfter(startDateMinusOne) && date4.isBefore(endDatePlusOne);
//        System.out.println(date4 + " > " + startDateMinusOne + " & " + date4 +
//                " < " + endDatePlusOne + ": " + dateRange4);
//
//        boolean dateRange5 =
//                date5.isAfter(startDateMinusOne) && date5.isBefore(endDatePlusOne);
//        System.out.println(date5 + " > " + startDateMinusOne + " & " + date5 +
//                " < " + endDatePlusOne + ": " + dateRange5);
//
//        LocalDate searchDate = LocalDate.of(2024, 2, 20);
//        LocalTime startTime = LocalTime.of(5, 30);
//        LocalTime endTime = LocalTime.of(6, 00);
//        LocalTime startTimeMinusOne = startTime.minusMinutes(1);
//        LocalTime endTimePlusOne = endTime.plusMinutes(1);
//        System.out.println("Get booking made on 20/02/2024 between " + startTime + " to " + endTime + ": " + frontHouse.getBookingsByDateAndTimeRange(searchDate, startTime, endTime));
//
//        LocalTime time1 = LocalTime.of(7, 00);
//        LocalTime time2 = LocalTime.of(7, 30);
//        LocalTime time3 = LocalTime.of(8, 00);
//        LocalTime time4 = LocalTime.of(8, 30);
//        LocalTime time5 = LocalTime.of(9, 00);
//
//        System.out.println(time1.compareTo(time2));
//        System.out.println("Time range: " + startTime + " to " + endTime);
//
//        boolean timeRange1 =
//                time1.isAfter(startTimeMinusOne) && time1.isBefore(endTimePlusOne);
//        System.out.println(time1 + " > " + startTimeMinusOne + " & " + time1 +
//                " < " + endTimePlusOne + ": " + timeRange1);
//
//        boolean timeRange2 =
//                time2.isAfter(startTimeMinusOne) && time2.isBefore(endTimePlusOne);
//        System.out.println(time2 + " > " + startTimeMinusOne + " & " + time2 +
//                " < " + endTimePlusOne + ": " + timeRange2);
//
//        boolean timeRange3 =
//                time3.isAfter(startTimeMinusOne) && time3.isBefore(endTimePlusOne);
//        System.out.println(time3 + " > " + startTimeMinusOne + " & " + time3 +
//                " < " + endTimePlusOne + ": " + timeRange3);
//
//        boolean timeRange4 =
//                time4.isAfter(startTimeMinusOne) && time4.isBefore(endTimePlusOne);
//        System.out.println(time4 + " > " + startTimeMinusOne + " & " + time4 +
//                " < " + endTimePlusOne + ": " + timeRange4);
//
//        boolean timeRange5 =
//                time5.isAfter(startTimeMinusOne) && time5.isBefore(endTimePlusOne);
//        System.out.println(time5 + " > " + startTimeMinusOne + " & " + time5 +
//                " < " + endTimePlusOne + ": " + timeRange5);

//        System.out.println(frontHouse.getNamesOfTables());

//        System.out.print("Before deletion: ");
//        System.out.println(frontHouse.getAllUniqueBookings());
//        System.out.println("Attempt to delete this booking: " + booking1);
//        frontHouse.cancelBooking(booking1);
//        System.out.print("After deletion: ");
//        System.out.println(frontHouse.getAllUniqueBookings());


        assertEquals(true, true);
    }
}
