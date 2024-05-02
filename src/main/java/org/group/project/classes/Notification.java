package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class handles notification data for users.
 *
 * @author azmi_maz
 */
public class Notification {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_FORMAT_DATABASE = "yyyy-M-d";
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String TIME_FORMAT_DATABASE = "H-m";
    private int notificationId;
    private int userId;
    private LocalDate notificationDate;
    private LocalTime notificationTime;
    private String notificationType;
    private boolean readStatus;
    private String messageBody;


    /**
     * The constructor that creates new notification for user.
     *
     * @param notificationId   - the unique id of the notification.
     * @param userId           - the user id of the notification.
     * @param notificationDate - the date of notification was created.
     * @param notificationTime - the time of notification was created.
     * @param notificationType - the type of notification.
     * @param messageBody      - the body of the notification.
     */
    public Notification(int notificationId,
                        int userId,
                        LocalDate notificationDate,
                        LocalTime notificationTime,
                        String notificationType,
                        String messageBody) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.notificationDate = notificationDate;
        this.notificationTime = notificationTime;
        this.notificationType = notificationType;
        this.messageBody = messageBody;
        readStatus = false;
    }

    /**
     * This constructor creates a notification based on updated data from the
     * database.
     *
     * @param notificationId   - the unique id of the notification.
     * @param userId           - the user id of the notification.
     * @param notificationDate - the date of notification was created.
     * @param notificationTime - the time of notification was created.
     * @param notificationType - the type of notification.
     * @param messageBody      - the body of the notification.
     * @param readStatus       - the read status of the notification.
     */
    public Notification(int notificationId,
                        int userId,
                        LocalDate notificationDate,
                        LocalTime notificationTime,
                        String notificationType,
                        String messageBody,
                        boolean readStatus) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.notificationDate = notificationDate;
        this.notificationTime = notificationTime;
        this.notificationType = notificationType;
        this.messageBody = messageBody;
        this.readStatus = readStatus;
    }

    /**
     * This constructor creates a notification with basic info.
     *
     * @param notificationId   - the unique id of the notification.
     * @param userId           - the user id of the notification.
     * @param notificationType - the type of notification.
     */
    public Notification(int notificationId,
                        int userId,
                        String notificationType) {
        this.notificationId = notificationId;
        this.userId = userId;
        notificationDate = LocalDate.now();
        notificationTime = LocalTime.now();
        this.notificationType = notificationType;
    }

    /**
     * Getter method to get the notification date
     *
     * @return - the date a notification was created.
     */
    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    /**
     * This method gets the notification date in dd/mm/yyyy format.
     *
     * @return the string of the notification date in the desired format.
     */
    public String getNotificationDateInFormat() {
        return getNotificationDate()
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * This method gets the notification date in database compatible format.
     *
     * @return the string of the notification date in the desired format.
     */
    public String getNotificationDateForDatabase() {
        return getNotificationDate()
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT_DATABASE));
    }

    /**
     * This method gets the notification time in hh:mm a format.
     *
     * @return the string of the notification time in the desired format.
     */
    public String getNotificationTimeInFormat() {
        return getNotificationTime()
                .format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * This method gets the notification time in database compatible format.
     *
     * @return the string of the notification time in the desired format.
     */
    public String getNotificationTimeForDatabase() {
        return getNotificationTime()
                .format(DateTimeFormatter.ofPattern(TIME_FORMAT_DATABASE));
    }

    /**
     * This method gets the user id of a notification.
     *
     * @return the user id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This method gets the read status of a notification.
     *
     * @return the read status.
     */
    public boolean getReadStatus() {
        return readStatus;
    }

    /**
     * This method gets the notification id of a notification.
     *
     * @return the id of a notification.
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * Getter method to get the notification time.
     *
     * @return - the time a notification was created.
     */
    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    /**
     * Getter method to get the notification body.
     *
     * @return - the notification message body.
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * Setter method to set the main message of a notification.
     *
     * @param messageBody - the main message.
     */
    public void setMessageBody(
            String messageBody
    ) {
        this.messageBody = messageBody;
    }

    /**
     * Getter method to get the notification type.
     *
     * @return - the type of notification.
     */
    public String getNotificationType() {
        return notificationType;
    }

}
