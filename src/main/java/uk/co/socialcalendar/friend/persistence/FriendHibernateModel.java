package uk.co.socialcalendar.friend.persistence;

import uk.co.socialcalendar.friend.entities.FriendStatus;
import uk.co.socialcalendar.friend.entities.Friend;

import javax.persistence.*;

@Entity
public class FriendHibernateModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private int friendId;

    @Column(name="REQUESTER_EMAIL")
    private String requesterEmail;

    @Column(name="BEFRIENDED_EMAIL")
    private String beFriendedEmail;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    public FriendHibernateModel(){}

    public FriendHibernateModel(Friend friend){
        this.beFriendedEmail = friend.getBeFriendedEmail();
        this.requesterEmail = friend.getRequesterEmail();
        this.status = friend.getStatus();
        this.friendId = friend.getFriendId();
    }

    public int getFriendId() {
        return friendId;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public String getBeFriendedEmail() {
        return beFriendedEmail;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public void setBeFriendedEmail(String beFriendedEmail) {
        this.beFriendedEmail = beFriendedEmail;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        FriendHibernateModel friendHibernateModel = (FriendHibernateModel) obj;

        if (! this.requesterEmail.equals(friendHibernateModel.getRequesterEmail())) return false;
        if (! this.beFriendedEmail.equals(friendHibernateModel.getBeFriendedEmail())) return false;
        if (! this.status.equals(friendHibernateModel.getStatus())) return false;

        return friendId == friendHibernateModel.getFriendId();
    }

    public int hashcode(){
        int hash = 7;
        hash = 31 * hash + friendId + requesterEmail.hashCode() + beFriendedEmail.hashCode() + status.hashCode();
        return hash;
    }

    public String toString(){
        return "friendId=" + this.friendId
                + " requester email=" + this.requesterEmail
                + " befriended email=" + this.beFriendedEmail
                + " status=" + this.status;

    }
}
