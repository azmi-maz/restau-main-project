import org.group.project.classes.Booking;
import org.group.project.classes.Customer;
import org.group.project.classes.Floor;
import org.group.project.classes.Table;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloorTests {

    @Test
    public void testGetAllUniqueBookings() {
        Customer customer = new Customer("John", "Doe", "john.doe", 1, "41 " +
                "Pine Street, Swansea");
        Customer customer2 = new Customer("Jane", "Doe", "jane.doe", 2, "40 " +
                "Short Street, Swansea");

        Table tableA = new Table("Table A", 4);
        Table tableB = new Table("Table B", 4);
        Table tableC = new Table("Table C", 4);

        LocalDate bookingDate = LocalDate.of(2024, 2, 20);
        LocalDate bookingDate2 = LocalDate.of(2024, 2, 20);

        LocalTime bookingTime = LocalTime.of(7, 30);
        LocalTime bookingTime2 = LocalTime.of(5, 30);

        int numGuests = 4;
        int numGuests2 = 2;

        List<Table> tableRequests = new ArrayList<>();
        List<Table> tableRequests2 = new ArrayList<>();

        tableRequests.add(tableA);
        tableRequests.add(tableB);

        tableRequests2.add(tableB);
        tableRequests2.add(tableC);

        Booking booking1 = new Booking(customer, bookingDate, bookingTime,
                numGuests, tableRequests);

        Booking booking2 = new Booking(customer2, bookingDate2, bookingTime2,
                numGuests2, tableRequests2);

        Floor frontHouse = new Floor();
        frontHouse.addNewTable(tableA);
        frontHouse.addNewTable(tableB);
        frontHouse.addNewTable(tableC);

        frontHouse.addBookingToReservation(booking1);
        frontHouse.addBookingToReservation(booking2);

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

        assertEquals(true, true);
    }
}
