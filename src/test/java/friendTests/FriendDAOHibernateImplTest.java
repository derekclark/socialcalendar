package friendTests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.frameworksAndDrivers.FriendDAOHibernateImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static uk.co.socialcalendar.entities.FriendStatus.ACCEPTED;
import static uk.co.socialcalendar.entities.FriendStatus.UNKNOWN;

public class FriendDAOHibernateImplTest {


    private static final int FRIEND_ID = 1;
    FriendDAOHibernateImpl friendDAOImpl;
    Friend friend;
    Friend emptyFriend;
	private final static String REQUESTER_EMAIL = "requesterEmail";
    private final static String BEFRIENDED_EMAIL = "befriendedEmail";

    SessionFactory mockSessionFactory;
    Session mockSession;
    
	@Before
	public void setup(){
        friendDAOImpl = new FriendDAOHibernateImpl();
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
        friend.setFriendId(FRIEND_ID);
        emptyFriend = new Friend();

        mockSessionFactory = mock(SessionFactory.class);
        friendDAOImpl.setSessionFactory(mockSessionFactory);
        mockSession = mock(Session.class);
        setupMocks();
	}

    public void setupMocks(){
        when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
    }

	@Test
	public void canCreateFriendDAOHibernateImplInstance(){
		assertTrue(friendDAOImpl instanceof FriendDAOHibernateImpl);
	}

    @Test
    public void canSaveFriend(){
        assertTrue(friendDAOImpl.save(friend));
    }

    @Test
    public void getMockedHibernateSession(){
        assertTrue(mockSession instanceof org.hibernate.Session);
    }


    @Test
    public void saveCallsSessionFactory(){
        friendDAOImpl.save(friend);
        verify(mockSession).save(friend);
    }

    @Test
    public void willNotSaveFriendWithZeroId(){
        emptyFriend.setFriendId(0);
        assertFalse(friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithNullRequesterEmail(){
        emptyFriend = new Friend(null, BEFRIENDED_EMAIL, ACCEPTED);
        emptyFriend.setFriendId(FRIEND_ID);
        assertFalse(friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithEmptyRequesterEmail(){
        emptyFriend = new Friend("", BEFRIENDED_EMAIL,ACCEPTED);
        emptyFriend.setFriendId(FRIEND_ID);
        assertFalse(friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithNullBeFriendedEmail(){
        emptyFriend = new Friend(REQUESTER_EMAIL, null, ACCEPTED);
        emptyFriend.setFriendId(FRIEND_ID);
        assertFalse(friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithEmptyBeFriendedEmail(){
        emptyFriend = new Friend(REQUESTER_EMAIL, "", ACCEPTED);
        emptyFriend.setFriendId(FRIEND_ID);
        assertFalse(friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithInvalidStatus(){
        emptyFriend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, UNKNOWN);
        emptyFriend.setFriendId(FRIEND_ID);
        assertFalse(friendDAOImpl.save(emptyFriend));
    }
    
//	@Test
//	public void testSettingQueryStringForGettingAnOwnersAcceptedFriends(){
//		String expectedString = "select friendEmail from Friend "
//				+ "where ownerEmail = " + REQUESTER_EMAIL + " and status = " + FriendStatus.ACCEPTED;
//		assertEquals(expectedString, friendDAOImpl.setQueryStringOwnersAcceptedFriends(REQUESTER_EMAIL) );
//	}
//
//	@Test
//	public void testSettingQueryStringForGettingAnOwneesAcceptedFriends(){
//		String expectedString = "select ownerEmail from Friends "
//				+ "where friendEmail = " + REQUESTER_EMAIL + " and status = " + FriendStatus.ACCEPTED;
//		assertEquals(expectedString, friendDAOImpl.setQueryStringOwneesAcceptedFriends(REQUESTER_EMAIL) );
//	}


}
