package uk.co.socialcalendar.notification;

public interface UserNotification {
    boolean notify(String recipient, String sender, String subject, String message);
}
