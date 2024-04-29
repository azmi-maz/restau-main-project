package org.group.project.classes;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles all notifications data.
 *
 * @author azmi_maz
 */
public class NotificationBoard {

    private List<Notification> notificationList;

    // TODO
    public NotificationBoard() throws TextFileNotFoundException {

        notificationList = new ArrayList<>();

        try {
            notificationList = getNotificationsFromDatabase();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    // TODO
    public List<Notification> getNotificationList() {
        return notificationList;
    }


    // TODO
    public List<Notification> getNotificationsFromDatabase()
            throws TextFileNotFoundException {

        try {
            List<Notification> notificationList = new ArrayList<>();
            List<String> allNotificationsFromDatabase = DataManager
                    .allDataFromFile("NOTIFICATION");

            for (String notification : allNotificationsFromDatabase) {
                notificationList.add(
                        getNotificationFromString(notification)
                );
            }
            return notificationList;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public Notification getNotificationFromString(
            String notification
    ) {
        List<String> notificationDetails = List.of(
                notification.split(","));

        // userId
        int currentUserId = Integer.parseInt(
                notificationDetails.get(DataFileStructure
                        .getIndexByColName("NOTIFICATION",
                                "userId")));

        // notification id
        int notifcationid = Integer.parseInt(
                notificationDetails.get(DataFileStructure
                        .getIndexColOfUniqueId("NOTIFICATION")));


        // notificationDate
        LocalDate notificationDate = getLocalDateFromString(
                notificationDetails.get(DataFileStructure
                        .getIndexByColName("NOTIFICATION",
                                "notificationDate")
                ));

        // notificationTime
        LocalTime notificationTime = getLocalTimeFromString(
                notificationDetails.get(DataFileStructure
                        .getIndexByColName("NOTIFICATION",
                                "notificationTime")
                ));

        // notificationType
        String notificationType = notificationDetails.get(
                DataFileStructure.getIndexByColName("NOTIFICATION",
                        "notificationType"));

        // readStatus
        boolean readStatus = Boolean.parseBoolean(
                notificationDetails.get(DataFileStructure
                        .getIndexByColName("NOTIFICATION",
                                "readStatus")));

        // messageBody
        String messageBody = notificationDetails.get(
                DataFileStructure.getIndexByColName("NOTIFICATION",
                        "messageBody"));

        if (notificationType.equalsIgnoreCase("booking")) {
            // TODO this is needed to replace the ; to ,
            messageBody = formatMessageToRead(messageBody);
        }

        return new Notification(
                notifcationid,
                currentUserId,
                notificationDate,
                notificationTime,
                notificationType,
                messageBody,
                readStatus
        );
    }

    // TODO
    public LocalDate getLocalDateFromString(
            String notificationDate
    ) {
        List<String> notificationDateDetails =
                List.of(notificationDate.split("-"));
        return LocalDate.of(Integer.parseInt(notificationDateDetails.get(0)),
                Integer.parseInt(notificationDateDetails.get(1)),
                Integer.parseInt(notificationDateDetails.get(2)));
    }

    // TODO
    public LocalTime getLocalTimeFromString(
            String notificationTime
    ) {
        List<String> notificationTimeDetails =
                List.of(notificationTime.split("-"));
        return LocalTime.of(Integer.parseInt(notificationTimeDetails.get(0)),
                Integer.parseInt(notificationTimeDetails.get(1)));
    }

    // TODO comment
    public void getNotificationDataByUserId(
            ObservableList<Notification> data,
            int userId
    ) {

        List<Notification> notificationData = getNotificationList();
        for (Notification notification : notificationData) {

            if (notification
                    .getUserId() == userId) {
                data.add(notification);
            }
        }
    }

    // TODO comment
    public int getUnreadNotificationByUserId(
            int userId
    ) {

        int numOfUnreadNotification = 0;

        // TODO to filter
        List<Notification> notificationData = getNotificationList();
        for (Notification notification : notificationData) {

            if (notification
                    .getUserId() == userId
                    && !notification.getReadStatus()) {
                numOfUnreadNotification++;
            }
        }
        return numOfUnreadNotification;
    }

    // TODO
    public void updatesNavbarCounter(
            int numOfUnreadNotification,
            Label firstDigit,
            Label secondDigit,
            HBox counterBox
    ) {
        if (numOfUnreadNotification == 0) {
            firstDigit.setText("");
            secondDigit.setText("");
            counterBox.getStyleClass().clear();
        } else if (numOfUnreadNotification > 0
                && numOfUnreadNotification <= 9) {
            firstDigit.setText(String.valueOf(numOfUnreadNotification));
            secondDigit.setText("");
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        } else if (numOfUnreadNotification > 9
                && numOfUnreadNotification <= 99) {
            String count = String.valueOf(numOfUnreadNotification);
            firstDigit.setText(String.valueOf(count.charAt(0)));
            secondDigit.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        }
    }

    // TODO
    public int getNewNotificationId() {

        Notification lastNotification = notificationList.getLast();
        int lastNotificationId = lastNotification.getNotificationId();
        lastNotificationId++;
        return lastNotificationId;

    }

    // TODO
    public Notification createNewNotification(
            int customerId,
            String notificationType
    ) {
        return new Notification(
                getNewNotificationId(),
                customerId,
                notificationType
        );
    }

    // TODO
    public void addNotificationToDatabase(
            Notification newNotification
    ) throws TextFileNotFoundException {
        String notificationId = String.valueOf(
                newNotification.getNotificationId());
        String customerId = String.valueOf(
                newNotification.getUserId()
        );
        String notificationDate = newNotification
                .getNotificationDateForDatabase();
        String notificationTime = newNotification
                .getNotificationTimeForDatabase();
        String notificationType = newNotification
                .getNotificationType();
        String readStatus = String.valueOf(newNotification
                .getReadStatus());
        String messageBody = newNotification
                .getMessageBody();

        List<String> newNotificationForDatabase = new ArrayList<>(Arrays.asList(
                notificationId,
                customerId,
                notificationDate,
                notificationTime,
                notificationType,
                readStatus,
                messageBody
        ));

        try {
            DataManager.appendDataToFile("NOTIFICATION",
                    newNotificationForDatabase);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method formats messsage to transform any symbols compatible for
     * users to read.
     *
     * @param message - the formatted message taken from database.
     * @return a message with the correct format.
     */
    public String formatMessageToRead(String message) {
        return message.replaceAll(";", ",");
    }
}
