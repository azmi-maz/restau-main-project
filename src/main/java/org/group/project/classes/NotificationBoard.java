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

    /**
     * This constructor sets up the notification board and updates its data
     * from the database.
     *
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public NotificationBoard() throws TextFileNotFoundException {

        notificationList = new ArrayList<>();

        try {
            notificationList = getNotificationsFromDatabase();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * This method get the list of notifications.
     *
     * @return the list of the notifications.
     */
    public List<Notification> getNotificationList() {
        return notificationList;
    }


    /**
     * This method gets all the notifications from the database.
     *
     * @return the list of notifications.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
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

    /**
     * This method gets a notification from a string.
     *
     * @param notification the string that contains the notification data.
     * @return the notification with the updated data.
     */
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
            // This is needed to replace all ; to ,
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

    /**
     * This method gets the LocalDate from a given string.
     *
     * @param notificationDate - the notification date in string format.
     * @return the LocalDate of the notification date.
     */
    public LocalDate getLocalDateFromString(
            String notificationDate
    ) {
        List<String> notificationDateDetails =
                List.of(notificationDate.split("-"));
        return LocalDate.of(Integer.parseInt(notificationDateDetails.get(0)),
                Integer.parseInt(notificationDateDetails.get(1)),
                Integer.parseInt(notificationDateDetails.get(2)));
    }

    /**
     * This method gets the LocalTime from a given string.
     *
     * @param notificationTime - the notification time in string format.
     * @return the LocalTime of the notification time.
     */
    public LocalTime getLocalTimeFromString(
            String notificationTime
    ) {
        List<String> notificationTimeDetails =
                List.of(notificationTime.split("-"));
        return LocalTime.of(Integer.parseInt(notificationTimeDetails.get(0)),
                Integer.parseInt(notificationTimeDetails.get(1)));
    }

    /**
     * This method populates table view list with all the notifications
     * by user id.
     *
     * @param data   - the table view list to be updated.
     * @param userId - the specific user id.
     */
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

    /**
     * This method gets number of notifications unread by user id.
     *
     * @param userId - the specific user id.
     * @return the number of unread notifications.
     */
    public int getUnreadNotificationByUserId(
            int userId
    ) {

        int numOfUnreadNotification = 0;

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

    /**
     * This method updates the notification counter of unread notifications.
     *
     * @param numOfUnreadNotification - the number of unread notifications.
     * @param firstDigit              - the first digit of the number.
     * @param secondDigit             - the second digit of the number.
     * @param counterBox              - the HBox that contains the counter.
     */
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

    /**
     * This method gets a new unique id for a notification.
     *
     * @return a unique id.
     */
    public int getNewNotificationId() {

        Notification lastNotification = notificationList.getLast();
        int lastNotificationId = lastNotification.getNotificationId();
        lastNotificationId++;
        return lastNotificationId;

    }

    /**
     * This method creates a new notification.
     *
     * @param customerId       - the user id.
     * @param notificationType - the type of notification.
     * @return the new notification.
     */
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

    /**
     * This method adds a new notification to the database.
     *
     * @param newNotification - the new notification.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
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

        List<String> newNotificationForDatabase = new ArrayList<>(
                Arrays.asList(
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
     * This method formats message to transform any symbols compatible for
     * users to read.
     *
     * @param message - the formatted message taken from database.
     * @return a message with the correct format.
     */
    public String formatMessageToRead(String message) {
        return message.replaceAll(";", ",");
    }
}
