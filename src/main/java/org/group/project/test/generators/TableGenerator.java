package org.group.project.test.generators;

import org.group.project.classes.Table;

public class TableGenerator {

    public static Table createTableOfFourSeats1() {
        return new Table("Quatre Quartiers (4)", 4);
    }

    public static Table createTableOfFourSeats2() {
        return new Table("Convives Carré (4)", 4);
    }

    public static Table createTableOfFourSeats3() {
        return new Table("Groupe Grandeur (4)", 4);
    }
}
