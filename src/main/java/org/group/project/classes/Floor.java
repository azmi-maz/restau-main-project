package org.group.project.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class stores and handles table reservations made by customers.
 *
 * @author azmi_maz
 */
public class Floor {
    protected HashMap<Table, List<Booking>> tableBookings;

    /**
     * This constructor set up the restaurant floor.
     */
    public Floor() {
        tableBookings = new HashMap<>();
    }

    /**
     * Getter method to get the list of table reservations.
     * @return the list of table reservations.
     */
    public List<Table> getListOfTables() {
        // to check
        return (List<Table>) tableBookings.keySet();
    }

    /**
     * This method add new table to the tableBookings.
     * @param newTable - new table to be added.
     */
    public void addNewTable(Table newTable) {
        tableBookings.put(newTable, new ArrayList<>());
    }

    /**
     * This method adds new booking to the list of table reservations.
     * @param booking - the new booking to be added.
     * @return true if the booking was added successfully.
     */
    public boolean addBookingToReservation(Booking booking) {
        // To check - this only handles one table preference, how about many
        // tables?
        List<Booking> getTableBookings =
                tableBookings.get(booking.getTablePreference().getFirst());
        if (getTableBookings.size() > 0) {
            getTableBookings.add(booking);
            return true;
        }
        return false;
    }

    public List<Booking> getAllUniqueBookings() {

        return new ArrayList<>();
    }



}
