package org.group.project.classes;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class stores and handles table reservations made by customers.
 *
 * @author azmi_maz
 */
public class Floor {
    protected HashMap<Table, List<Booking>> tableBookings;

    private static final int MAX_BOOKING_LENGTH = 5;
    private static final LocalTime OPENING_TIME = LocalTime.of(
            10, 00);
    private static final LocalTime LATEST_BOOKING_TIME = LocalTime.of(
            21, 00);

    /**
     * This constructor set up the restaurant floor and updates its data
     * from the database.
     *
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public Floor() throws TextFileNotFoundException {

        tableBookings = new HashMap<>();
        List<Table> tableList;
        List<Booking> bookingList;

        try {

            bookingList = getBookingDataFromDatabase();
            tableList = getTableDataFromDatabase();

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        for (Table table : tableList) {
            if (!tableBookings.containsKey(table)) {
                tableBookings.put(
                        table,
                        new ArrayList<>()
                );
            }
        }

        for (Booking booking : bookingList) {
            String currentBookingTableName = booking
                    .getTablePreference()
                    .getFirst()
                    .getTableName();
            for (Map.Entry<Table, List<Booking>> entry :
                    tableBookings.entrySet()) {
                Table table = entry.getKey();
                String tableName = table.getTableName();
                List<Booking> bookings = entry.getValue();
                if (tableName
                        .equalsIgnoreCase(currentBookingTableName)) {
                    bookings.add(
                            booking
                    );
                }
            }
        }
    }

    /**
     * Getter method to get the set of tables.
     *
     * @return the set of tables.
     */
    public Set<Table> getSetOfTables() {
        return tableBookings.keySet();
    }

    /**
     * This method gets all the table names in the restaurant.
     *
     * @return the list of table names.
     */
    public List<String> getNamesOfTables() {
        List<String> tableNames = new ArrayList<>();
        tableBookings.keySet().stream().forEach(
                table -> tableNames.add(table.getTableName()));
        return tableNames;
    }

