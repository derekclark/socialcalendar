package uk.co.socialcalendar.frameworksAndDrivers;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import javax.persistence.*;

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

    public FriendHibernateModel(Friend friend){
        this.beFriendedEmail = friend.getBeFriendedEmail();
        this.requesterEmail = friend.getRequesterEmail();
        this.status = friend.getStatus();
        this.friendId = friend.getFriendId();
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
}
