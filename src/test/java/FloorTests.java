import org.group.project.classes.Booking;
import org.group.project.classes.Customer;
import org.group.project.classes.Floor;
import org.group.project.classes.Table;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FloorTests {

    @Test
    public void testGetAllUniqueBookings() {
        Customer customer = new Customer("John", "Doe", "john.doe", 1, "41 " +
                "Pine Street, Swansea");
        Table tableA = new Table("Table A", 4);
        LocalDate bookingDate = LocalDate.of(2024, 3, 26);
        LocalTime bookingTime = LocalTime.of(7, 30);
        int numGuests = 4;
        List<Table> tableRequests = new ArrayList<>();
        tableRequests.add(tableA);
        Booking booking1 = new Booking(customer, bookingDate, bookingTime,
                numGuests, tableRequests);

        Booking bookingSimilar = new Booking(customer, bookingDate, bookingTime,
                numGuests, tableRequests);

        Customer customer2 = new Customer("Jane", "Doe", "jane.doe", 2, "40 " +
                "Short Street, Swansea");
        Table tableB = new Table("Table B", 4);
        LocalDate bookingDate2 = LocalDate.of(2024, 2, 20);
        LocalTime bookingTime2 = LocalTime.of(5, 20);
        int numGuests2 = 2;
        List<Table> tableRequests2 = new ArrayList<>();
        tableRequests2.add(tableB);


        Booking booking2 = new Booking(customer2, bookingDate2, bookingTime2,
                numGuests2, tableRequests2);

        Floor frontHouse = new Floor();
        frontHouse.addNewTable(tableA);
        frontHouse.addNewTable(tableB);

        frontHouse.addBookingToReservation(booking1);
        frontHouse.addBookingToReservation(booking2);

        System.out.println(frontHouse.getListOfTables());
    }
}
