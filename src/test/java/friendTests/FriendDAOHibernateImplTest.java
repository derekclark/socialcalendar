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

import java.util.ArrayList;
import java.util.List;

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
        String expectedString = queryStringGetMyFriendsWhereIAmOwner();
        assertEquals(expectedString, friendDAOImpl.queryStringForMyAcceptedFriendsWhereIAmOwner(REQUESTER_EMAIL) );
	}

    private String queryStringGetMyFriendsWhereIAmOwner() {
        return "select BEFRIENDED_EMAIL from FriendHibernateModel "
                    + "where REQUESTER_EMAIL = " + REQUESTER_EMAIL + " and STATUS = " + ACCEPTED;
    }

    @Test
    public void testSettingQueryStringForGettingMyAcceptedFriendsWhereIAmNotOwner(){
        String expectedString = queryStringGetMyFriendsWhereIAmNotOwner();
        assertEquals(expectedString, friendDAOImpl.queryStringForMyAcceptedFriendsWhereIAmNotOwner(REQUESTER_EMAIL) );
    }

    private String queryStringGetMyFriendsWhereIAmNotOwner() {
        return "select REQUESTER_EMAIL from FriendHibernateModel "
                    + "where BEFRIENDED_EMAIL = " + REQUESTER_EMAIL + " and STATUS = " + ACCEPTED;
    }

    @Test
    public void getListOfMyAcceptedFriends(){
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        List<Friend> myAcceptedFriendsWhenIAmOwner = getMyAcceptedFriendsWhenIAmOwner();
        List<Friend> myAcceptedFriendsWhenIAmNotOwner = getMyAcceptedFriendsWhenIAmNotOwner();
        List<Friend> expectedFriendsList = new ArrayList<Friend>();
        expectedFriendsList.addAll(myAcceptedFriendsWhenIAmOwner);
        expectedFriendsList.addAll(myAcceptedFriendsWhenIAmNotOwner);

        when(mockQuery.list()).thenReturn(myAcceptedFriendsWhenIAmOwner).thenReturn(myAcceptedFriendsWhenIAmNotOwner);

        assertEquals(expectedFriendsList, friendDAOImpl.getMyAcceptedFriends(REQUESTER_EMAIL));
    }

    private List<Friend> getMyAcceptedFriendsWhenIAmNotOwner() {
        List<Friend> myAcceptedFriendsWhenIAmNotOwner = new ArrayList<Friend>();
        myAcceptedFriendsWhenIAmNotOwner.add(new Friend("user3", REQUESTER_EMAIL, ACCEPTED));
        myAcceptedFriendsWhenIAmNotOwner.add(new Friend("user4", REQUESTER_EMAIL, ACCEPTED));
        return myAcceptedFriendsWhenIAmNotOwner;
    }

    private List<Friend> getMyAcceptedFriendsWhenIAmOwner() {
        List<Friend> myAcceptedFriendsWhenIAmOwner = new ArrayList<Friend>();
        myAcceptedFriendsWhenIAmOwner.add(new Friend(REQUESTER_EMAIL, "user1", ACCEPTED));
        myAcceptedFriendsWhenIAmOwner.add(new Friend(REQUESTER_EMAIL, "user2", ACCEPTED));
        return myAcceptedFriendsWhenIAmOwner;
    }

    @Test
    public void queryStringForMyPendingFriends(){
        String expectedString = queryStringGetMyPendingFriends();

        assertEquals(expectedString, friendDAOImpl.queryStringForMyPendingFriends(REQUESTER_EMAIL));
    }

    private String queryStringGetMyPendingFriends() {
        return "from FriendHibernateModel "
                    + "where REQUESTER_EMAIL = " + REQUESTER_EMAIL + " and status = PENDING";
    }

    @Test
    public void getListOfMyPendingFriends(){
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);
        List<Friend> myPendingFriends = new ArrayList<Friend>();
        myPendingFriends.add(new Friend(REQUESTER_EMAIL, "user1", PENDING));
        myPendingFriends.add(new Friend(REQUESTER_EMAIL, "user2", PENDING));
        when(mockQuery.list()).thenReturn(myPendingFriends);

        assertEquals(myPendingFriends, friendDAOImpl.getListOfPendingFriendsByRequester(REQUESTER_EMAIL));
    }


}
