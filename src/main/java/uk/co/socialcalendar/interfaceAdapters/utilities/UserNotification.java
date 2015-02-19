package uk.co.socialcalendar.interfaceAdapters.utilities;

public interface UserNotification {
    boolean notify(String recipient, String sender, String subject, String message);
}
