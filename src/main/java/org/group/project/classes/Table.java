package org.group.project.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a table for a customer to book.
 *
 * @author azmi_maz
 */
public class Table {
    private final String tableName;
    private final List<Seat> seats;

    /**
     * This constructor creates a table with a specified number of seats.
     *
     * @param tableName  - the table name given.
     * @param numOfSeats - the number of seats that this table needs.
     */
    public Table(String tableName, int numOfSeats) {
        this.tableName = tableName;
        this.seats = new ArrayList<>();
        for (int i = 0; i < numOfSeats; i++) {
            seats.add(new Seat());
        }
    }

    /**
     * Getter method to get the table name.
     *
     * @return the name of the table.
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Getter method to get the number of seats a table has.
     *
     * @return the total number of seats.
     */
    public int getNumberOfSeats() {
        return seats.size();
    }

    /**
     * This method books seats of a table based on the number of seats required.
     *
     * @param seatsToBook - the number of seats to book.
     * @return true if the seat bookings are successful.
     */
    public boolean bookSeats(int seatsToBook) {

        if (seatsToBook == 0) {
            return false;
        }
        int counter;
        if (seatsToBook >= seats.size()) {
            counter = seats.size();
        } else {
            counter = seatsToBook;
        }

        for (int i = 0; i < seats.size(); i++) {
            if (counter > 0 && seats.get(i).isAvailable()) {
                seats.get(i).setAvailability(false);
                counter--;
            }
        }

        if (counter != 0) {
            // overspill was not handled, assuming seats are booked perfectly.
            return false;
        }
        return true;
    }

    /**
     * This method checks if the table is fully booked or not.
     *
     * @return true if all the seats of table is booked.
     */
    public boolean isTableFullyBooked() {
        int seatsBooked = getNumberOfSeats();
        for (Seat seat : seats) {
            if (!seat.isAvailable()) {
                seatsBooked--;
            }
        }
        if (seatsBooked == 0) {
            return true;
        }
        return false;
    }

    /**
     * This method count how many seats are available.
     *
     * @return the number of seats available.
     */
    public int checksHowManySeatsAvailable() {
        int count = 0;
        if (!isTableFullyBooked()) {
            for (Seat seat : seats) {
                if (seat.isAvailable()) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    /**
     * To print the table name.
     *
     * @return the table name.
     */
    @Override
    public String toString() {
        return tableName;
    }


}
