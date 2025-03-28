package org.group.project.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a table for a customer to book.
 *
 * @author azmi_maz
 */
public class Table {
    private String tableName;
    private List<Seat> seats;
    private String imageUrl;
    private boolean availabilityStatus;
    private double heightSub;
    private double heightDiv;
    private double widthSub;
    private double widthDiv;
    private int colIdx;
    private int rowIdx;
    private int colSpan;
    private int rowSpan;
    private static final String EMPTY_TABLE = "empty";
    private static final String FILLED_TABLE = "filled";

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
        this.imageUrl = "url";
        this.availabilityStatus = true;
        this.heightSub = 0;
        this.heightDiv = 0;
        this.widthSub = 0;
        this.widthDiv = 0;
        this.colIdx = 0;
        this.rowIdx = 0;
        this.colSpan = 0;
        this.rowSpan = 0;

    }

    /**
     * This constructor creates a table from the database default information.
     * 
     * @param tableName  - the table name given.
     * @param numOfSeats - the number of seats that thos table needs.
     * @param url        - the image url used to represent the table on display.
     * @param status     - the availability of the table based on the desired
     *                   booking date and time requested by the user.
     * @param heightSub  - the height sub value of the image on display.
     * @param heightDiv  - the height div value of the image on display.
     * @param widthSub   - the width sub value of the image on display.
     * @param widthDiv   - the width div value of the image on display.
     * @param colIdx     - the column position/index of the image on display.
     * @param rowIdx     - the row position/index of the image on display.
     * @param colSpan    - the image horizontal span across the specified number of
     *                   columns.
     * @param rowSpan    - the image vertical span across the specified number of
     *                   rows.
     */
    public Table(
            String tableName,
            int numOfSeats,
            String url,
            boolean status,
            double heightSub,
            double heightDiv,
            double widthSub,
            double widthDiv,
            int colIdx,
            int rowIdx,
            int colSpan,
            int rowSpan) {
        this.tableName = tableName;
        this.seats = new ArrayList<>();
        for (int i = 0; i < numOfSeats; i++) {
            seats.add(new Seat());
        }
        this.imageUrl = url;
        this.availabilityStatus = status;
        this.heightSub = heightSub;
        this.heightDiv = heightDiv;
        this.widthSub = widthSub;
        this.widthDiv = widthDiv;
        this.colIdx = colIdx;
        this.rowIdx = rowIdx;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
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
     * Getter method for the number of seats a table has.
     *
     * @return the total number of seats.
     */
    public int getNumberOfSeats() {
        return seats.size();
    }

    /**
     * Getter method for the image url of the table.
     * 
     * @return the url of the image.
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * Getter method for table availability status.
     * 
     * @return the table availability.
     */
    public boolean getAvailabilityStatus() {
        return this.availabilityStatus;
    }

    /**
     * Getter method for the image height sub value.
     * 
     * @return the height sub value of the image.
     */
    public double getHeightSub() {
        return this.heightSub;
    }

    /**
     * Getter method for the image height div value.
     * 
     * @return the height div value of the image.
     */
    public double getHeightDiv() {
        return this.heightDiv;
    }

    /**
     * Getter method for the image width sub value.
     * 
     * @return the width sub value of the image.
     */
    public double getWidthSub() {
        return this.widthSub;
    }

    /**
     * Getter method for the image width div value.
     * 
     * @return the width div value of the image.
     */
    public double getWidthDiv() {
        return this.widthDiv;
    }

    /**
     * Getter method for the starting column index of the image.
     * 
     * @return the starting column index of the image.
     */
    public int getColIdx() {
        return this.colIdx;
    }

    /**
     * Getter method for the starting row index of the image.
     * 
     * @return the starting row index of the image.
     */
    public int getRowIdx() {
        return this.rowIdx;
    }

    /**
     * Getter method for the horizontal span across columns of the image.
     * 
     * @return the number columns spanned for the image.
     */
    public int getColSpan() {
        return this.colSpan;
    }

    /**
     * Getter method for the vertical span across rows of the image.
     * 
     * @return the number of rows spanned for the image.
     */
    public int getRowSpan() {
        return this.rowSpan;
    }

    /**
     * This flips the table availability status.
     */
    public void flipAvailabilityStatus() {
        this.availabilityStatus = !availabilityStatus;
    }

    /**
     * This change the image url from "empty" to "filled".
     */
    public void changeUrlToFilled() {
        this.imageUrl = this.imageUrl.replace(EMPTY_TABLE, FILLED_TABLE);
    }

    /**
     * This change the image url from "filled" to "empty".
     */
    public void changeUrlToEmpty() {
        this.imageUrl = this.imageUrl.replace(FILLED_TABLE, EMPTY_TABLE);
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
