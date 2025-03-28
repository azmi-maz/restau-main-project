package org.group.project.classes;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.group.project.Main;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * This class represents tables for a customer to book.
 * 
 * @author azmi_maz
 */
public class Tables {

    private static final String TABLE_FILE = "TABLES";
    private List<Table> listOfTables;
    private static final String TABLE_NAME_COLUMN = "tableName";
    private static final String NUM_SEATS_COLUMN = "numOfSeats";
    private static final String IMAGE_URI_COLUMN = "base-imageurl";
    private static final String TABLE_STATUS_COLUMN = "table-status";
    private static final String HEIGHT_SUB_COLUMN = "height-sub";
    private static final String HEIGHT_DIV_COLUMN = "height-div";
    private static final String WIDTH_SUB_COLUMN = "width-sub";
    private static final String WIDTH_DIV_COLUMN = "width-div";
    private static final String COL_IDX_COLUMN = "colIdx";
    private static final String ROW_IDX_COLUMN = "rowIdx";
    private static final String COLSPAN_COLUMN = "colSpan";
    private static final String ROWSPAN_COLUMN = "rowSpan";
    private static final int MAX_HEIGHT = 100;
    private static final int MAX_WIDTH = 100;
    private static final String TABLE_NOT_AVAILABLE_STYLE = "tableNotAvailable";

