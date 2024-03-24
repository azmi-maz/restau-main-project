package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
     * Getter method to get the set of tables.
     *
     * @return the set of tables.
     */
    public Set<Table> getSetOfTables() {
        // to check
        return tableBookings.keySet();
    }

    /**
     * This method gets all the table names in the restaurant.
     *
     * @return the list of table names.
     */
    public List<String> getNamesOfTables() {
        List<String> tableNames = new ArrayList<>();
        tableBookings.keySet().stream().forEach(table -> tableNames.add(table.getTableName()));
        return tableNames;
    }

    /**
     * This method add new table to the tableBookings.
     *
     * @param newTable - new table to be added.
     */
    public void addNewTable(Table newTable) {
        if (!tableBookings.containsKey(newTable)) {
            tableBookings.put(newTable, new ArrayList<>());
        }
    }

    /**
     * This method add new tables from a list of tables.
     *
     * @param newTables - the list of new tables to be added.
     */
    public void addNewTable(List<Table> newTables) {
        for (Table table : newTables) {
            Boolean tableMatches = tableBookings.keySet().stream().anyMatch(thisTable -> {
                return thisTable.getTableName().equalsIgnoreCase(table.getTableName());
            });
            if (!tableMatches) {
                tableBookings.put(table, new ArrayList<>());
            }
        }
    }

    /**
     * Getter method to get list of bookings by table.
     *
     * @param table - the specific table of the restaurant.
     * @return list of bookings for that selected table.
     */
    public List<Booking> getBookingByTable(Table table) {
        return tableBookings.get(table);
    }

    /**
     * This method adds new booking to the list of table reservations.
     *
     * @param booking - the new booking to be added.
     * @return true if the booking was added successfully.
     */
    public boolean addBookingToReservation(Booking booking) {

        if (booking.getTablePreference().size() > 1) {
            List<Table> tables = booking.getTablePreference();
            for (Table table : tables) {
                tableBookings.get(table).add(booking);
            }
            return true;
        } else if (booking.getTablePreference().size() == 1) {
            tableBookings.get(booking.getTablePreference().getFirst()).add(booking);
        }

        return false;
    }

    /**
     * This method gets only unique bookings across all tables.
     *
     * @return the list of unique bookings.
     */
    public List<Booking> getAllUniqueBookings() {
        List<Booking> listOfBookings = new ArrayList<>();
        for (Map.Entry<Table, List<Booking>> entry :
                tableBookings.entrySet()) {
            for (Booking booking : entry.getValue()) {
                if (!listOfBookings.contains(booking)) {
                    listOfBookings.add(booking);
                }
            }
        }
        return listOfBookings;
    }

    public List<Booking> getBookingsByDateRange(LocalDate dateFrom,
                                                LocalDate dateTo) {
        List<Booking> allUniqueBookings = getAllUniqueBookings();
        List<Booking> filteredBookings = new ArrayList<>();
        LocalDate dateFromMinusOne = dateFrom.minusDays(1);
        LocalDate dateToPlusOne = dateTo.plusDays(1);

        // Date From > Date To - should not happen
        if (dateFrom.compareTo(dateTo) > 0) {
            return null;
        }

        for (Booking booking : allUniqueBookings) {

            // Date From is equal to Date To, single date check
            if (dateFrom.compareTo(dateTo) == 0) {

                if (booking.getBookingDate().isEqual(dateFrom)) {
                    filteredBookings.add(booking);
                }

                // Normal date range
            } else {

                if (booking.getBookingDate().isAfter(dateFromMinusOne) &&
                        booking.getBookingDate().isBefore(dateToPlusOne)) {
                    filteredBookings.add(booking);
                }
            }
        }
        return filteredBookings;
    }

    public List<Booking> getBookingsByDateAndTimeRange(LocalDate searchDate,
                                                       LocalTime startTime,
                                                       LocalTime endTime) {

        List<Booking> allUniqueBookings = getAllUniqueBookings();
        List<Booking> filteredBookings = new ArrayList<>();
        LocalTime startFromMinusOne = startTime.minusMinutes(1);
        LocalTime endTimePlusOne = endTime.plusMinutes(1);

        // Time From > Time To - should not happen
        if (startTime.compareTo(endTime) > 0) {
            return null;
        }

        for (Booking booking : allUniqueBookings) {

            if (booking.getBookingDate().compareTo(searchDate) == 0) {

                // Time From is equal to Time To, single time check
                if (startTime.compareTo(endTime) == 0) {

                    if (booking.getBookingTime().equals(startTime)) {
                        filteredBookings.add(booking);
                    }

                    // Normal time range
                } else {

                    if (booking.getBookingTime().isAfter(startFromMinusOne) &&
                            booking.getBookingTime().isBefore(endTimePlusOne)) {
                        filteredBookings.add(booking);
                    }
                }
            }
        }
        return filteredBookings;
    }

    /**
     * This method cancel booking that hasn't already past the booking date.
     *
     * @param booking - the booking to be cancelled.
     * @return true if the cancellation was made successfully.
     */
    public boolean cancelBooking(Booking booking) {

        List<Booking> allUniqueBookings = getAllUniqueBookings();
        Boolean isBookingExist = allUniqueBookings.contains(booking);
        if (isBookingExist) {
            for (Map.Entry<Table, List<Booking>> entry :
                    tableBookings.entrySet()) {
                if (entry.getValue().contains(booking)) {
                    entry.getValue().remove(booking);
                }
            }
            return true;
        }
        return false;
    }
}
