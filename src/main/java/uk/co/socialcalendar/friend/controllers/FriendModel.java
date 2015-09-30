package uk.co.socialcalendar.friend.controllers;

import uk.co.socialcalendar.user.entities.User;

public class FriendModel {
    private int friendId;
    private String facebookId;
    private String email;
    private String name;
    private boolean hasFacebookId;
    private boolean joined;
    private String availabilityStatus;


    public FriendModel(){
        this.facebookId="";
        this.friendId=0;
        this.email="";
        this.name="";
        this.joined = false;
        this.availabilityStatus = "";
    }

    public FriendModel(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.facebookId = user.getFacebookId();
        this.hasFacebookId=false;
        this.joined = false;
        this.availabilityStatus = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getFriendId() {
        return friendId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasFacebookId() {
        return hasFacebookId;
    }

    public void setHasFacebookId(boolean hasFacebookId) {
        this.hasFacebookId = hasFacebookId;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        FriendModel model = (FriendModel) obj;
        if (! this.facebookId.equals(model.getFacebookId())) return false;
        if (! this.name.equals(model.getName())) return false;
        if (! this.email.equals(model.getEmail())) return false;


        return friendId == model.getFriendId();
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + friendId + name.hashCode() + facebookId.hashCode() + email.hashCode() + name.hashCode();
        return hash;
    }

    @Override
    public String toString(){
        return "friendId=" + friendId + " name=" + name + " email=" + email + " facebookId=" + facebookId + " friendId=" + friendId;
    }
}