    /**
     * This constructor prepares tables to handle customer bookings.
     * 
     * @throws TextFileNotFoundException
     */
    public Tables() throws TextFileNotFoundException {

        listOfTables = new ArrayList<>();

        try {
            listOfTables = getTablesFromDatabase();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retrieve tables from the database.
     * 
     * @return a list of existing tables.
     * @throws TextFileNotFoundException if the text file is not available.
     */
    public List<Table> getTablesFromDatabase() throws TextFileNotFoundException {
        try {
            List<Table> tableList = new ArrayList<>();
            List<String> allTablesFromDatabase = DataManager.allDataFromFile(TABLE_FILE);

            for (String table : allTablesFromDatabase) {
                tableList.add(
                        getTableFromString(table));
            }
            return tableList;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Extract table from its String data.
     * 
     * @param table - String data from the database.
     * @return a table created from the String data.
     */
    public Table getTableFromString(String table) {
        List<String> tableDetails = List.of(table.split(","));
        String tableName = tableDetails.get(
                DataFileStructure.getIndexByColName(
                        TABLE_FILE,
                        TABLE_NAME_COLUMN));
        int tableNumSeats = Integer.parseInt(tableDetails.get(
                DataFileStructure.getIndexByColName(
                        TABLE_FILE,
                        NUM_SEATS_COLUMN)));
        String url = tableDetails.get(
                DataFileStructure.getIndexByColName(TABLE_FILE,
                        IMAGE_URI_COLUMN));
        boolean tableStatus = Boolean.parseBoolean(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                TABLE_STATUS_COLUMN)));
        double heightSub = Double.parseDouble(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                HEIGHT_SUB_COLUMN)));
        double heightDiv = Double.parseDouble(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                HEIGHT_DIV_COLUMN)));
        double widthSub = Double.parseDouble(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                WIDTH_SUB_COLUMN)));
        double widthDiv = Double.parseDouble(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                WIDTH_DIV_COLUMN)));
        int colIdx = Integer.parseInt(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                COL_IDX_COLUMN)));
        int rowIdx = Integer.parseInt(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                ROW_IDX_COLUMN)));
        int colSpan = Integer.parseInt(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                COLSPAN_COLUMN)));
        int rowSpan = Integer.parseInt(
                tableDetails.get(DataFileStructure
                        .getIndexByColName(TABLE_FILE,
                                ROWSPAN_COLUMN)));

        return new Table(
                tableName,
                tableNumSeats,
                url,
                tableStatus,
                heightSub,
                heightDiv,
                widthSub,
                widthDiv,
                colIdx,
                rowIdx,
                colSpan,
                rowSpan);
    }

    /**
     * Get a table based on its table name.
     * 
     * @param tableName - the table name to search.
     * @return the desired table if found.
     */
    public Table getTableByTablename(String tableName) {

        for (Table table : listOfTables) {
            if (table.getTableName().equals(tableName)) {
                return table;
            }
        }

        return null;
    }

    /**
     * To populate the grid pane with the default tables.
     * 
     * @param gridPane - the gridpane to display the tables.
     * @throws URISyntaxException        if the image url does not exist.
     * @throws TextFileNotFoundException if the image url does not exist.
     */
    public void populateGridWithInitialTables(GridPane gridPane) throws URISyntaxException, TextFileNotFoundException {

        for (Table table : listOfTables) {
            putTableToGrid(
                    gridPane,
                    table);
        }

    }

    /**
     * To make the selected tables to be unavailable.
     * 
     * @param selectedTables - the tables to be updates.
     */
    public void flipTableToUnavailable(List<Table> selectedTables) {

        if (selectedTables.isEmpty()) {
            return;
        }

        for (Table currentTable : selectedTables) {
            String currentTableName = currentTable.getTableName();
            for (Table activeTable : listOfTables) {
                if (activeTable.getTableName().equalsIgnoreCase(currentTableName)
                        && activeTable.getAvailabilityStatus()) {
                    activeTable.flipAvailabilityStatus();
                }
            }
        }
    }

    /**
     * This resets all the tables to be available.
     */
    public void resetsAllTablesStatus() {

        for (Table table : listOfTables) {
            if (!table.getAvailabilityStatus()) {
                table.flipAvailabilityStatus();
                table.changeUrlToEmpty();
            }
        }
    }

    /**
     * This takes a list of bookings that is used to update the existing table
     * statues.
     * 
     * @param bookingsMade - existing bookings that matches with user desired
     *                     booking date, time and booking length.
     */
    public void updateTablesFromExistingBookings(List<Booking> bookingsMade) {

        for (Booking booking : bookingsMade) {
            List<Table> currentTables = booking.getTablePreference();
            flipTableToUnavailable(currentTables);
        }
    }

    /**
     * This populates the gridpane with tables during initialization or during a
     * page refresh.
     * 
     * @param gridPane - the gridpane used to display the images.
     * @throws URISyntaxException        if image url does not exist.
     * @throws TextFileNotFoundException if the text file does not exist.
     */
    public void populateTablesToGridPane(GridPane gridPane) throws URISyntaxException, TextFileNotFoundException {

        if (gridPane.getChildren() == null) {
            populateGridWithInitialTables(gridPane);
            return;
        } else {
            gridPane.getChildren().clear();
            for (Table table : listOfTables) {
                putTableToGrid(gridPane, table);
            }
        }
        return;
    }

    /**
     * This adds a table to the active gridpane.
     * 
     * @param gridPane     - the gridpane to display the images.
     * @param tableDetails - the table to be added to the gridpane.
     * @throws URISyntaxException - the image url does not exist.
     */
    public void putTableToGrid(
            GridPane gridPane,
            Table tableDetails) throws URISyntaxException {

        if (!tableDetails.getAvailabilityStatus()) {
            tableDetails.changeUrlToFilled();
        }

        Image image = new Image(Main.class.getResource(
                tableDetails.getImageUrl()).toURI().toString());

        ImageView imageView = new ImageView(image);
        imageView.fitHeightProperty().bind(
                gridPane.heightProperty().subtract(tableDetails.getHeightSub())
                        .divide(tableDetails.getHeightDiv()));
        imageView.fitWidthProperty().bind(
                gridPane.widthProperty().subtract(tableDetails.getWidthSub())
                        .divide(tableDetails.getWidthDiv()));
        imageView.setPreserveRatio(true);

        StackPane imageViewFirstStack = new StackPane();
        Label tableLabel = new Label(tableDetails.getTableName());

        if (!tableDetails.getAvailabilityStatus()) {
            tableLabel.getStyleClass().add(TABLE_NOT_AVAILABLE_STYLE);
        } else {
            tableLabel.getStyleClass().removeAll(TABLE_NOT_AVAILABLE_STYLE);
        }

        imageViewFirstStack.getChildren().add(imageView);

        imageViewFirstStack.getChildren().add(tableLabel);

        gridPane.add(imageViewFirstStack,
                tableDetails.getColIdx(),
                tableDetails.getRowIdx(),
                tableDetails.getColSpan(),
                tableDetails.getRowSpan());

        // This is used during table design
        // imageViewFirstStack.setStyle("-fx-border-color: red;");

        imageViewFirstStack.setMaxHeight(MAX_HEIGHT);
        imageViewFirstStack.setMaxWidth(MAX_WIDTH);

    }

}
