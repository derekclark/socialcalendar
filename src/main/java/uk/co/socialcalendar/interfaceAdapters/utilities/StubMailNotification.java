package uk.co.socialcalendar.interfaceAdapters.utilities;

public class StubMailNotification implements UserNotification{

    private int messagesSent=0;

    private String recipient, sender, subject, message;

    @Override
    public boolean notify(String recipient, String sender, String subject, String message) {
        messagesSent++;
        this.recipient = recipient;
        this.sender = sender;
        this.subject = subject;
        this.message = message;
        return false;
    }

    public int getMessagesSent() {
        return messagesSent;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

}
