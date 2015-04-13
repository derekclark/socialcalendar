package friendTests;


import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.FriendHibernateModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.co.socialcalendar.entities.FriendStatus.ACCEPTED;

public class FriendHibernateModelTest {

    FriendHibernateModel friendHibernateModel;

    @Before
    public void setup(){
        friendHibernateModel = new FriendHibernateModel();
    }

    @Test
    public void canCreateInstance(){
        assertTrue(friendHibernateModel instanceof FriendHibernateModel);
    }

    @Test
    public void canCreateInstanceFromFriendObject(){
        Friend friend = new Friend(1, "email", "email", FriendStatus.ACCEPTED);
        friendHibernateModel = new FriendHibernateModel(friend);
        assertEquals(1, friendHibernateModel.getFriendId());
        assertEquals("email", friendHibernateModel.getRequesterEmail());
        assertEquals("email", friendHibernateModel.getBeFriendedEmail());
        assertEquals(FriendStatus.ACCEPTED, friendHibernateModel.getStatus());
    }

    @Test
    public void canSetRequesterEmail(){
        friendHibernateModel.setRequesterEmail("email");
        assertEquals("email", friendHibernateModel.getRequesterEmail());
    }

    @Test
    public void canSetBefriendedEmail(){
        friendHibernateModel.setBeFriendedEmail("email");
        assertEquals("email", friendHibernateModel.getBeFriendedEmail());
    }

    @Test
    public void canSetStatus(){
        friendHibernateModel.setStatus(FriendStatus.ACCEPTED);
        assertEquals(FriendStatus.ACCEPTED, friendHibernateModel.getStatus());
    }

    @Test
    public void testToString(){
        Friend friend = new Friend(1, "email", "email", FriendStatus.ACCEPTED);
        friendHibernateModel = new FriendHibernateModel(friend);
        String expectedString = "friendId=1 requester email=" + "email" + " befriended email=" + "email"
                + " status=" + ACCEPTED.toString();
        assertEquals(expectedString, friendHibernateModel.toString());

    }

}
