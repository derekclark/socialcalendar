package friendTests;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.FriendDAOHibernateImpl;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.FriendHibernateModel;
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

        setupMocks();
	}

    public void setupMocks(){
        mockSessionFactory = mock(SessionFactory.class);
        friendDAOImpl.setSessionFactory(mockSessionFactory);
        friendDAOImpl.setFriendValidator(friendValidator);
        mockSession = mock(Session.class);
        mockQuery = mock(Query.class);
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
        verify(mockSession).save(anyObject());
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

        FriendHibernateModel friendHibernateModel = new FriendHibernateModel(friend);
        when(mockSession.get(FriendHibernateModel.class, FRIEND_ID)).thenReturn(friendHibernateModel);
        Friend actual = friendDAOImpl.read(FRIEND_ID);
        assertEquals(friend.getFriendId(), actual.getFriendId());
        assertEquals(friend.getBeFriendedEmail(), actual.getBeFriendedEmail());
        assertEquals(friend.getRequesterEmail(), actual.getRequesterEmail());
        assertEquals(friend.getStatus(), actual.getStatus());
    }

    @Test
    public void canUpdateStatus(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, PENDING);
        friend.setFriendId(FRIEND_ID);
        FriendHibernateModel friendHibernateModel = new FriendHibernateModel(friend);
        when(mockSession.get(FriendHibernateModel.class, FRIEND_ID)).thenReturn(friendHibernateModel);

        friendDAOImpl.updateStatus(FRIEND_ID, DECLINED);

        ArgumentCaptor<FriendHibernateModel> argument = ArgumentCaptor.forClass(FriendHibernateModel.class);
        verify(mockSessionFactory.getCurrentSession()).update(argument.capture());
        assertEquals(DECLINED, argument.getValue().getStatus());
    }


	@Test
	public void testSettingQueryStringForGettingMyAcceptedFriendsWhereIAmOwner(){
        String expectedString = queryStringGetMyFriendsWhereIAmOwner();
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        Query query = friendDAOImpl.queryMyAcceptedFriendsWhereIAmOwner(REQUESTER_EMAIL);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(mockSessionFactory.getCurrentSession()).createQuery(argument.capture());
        assertEquals(expectedString, argument.getValue());

    }


    @Test
    public void testSettingQueryStringForGettingMyAcceptedFriendsWhereIAmNotOwner(){
        String expectedString = queryStringGetMyFriendsWhereIAmNotOwner();
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        Query query = friendDAOImpl.queryMyAcceptedFriendsWhereIAmNotOwner(REQUESTER_EMAIL);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(mockSessionFactory.getCurrentSession()).createQuery(argument.capture());
        assertEquals(expectedString, argument.getValue());


    }


    @Test
    public void getListOfMyAcceptedFriends(){
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        List<Friend> myAcceptedFriendsWhenIAmOwner = getMyAcceptedFriendsWhenIAmOwner();
        List<Friend> myAcceptedFriendsWhenIAmNotOwner = getMyAcceptedFriendsWhenIAmNotOwner();
        List<Friend> expectedFriendsList = new ArrayList<Friend>();
        expectedFriendsList.addAll(myAcceptedFriendsWhenIAmOwner);
        expectedFriendsList.addAll(myAcceptedFriendsWhenIAmNotOwner);

        List<FriendHibernateModel> modelIAmOwner = convertFriendListToModelList(myAcceptedFriendsWhenIAmOwner);
        List<FriendHibernateModel> modelIAmNotOwner = convertFriendListToModelList(myAcceptedFriendsWhenIAmOwner);
        when(mockQuery.list()).thenReturn(modelIAmOwner).thenReturn(modelIAmNotOwner);

        assertEquals(expectedFriendsList, friendDAOImpl.getMyAcceptedFriends(REQUESTER_EMAIL));
    }

    private List<FriendHibernateModel> convertFriendListToModelList(List<Friend> myAcceptedFriendsWhenIAmOwner) {
        List<FriendHibernateModel> modelIAmOwner = new ArrayList<FriendHibernateModel>();
        for (Friend friend: myAcceptedFriendsWhenIAmOwner){
            modelIAmOwner.add(new FriendHibernateModel(friend));
        }
        return modelIAmOwner;
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
    public void queryForMyPendingFriends(){
        String expectedString = queryStringGetMyFriendInvites();

        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        Query query = friendDAOImpl.queryStringForMyFriendInvites(REQUESTER_EMAIL);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(mockSessionFactory.getCurrentSession()).createQuery(argument.capture());
        assertEquals(expectedString, argument.getValue());
    }

    private String queryStringGetMyFriendsWhereIAmOwner() {
        return "from FriendHibernateModel "
                + "where REQUESTER_EMAIL = :email and STATUS = :status";
    }

    private String queryStringGetMyFriendsWhereIAmNotOwner() {
        return "from FriendHibernateModel "
                + "where BEFRIENDED_EMAIL = :email and STATUS = :status";
    }

    private String queryStringGetMyFriendInvites() {
        return "from FriendHibernateModel "
                    + "where BEFRIENDED_EMAIL = :email and STATUS = :status";
    }

    @Test
    public void shouldGetListOfMyFriendInvitations(){
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);
        List<Friend> myFriendInvites = getListOfMyFriendInvites();
        when(mockQuery.list()).thenReturn(myFriendInvites);

        assertEquals(myFriendInvites, friendDAOImpl.getMyFriendInvites(REQUESTER_EMAIL));
    }

    private List<Friend> getListOfMyFriendInvites() {
        List<Friend> myFriendInvites = new ArrayList<Friend>();
        myFriendInvites.add(new Friend(REQUESTER_EMAIL, "user1", PENDING));
        myFriendInvites.add(new Friend(REQUESTER_EMAIL, "user2", PENDING));
        return myFriendInvites;
    }

    @Test
    public void canConvertFriendToFriendHibernateModel(){
        FriendHibernateModel expected = new FriendHibernateModel(friend);

        FriendHibernateModel actual = friendDAOImpl.convertToFriendHibernateModel(friend);
        assertEquals(expected.getFriendId(), actual.getFriendId());
        assertEquals(expected.getBeFriendedEmail(), actual.getBeFriendedEmail());
        assertEquals(expected.getRequesterEmail(), actual.getRequesterEmail());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    public void canConvertFriendHibernateModelToFriend(){
        FriendHibernateModel expected = new FriendHibernateModel(friend);

        Friend actual = friendDAOImpl.convertToFriend(expected);
        assertEquals(actual.getStatus(),expected.getStatus());
        assertEquals(actual.getRequesterEmail(),expected.getRequesterEmail());
        assertEquals(actual.getBeFriendedEmail(), expected.getBeFriendedEmail());

    }

    @Test
    public void shouldReturnTrueIfFriendshipExistsWhenIAmOwnerOfFriendship(){
        String email1 = "email1";
        String email2 = "email2";
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        List<FriendHibernateModel> emptyList = new ArrayList<FriendHibernateModel>();
        List<FriendHibernateModel> notEmptyList = new ArrayList<FriendHibernateModel>();
        notEmptyList.add(new FriendHibernateModel(friend));

        when(mockQuery.list()).thenReturn(notEmptyList).thenReturn(emptyList);

        assertTrue(friendDAOImpl.doesFriendshipExist(email1, email2));
    }

    @Test
    public void shouldReturnTrueIfFriendshipExistsWhenIAmNotOwnerOfFriendship(){
        String email1 = "email1";
        String email2 = "email2";
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        List<FriendHibernateModel> emptyList = new ArrayList<FriendHibernateModel>();
        List<FriendHibernateModel> notEmptyList = new ArrayList<FriendHibernateModel>();
        notEmptyList.add(new FriendHibernateModel(friend));

        when(mockQuery.list()).thenReturn(emptyList).thenReturn(notEmptyList);

        assertTrue(friendDAOImpl.doesFriendshipExist(email1, email2));
    }

    @Test
    public void shouldReturnFalseIfFriendshipDoesNotExist(){
        String email1 = "email1";
        String email2 = "email2";
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        List<FriendHibernateModel> emptyList = new ArrayList<FriendHibernateModel>();
        List<FriendHibernateModel> notEmptyList = new ArrayList<FriendHibernateModel>();
        notEmptyList.add(new FriendHibernateModel(friend));

        when(mockQuery.list()).thenReturn(emptyList);
        assertFalse(friendDAOImpl.doesFriendshipExist(email1, email2));
    }

}
