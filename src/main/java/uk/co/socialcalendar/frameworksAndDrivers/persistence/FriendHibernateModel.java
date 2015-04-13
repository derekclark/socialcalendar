package uk.co.socialcalendar.frameworksAndDrivers.persistence;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;

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

    public void setFriendId(int friendId) {
        this.friendId = friendId;
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


    public String toString(){
        return "friendId=" + this.friendId
                + " requester email=" + this.requesterEmail
                + " befriended email=" + this.beFriendedEmail
                + " status=" + this.status;

    }
}
