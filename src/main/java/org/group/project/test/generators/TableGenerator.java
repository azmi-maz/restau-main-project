package org.group.project.test.generators;

import org.group.project.classes.Table;

public class TableGenerator {

    public static Table createTableOfFourSeats1() {
        return new Table("Table A", 4);
    }

    public static Table createTableOfFourSeats2() {
        return new Table("Table B", 4);
    }

    public static Table createTableOfFourSeats3() {
        return new Table("Table C", 4);
    }
}
