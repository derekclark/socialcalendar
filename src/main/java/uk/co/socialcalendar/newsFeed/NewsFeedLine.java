package uk.co.socialcalendar.newsFeed;

import org.joda.time.LocalDateTime;
import uk.co.socialcalendar.user.entities.User;

import java.util.Set;

public class NewsFeedLine {
    private int availabilityId;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String dateLine;
    private String allDay;
    private String ownerName;
    private String ownerEmail;
    private String ownerFacebookId;
    private String textLine;
    private String myAcceptanceStatus;
    private String myAvailabilityText;
    private Set<User> acceptedUsers;
    private Set<User> declinedUsers;
    private Set<User> tentativeUsers;
    private Set<User> sharedUsers;
    /*private Set<InvitedFriendsStatus> invitedFriendsStatus;*/
    private String url;
    private String key;
    /*private Message latestMessage;*/
    private boolean hasResponded;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDateLine() {
        return dateLine;
    }

    public void setDateLine(String dateLine) {
        this.dateLine = dateLine;
    }

    public String getAllDay() {
        return allDay;
    }

    public void setAllDay(String allDay) {
        this.allDay = allDay;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerFacebookId() {
        return ownerFacebookId;
    }

    public void setOwnerFacebookId(String ownerFacebookId) {
        this.ownerFacebookId = ownerFacebookId;
    }

    public String getTextLine() {
        return textLine;
    }

    public void setTextLine(String textLine) {
        this.textLine = textLine;
    }

    public String getMyAcceptanceStatus() {
        return myAcceptanceStatus;
    }

    public void setMyAcceptanceStatus(String myAcceptanceStatus) {
        this.myAcceptanceStatus = myAcceptanceStatus;
    }

    public String getMyAvailabilityText() {
        return myAvailabilityText;
    }

    public void setMyAvailabilityText(String myAvailabilityText) {
        this.myAvailabilityText = myAvailabilityText;
    }

    public Set<User> getAcceptedUsers() {
        return acceptedUsers;
    }

    public void setAcceptedUsers(Set<User> acceptedUsers) {
        this.acceptedUsers = acceptedUsers;
    }

    public Set<User> getDeclinedUsers() {
        return declinedUsers;
    }

    public void setDeclinedUsers(Set<User> declinedUsers) {
        this.declinedUsers = declinedUsers;
    }

    public Set<User> getTentativeUsers() {
        return tentativeUsers;
    }

    public void setTentativeUsers(Set<User> tentativeUsers) {
        this.tentativeUsers = tentativeUsers;
    }

    public Set<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(Set<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isHasResponded() {
        return hasResponded;
    }

    public void setHasResponded(boolean hasResponded) {
        this.hasResponded = hasResponded;
    }

    public int getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(int availabilityId) {
        this.availabilityId = availabilityId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
}
