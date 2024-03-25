package org.group.project.classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class handles file reading and writing to store/persist data in this
 * program.
 *
 * @author azmi_maz
 */
public class DataManager {

    public static void createFile() throws IOException {

        File myFile = new File(FileNames.DATA.getDataFile("USERS"));
        Scanner myReader = new Scanner(myFile);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] splitData = data.split(",");
            String username = splitData[2];
            System.out.println(username);
            if (username.equalsIgnoreCase("kyle.broflovski")){
                System.out.println("Hi, Kyle!");
                break;
            }
        }
        myReader.close();

        String fileName = FileNames.DATA.getDataFile("USERS");
        FileWriter myWriter = new FileWriter(fileName, true);
//        myWriter.write("eric, cartman, eric.cartman\n");
//        myWriter.write("stan, marsh, stan.marsh\n");
//        myWriter.write("kyle, broflovski, kyle.broflovski\n");
//        myWriter.write("kenny, mccormick, kenny.mccormick\n");
        myWriter.close();

    }

    public static void main(String[] args) throws IOException {
        createFile();
    }
}
