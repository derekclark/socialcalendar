package uk.co.socialcalendar.newsFeed;

import uk.co.socialcalendar.user.entities.User;

import java.util.Set;

public class NewsFeedLine {
    private int availabilityId;
    private String title;
    private String startDateTime;
    private String endDateTime;
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

    public NewsFeedLine(){}

    public NewsFeedLine(int availabilityId, String title, String startDateTime, String endDateTime,
                        String dateLine, String allDay, String ownerName, String ownerEmail, String ownerFacebookId,
                        String textLine, String myAcceptanceStatus, String myAvailabilityText, Set<User> acceptedUsers,
                        Set<User> declinedUsers, Set<User> tentativeUsers, Set<User> sharedUsers,
                        String url, String key, boolean hasResponded){
        this.setAvailabilityId(availabilityId);
        this.setTitle(title);
        this.setStartDateTime(startDateTime);
        this.setEndDateTime(endDateTime);
        this.setDateLine(dateLine);
        this.setAllDay(allDay);
        this.setOwnerName(ownerName);
        this.setOwnerEmail(ownerEmail);
        this.setOwnerFacebookId(ownerFacebookId);
        this.setTextLine(textLine);
        this.setMyAcceptanceStatus(myAcceptanceStatus);
        this.setMyAvailabilityText(myAvailabilityText);
        this.setAcceptedUsers(acceptedUsers);
        this.setDeclinedUsers(declinedUsers);
        this.setTentativeUsers(tentativeUsers);
        this.setSharedUsers(sharedUsers);
        this.setUrl(url);
        this.setKey(key);
        this.setHasResponded(hasResponded);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
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

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        NewsFeedLine newsFeedLine = (NewsFeedLine) obj;

        if (! this.ownerEmail.equals(newsFeedLine.getOwnerEmail())) return false;
        if (! this.ownerName.equals(newsFeedLine.getOwnerName())) return false;
        if (! this.title.equals(newsFeedLine.getTitle())) return false;
        if (! this.startDateTime.equals(newsFeedLine.getStartDateTime())) return false;
        if (! this.endDateTime.equals(newsFeedLine.getEndDateTime())) return false;


        return availabilityId == this.getAvailabilityId();
    }

    public int hashcode(){
        int hash = 7;
        hash = 31 * hash + availabilityId + ownerEmail.hashCode() + ownerName.hashCode()
                + title.hashCode() + startDateTime.hashCode() + endDateTime.hashCode();
        return hash;
    }


    public String toString(){
        return this.getAvailabilityId() + " "
                + this.getOwnerEmail() + " "
                + this.getTitle() + " "
                + this.getOwnerName() + " "
                + this.getAcceptedUsers() + " "
                + this.getAllDay() + " "
                + this.getDateLine() + " "
                + this.getDeclinedUsers() + " "
                + this.getEndDateTime() + " "
                + this.getKey() + " "
                + this.getMyAcceptanceStatus() + " "
                + this.getMyAvailabilityText() + " "
                + this.getOwnerFacebookId() + " "
                + this.getSharedUsers() + " "
                + this.getStartDateTime() + " "
                + this.getTentativeUsers() + " "
                + this.getTextLine() + " "
                + this.getUrl();
    }
}
