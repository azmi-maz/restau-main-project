package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class handles notification data for users.
 *
 * @author azmi_maz
 */
public class Notification {
    private LocalDate notificationDate;
    private LocalTime notificationTime;
    private String messageBody;
    private String notificationType;

    /**
     * The constructor that creates new notification for user.
     *
     * @param notificationDate - the date of notification was created.
     * @param notificationTime - the time of notification was created.
     * @param messageBody      - the body of the notification.
     * @param notificationType - the type of notification.
     */
    public Notification(LocalDate notificationDate,
                        LocalTime notificationTime, String messageBody,
                        String notificationType) {
        this.notificationDate = notificationDate;
        this.notificationTime = notificationTime;
        this.messageBody = messageBody;
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
    public String getNotificationBody() {
        return messageBody;
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
