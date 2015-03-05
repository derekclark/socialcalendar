package friendTests;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.frameworksAndDrivers.FriendDAOHibernateImpl;
import uk.co.socialcalendar.interfaceAdapters.models.FriendValidator;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static uk.co.socialcalendar.entities.FriendStatus.*;


public class FriendDAOHibernateImplTest {


    private static final int FRIEND_ID = 1;
    FriendDAOHibernateImpl friendDAOImpl;
    Friend friend;
    Friend emptyFriend;
    FriendValidator friendValidator;
	private final static String REQUESTER_EMAIL = "requesterEmail";
    private final static String BEFRIENDED_EMAIL = "befriendedEmail";

    SessionFactory mockSessionFactory;
    Session mockSession;
    Query mockQuery;
    
	@Before
	public void setup(){
        friendDAOImpl = new FriendDAOHibernateImpl();
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
        friend.setFriendId(FRIEND_ID);
        emptyFriend = new Friend();
        friendValidator = new FriendValidator();

        mockSessionFactory = mock(SessionFactory.class);
        friendDAOImpl.setSessionFactory(mockSessionFactory);
        friendDAOImpl.setFriendValidator(friendValidator);
        mockSession = mock(Session.class);
        mockQuery = mock(Query.class);
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

    @Test
    public void willReadFriend(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
        friend.setFriendId(FRIEND_ID);

        when(mockSession.get(Friend.class, FRIEND_ID)).thenReturn(friend);
        assertEquals(friend, friendDAOImpl.read(FRIEND_ID));
    }

    @Test
    public void canUpdateStatus(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, PENDING);
        friend.setFriendId(FRIEND_ID);
        when(mockSession.get(Friend.class, FRIEND_ID)).thenReturn(friend);

        friendDAOImpl.updateStatus(FRIEND_ID, DECLINED);

        ArgumentCaptor<Friend> argument = ArgumentCaptor.forClass(Friend.class);
        verify(mockSessionFactory.getCurrentSession()).update(argument.capture());
        assertEquals(DECLINED, argument.getValue().getStatus());
    }


	@Test
	public void testSettingQueryStringForGettingMyAcceptedFriendsWhereIAmOwner(){
		String expectedString = "select beFriendedEmail from Friend "
				+ "where requesterEmail = " + REQUESTER_EMAIL + " and status = " + ACCEPTED;
		assertEquals(expectedString, friendDAOImpl.queryStringForMyAcceptedFriendsWhereIAmOwner(REQUESTER_EMAIL) );
	}

    @Test
    public void testSettingQueryStringForGettingMyAcceptedFriendsWhereIAmNotOwner(){
        String expectedString = "select requesterEmail from Friend "
                + "where beFriendedEmail = " + REQUESTER_EMAIL + " and status = " + ACCEPTED;
        assertEquals(expectedString, friendDAOImpl.queryStringForMyAcceptedFriendsWhereIAmNotOwner(REQUESTER_EMAIL) );
    }

    @Test
    public void getListOfMyAcceptedFriends(){
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);
        friendDAOImpl.getMyAcceptedFriends(REQUESTER_EMAIL);

        String expectedQueryStringGetMyFriendsWhereIAmOwner = "select beFriendedEmail from Friend "
                + "where requesterEmail = " + REQUESTER_EMAIL + " and status = ACCEPTED";

        String expectedQueryStringGetMyFriendsWhereIAmNotOwner = "select requesterEmail from Friend "
                + "where beFriendedEmail = " + REQUESTER_EMAIL + " and status = ACCEPTED";

        verify(mockSessionFactory.getCurrentSession(), times(1)).createQuery(expectedQueryStringGetMyFriendsWhereIAmOwner);
        verify(mockSessionFactory.getCurrentSession(), times(1)).createQuery(expectedQueryStringGetMyFriendsWhereIAmNotOwner);
    }

    @Test
    public void canUpdateStatusToAccepted(){

    }
//	@Test
//	public void testSettingQueryStringForGettingAnOwneesAcceptedFriends(){
//		String expectedString = "select ownerEmail from Friends "
//				+ "where friendEmail = " + REQUESTER_EMAIL + " and status = " + FriendStatus.ACCEPTED;
//		assertEquals(expectedString, friendDAOImpl.setQueryStringOwneesAcceptedFriends(REQUESTER_EMAIL) );
//	}


}
