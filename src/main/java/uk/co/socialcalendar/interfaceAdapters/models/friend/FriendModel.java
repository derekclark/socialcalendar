package uk.co.socialcalendar.interfaceAdapters.models.friend;

public class FriendModel {
    private int friendId;
    private String facebookId;
    private String email;
    private String name;
    private boolean hasFacebookId;

    public FriendModel(){
        this.facebookId="";
        this.friendId=0;
        this.email="";
        this.name="";
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

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        FriendModel friend = (FriendModel) obj;
        if (! this.facebookId.equals(friend.getFacebookId())) return false;
        if (! this.name.equals(friend.getName())) return false;
        if (! this.email.equals(friend.getEmail())) return false;


        return friendId == friend.getFriendId();
    }

    public int hashcode(){
        int hash = 7;
        hash = 31 * hash + friendId + name.hashCode() + facebookId.hashCode() + email.hashCode() + name.hashCode();
        return hash;
    }

}
