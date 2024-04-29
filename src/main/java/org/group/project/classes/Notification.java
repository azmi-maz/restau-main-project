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

    // TODO comment
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

    // TODO
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

    // TODO comment
    public String getNotificationDateInFormat() {
        return getNotificationDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // TODO comment
    public String getNotificationDateForDatabase() {
        return getNotificationDate()
                .format(DateTimeFormatter.ofPattern("yyyy-M-d"));
    }

    // TODO comment
    public String getNotificationTimeInFormat() {
        return getNotificationTime()
                .format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    // TODO comment
    public String getNotificationTimeForDatabase() {
        return getNotificationTime()
                .format(DateTimeFormatter.ofPattern("H-m"));
    }

    // TODO comment
    public int getUserId() {
        return userId;
    }

    // TODO comment
    public boolean getReadStatus() {
        return readStatus;
    }

    // TODO comment
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

    // TODO
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