    /**
     * This method gets all the table availability status in the restaurant.
     *
     * @return the list of table availabilty for all tables.
     */
    public List<Boolean> getEachTableAvailability() {
        List<Boolean> tableAvailability = new ArrayList<>();
        tableBookings.keySet().stream().forEach(
                table -> tableAvailability.add(table.isTableFullyBooked()));
        return tableAvailability;
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
            Boolean tableMatches = tableBookings.keySet()
                    .stream().anyMatch(thisTable -> {
                        return thisTable.getTableName()
                                .equalsIgnoreCase(table.getTableName());
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

                /*
                 * To implement this, it requires tables to exist separately
                 * based on date and time. And to implement checking those
                 * table is available or not.
                 *
                 * purposely not implemented
                 * table.bookSeats(booking.getNumOfGuests());
                 */

                tableBookings.get(table).add(booking);
            }
            return true;
        } else if (booking.getTablePreference().size() == 1) {
            tableBookings.get(booking.getTablePreference()
                    .getFirst()).add(booking);
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

    /**
     * Getter method to get bookings by date range.
     *
     * @param dateFrom - the start date for the filter.
     * @param dateTo   - the end date for the filter.
     * @return the list of bookings between two dates, inclusive.
     */
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

    /**
     * Getter method to get bookings within the same date and to filter
     * between two time range.
     *
     * @param searchDate - the specific date when bookings are made.
     * @param startTime  - the start time of the filter.
     * @param endTime    - the end time of the filter.
     * @return the list of bookings that matched all filter criteria.
     */
    public List<Booking> getBookingsByDateAndTimeRange(
            LocalDate searchDate,
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
     * This method gets the booking data from the database.
     *
     * @return the list of bookings.
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public List<Booking> getBookingDataFromDatabase()
            throws TextFileNotFoundException {

        try {
            List<Booking> bookingList = new ArrayList<>();
            List<String> tableReservations = DataManager
                    .allDataFromFile("BOOKINGS");
            UserManagement userManagement = new UserManagement();

            for (String booking : tableReservations) {
                bookingList.add(
                        getBookingFromString(
                                userManagement,
                                booking)
                );
            }

            return bookingList;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method creates a booking object from a data taken from the database.
     *
     * @param userManagement - the class that is needed to get the customer.
     * @param booking        - the booking data in string.
     * @return the booking object with updated data.
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public Booking getBookingFromString(
            UserManagement userManagement,
            String booking
    ) throws TextFileNotFoundException {
        try {

            List<String> bookingDetails = List.of(booking.split(","));
            int bookingId = Integer.parseInt(bookingDetails.get(
                    DataFileStructure.getIndexColOfUniqueId(
                            "BOOKINGS"
                    )
            ));
            int customerId = Integer.parseInt(bookingDetails.get(
                    DataFileStructure.getIndexByColName(
                            "BOOKINGS", "userId")));
            Customer customer = userManagement.getCustomerById(
                    customerId
            );

            LocalDate bookingDate = getLocalDateFromString(
                    bookingDetails.get(DataFileStructure
                            .getIndexByColName(
                                    "BOOKINGS",
                                    "bookingDate"
                            ))
            );
            LocalTime bookingTime = getLocalTimeFromString(
                    bookingDetails.get(DataFileStructure
                            .getIndexByColName(
                                    "BOOKINGS",
                                    "bookingTime"
                            ))
            );
            int numOfGuests = Integer.parseInt(bookingDetails.get(
                    DataFileStructure
                            .getIndexByColName(
                                    "BOOKINGS",
                                    "numOfGuests"
                            )
            ));
            int bookingLength = Integer.parseInt(bookingDetails.get(
                    DataFileStructure
                            .getIndexByColName(
                                    "BOOKINGS",
                                    "bookingLength"
                            )
            ));
            String bookingTable = bookingDetails.get(
                    DataFileStructure
                            .getIndexByColName(
                                    "BOOKINGS",
                                    "tablePreference"
                            )
            );
            String bookingStatus = bookingDetails.get(
                    DataFileStructure
                            .getIndexByColName(
                                    "BOOKINGS",
                                    "bookingStatus"
                            )
            );
            List<Table> tablePreference = new ArrayList<>();

            getTablesFromString(tablePreference, bookingTable);

            return new Booking(
                    bookingId,
                    customer,
                    bookingDate,
                    bookingTime,
                    numOfGuests,
                    tablePreference,
                    bookingLength,
                    bookingStatus
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method change the date string to LocalDate.
     *
     * @param bookingDate - the date string.
     * @return the LocalDate of that date string.
     */
    public LocalDate getLocalDateFromString(
            String bookingDate
    ) {
        List<String> bookingDateDetails =
                List.of(bookingDate.split("-"));
        return LocalDate.of(Integer.parseInt(bookingDateDetails.get(0)),
                Integer.parseInt(bookingDateDetails.get(1)),
                Integer.parseInt(bookingDateDetails.get(2)));
    }

    /**
     * This method change the time string to LocalTime.
     *
     * @param bookingTime - the time string.
     * @return the LocalTime of that time string.
     */
    public LocalTime getLocalTimeFromString(
            String bookingTime
    ) {
        List<String> bookingTimeDetails =
                List.of(bookingTime.split("-"));
        return LocalTime.of(Integer.parseInt(bookingTimeDetails.get(0)),
                Integer.parseInt(bookingTimeDetails.get(1)));
    }

    /**
     * This method adds an existing Table to the Table list for a booking.
     *
     * @param tablePreference - the list of tables in a booking.
     * @param searchTableName - the name of the table to match with.
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public void getTablesFromString(
            List<Table> tablePreference,
            String searchTableName
    ) throws TextFileNotFoundException {
        try {
            List<Table> tableList = getTableDataFromDatabase();

            for (Table table : tableList) {
                if (table
                        .getTableName()
                        .equalsIgnoreCase(searchTableName)) {
                    tablePreference.add(table);
                }
            }

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method creates a new booking created by a customer.
     *
     * @param customerId      - the id of the customer making the new booking.
     * @param bookingDate     - the booking date.
     * @param bookingTime     - the booking time.
     * @param numOfGuests     - the number of guests for the booking.
     * @param tablePreference - the table preference of the customer.
     * @param bookingLength   - the length of booking time.
     * @return a new booking based on the given information.
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public Booking createNewBooking(
            int customerId,
            LocalDate bookingDate,
            LocalTime bookingTime,
            int numOfGuests,
            Table tablePreference,
            int bookingLength
    ) throws TextFileNotFoundException {

        try {

            return new Booking(
                    getNewBookingId(),
                    customerId,
                    bookingDate,
                    bookingTime,
                    numOfGuests,
                    tablePreference,
                    bookingLength
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets a new booking id.
     *
     * @return a unique id for the new booking.
     */
    public int getNewBookingId() {

        List<Booking> allUniqueBookings = getAllUniqueBookings();
        int lastBookingId = -1;
        for (Booking booking : allUniqueBookings) {
            int currentBookingId = booking.getBookingId();
            if (lastBookingId < currentBookingId) {
                lastBookingId = currentBookingId;
            } else if (lastBookingId == -1) {
                lastBookingId = currentBookingId;
            }
        }
        if (lastBookingId > -1) {
            lastBookingId++;
        }
        return lastBookingId;
    }

    /**
     * This method creates a booking from an existing data or an edited data.
     *
     * @param booking       - the current booking being selected.
     * @param bookingDate   - the current/edited booking date.
     * @param bookingTime   - the current/edited booking time.
     * @param numOfGuests   - the current/edited number of guests.
     * @param table         - the current/edited table preference.
     * @param bookingLength - the current/edited booking length.
     * @return a booking with the updated data.
     */
    public Booking getCurrentBooking(
            Booking booking,
            LocalDate bookingDate,
            LocalTime bookingTime,
            int numOfGuests,
            Table table,
            int bookingLength
    ) {

        int bookingId = booking.getBookingId();
        Customer customer = booking.getCustomer();
        List<Table> tablePreference = new ArrayList<>(
                Arrays.asList(table)
        );
        String bookingStatus = booking.getBookingStatus();
        return new Booking(
                bookingId,
                customer,
                bookingDate,
                bookingTime,
                numOfGuests,
                tablePreference,
                bookingLength,
                bookingStatus
        );
    }

    /**
     * This method adds a new booking to the database.
     *
     * @param newBooking - the booking that was created.
     * @throws TextFileNotFoundException - if text file is non-existent
     */
    public void addBookingToDatabase(
            Booking newBooking
    ) throws TextFileNotFoundException {
        String bookingId = String.valueOf(newBooking.getBookingId());
        String customerId = String.valueOf(newBooking.getCustomerId());
        String bookingDate = newBooking
                .getBookingDate()
                .format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String bookingTime = newBooking
                .getBookingTime()
                .format(DateTimeFormatter.ofPattern("H-m"));
        String numOfGuests = String.valueOf(newBooking.getNumOfGuests());
        String bookingLength =
                String.valueOf(newBooking.getBookingLengthInHour());
        String tablePreference =
                newBooking.getTablePreference().getFirst().getTableName();
        String bookingStatus = newBooking.getBookingStatus();

        List<String> newBookingForDatabase = new ArrayList<>(Arrays.asList(
                bookingId,
                customerId,
                bookingDate,
                bookingTime,
                numOfGuests,
                bookingLength,
                tablePreference,
                bookingStatus
        ));

        try {
            DataManager.appendDataToFile(
                    "BOOKINGS", newBookingForDatabase);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method edits booking details in the database.
     *
     * @param editedBooking - the booking with edited information.
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public void editBookingDataInDatabase(
            Booking editedBooking
    ) throws TextFileNotFoundException {
        int bookingId = editedBooking.getBookingId();
        String bookingDate = editedBooking
                .getBookingDate()
                .format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String bookingTime = editedBooking
                .getBookingTime()
                .format(DateTimeFormatter.ofPattern("H-m"));
        String numOfGuests = String.valueOf(editedBooking
                .getNumOfGuests());
        String tablePreference = String.valueOf(editedBooking
                .getTablePreference().getFirst());
        String bookingLength = String.valueOf(editedBooking
                .getBookingLengthInHour());

        try {
            DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                    "bookingDate", bookingDate);
            DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                    "bookingTime", bookingTime);
            DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                    "numOfGuests", numOfGuests);
            DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                    "tablePreference", tablePreference);
            DataManager.editColumnDataByUniqueId("BOOKINGS", bookingId,
                    "bookingLength", bookingLength);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets all the booking that are pending for approval.
     *
     * @param data - the table view list containing the bookings for display.
     */
    public void getPendingBookingData(
            ObservableList<Booking> data
    ) {

        List<Booking> bookingData = getAllUniqueBookings();
        for (Booking booking : bookingData) {

            if (booking
                    .getBookingStatus()
                    .equalsIgnoreCase("pending-approval")) {
                data.add(booking);
            }
        }
    }

    /**
     * This method gets all the booking data by customer id.
     *
     * @param data   - the table view list to display any bookings if found.
     * @param userId - the customer id.
     */
    public void getBookingDataByUserId(
            ObservableList<Booking> data,
            int userId
    ) {

        List<Booking> bookingData = getAllUniqueBookings();
        for (Booking booking : bookingData) {

            if (booking
                    .getCustomerId() == userId) {
                data.add(booking);
            }
        }
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

    /**
     * This method gets table data from the database.
     *
     * @return - the list of tables in the restaurant.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public List<Table> getTableDataFromDatabase()
            throws TextFileNotFoundException {

        try {
            List<Table> tableList = new ArrayList<>();
            List<String> tableData = null;
            tableData = DataManager.allDataFromFile("TABLES");
            for (String table : tableData) {
                List<String> tableDetails = List.of(table.split(","));
                String tableName = tableDetails.get(
                        DataFileStructure.getIndexByColName(
                                "TABLES",
                                "tableName"
                        )
                );
                int numOfSeats = Integer.parseInt(tableDetails.get(
                        DataFileStructure.getIndexByColName(
                                "TABLES",
                                "numOfSeats"
                        )
                ));
                tableList.add(new Table(
                        tableName,
                        numOfSeats
                ));
            }
            return tableList;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method populates a table choice box for users to choose from.
     *
     * @param tableChoiceBox - the choice box to populate with tables.
     */
    public void updateTableChoiceBox(
            ChoiceBox<Table> tableChoiceBox
    ) {
        Set<Table> tableList = getSetOfTables();
        List<Table> unsortedTables = new ArrayList<>();
        for (Table table : tableList) {
            unsortedTables.add(table);
        }
        Collections.sort(
                unsortedTables,
                Comparator.comparing(Table::getNumberOfSeats)
        );
        tableChoiceBox.getItems().addAll(unsortedTables);
    }

    /**
     * This method updates the table choice box based on the number of guests.
     *
     * @param numOfGuests    - the number of guests selected.
     * @param tableChoiceBox - the choice box to be updated.
     */
    public void updateTablesBasedOnGuests(
            int numOfGuests,
            ChoiceBox<Table> tableChoiceBox
    ) {
        tableChoiceBox.getItems().clear();
        Set<Table> tableList = getSetOfTables();
        for (Table table : tableList) {
            if (table.getNumberOfSeats() == numOfGuests) {
                tableChoiceBox.getItems().add(table);
            }
        }
    }

    /**
     * This method populates a choice box with numbers of guests for
     * users to choose from.
     *
     * @param numOfGuestsChoiceBox - the choice box to be updated.
     */
    public void updateNumOfGuestsChoiceBox(
            ChoiceBox<Integer> numOfGuestsChoiceBox
    ) {
        List<Integer> uniqueNumOfGuests = new ArrayList<>();
        Set<Table> tableList = getSetOfTables();
        for (Table table : tableList) {
            if (!uniqueNumOfGuests.contains(
                    table.getNumberOfSeats()
            )) {
                uniqueNumOfGuests.add(
                        table.getNumberOfSeats()
                );
            }
        }
        Collections.sort(uniqueNumOfGuests);
        for (int numOfGuest : uniqueNumOfGuests) {
            numOfGuestsChoiceBox.getItems()
                    .add(numOfGuest);
        }
    }

    /**
     * This method populates a choice box with numbers of booking length
     * for users to choose from.
     *
     * @param bookingLengthChoiceBox - the choice box to be updated.
     */
    public void updateBookingLengthChoiceBox(
            ChoiceBox<Integer> bookingLengthChoiceBox
    ) {
        bookingLengthChoiceBox.getItems().clear();
        for (int i = 1; i < MAX_BOOKING_LENGTH; i++) {
            bookingLengthChoiceBox.getItems().add(i);
        }
    }

    /**
     * This method populates a choice box with available reservation times
     * of the restaurant.
     *
     * @param reservationTimeChoiceBox - the choice box to be updated.
     */
    public void updateReservationTimeChoiceBox(
            ChoiceBox<LocalTime> reservationTimeChoiceBox
    ) {
        // TODO magic
        reservationTimeChoiceBox.getItems().clear();
        for (LocalTime startTime = OPENING_TIME;
             startTime.compareTo(LATEST_BOOKING_TIME) <= 0;
             startTime = startTime.plusMinutes(30)) {
            reservationTimeChoiceBox
                    .getItems()
                    .add(startTime);
        }
    }

    /**
     * This method formats address for users to read.
     *
     * @param address - the formatted address taken from database.
     * @return an address with the correct format.
     */
    public String formatAddressToRead(String address) {
        return address.replaceAll(";", ",");
    }

}
