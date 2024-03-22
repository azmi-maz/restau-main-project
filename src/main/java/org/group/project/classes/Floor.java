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

    public List<Table> getListOfTables() {
        // to check
        return (List<Table>) tableBookings.keySet();
    }

    public void addNewTable(Table newTable) {
        tableBookings.put(newTable, new ArrayList<>());
    }

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



}
