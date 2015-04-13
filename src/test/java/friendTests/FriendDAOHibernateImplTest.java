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
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendValidator;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
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
    private static final int NOT_SAVED = -1;

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
        when(mockSession.save(anyObject())).thenReturn(1);
        assertThat(friendDAOImpl.save(friend), greaterThan(0));
    }

    @Test
    public void getMockedHibernateSession(){
        assertTrue(mockSession instanceof org.hibernate.Session);
    }


    @Test
    public void saveCallsSessionFactory(){
        when(mockSession.save(anyObject())).thenReturn(1);
        friendDAOImpl.save(friend);
        verify(mockSession).save(anyObject());
    }

    @Test
    public void willNotSaveFriendWithNullRequesterEmail(){
        emptyFriend = new Friend(FRIEND_ID, null, BEFRIENDED_EMAIL, ACCEPTED);
        assertEquals(NOT_SAVED, friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithEmptyRequesterEmail(){
        emptyFriend = new Friend(FRIEND_ID, "", BEFRIENDED_EMAIL,ACCEPTED);
        assertEquals(NOT_SAVED, friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithNullBeFriendedEmail(){
        emptyFriend = new Friend(FRIEND_ID, REQUESTER_EMAIL, null, ACCEPTED);
        assertEquals(NOT_SAVED, friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithEmptyBeFriendedEmail(){
        emptyFriend = new Friend(FRIEND_ID, REQUESTER_EMAIL, "", ACCEPTED);
        assertEquals(NOT_SAVED, friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willNotSaveFriendWithInvalidStatus(){
        emptyFriend = new Friend(FRIEND_ID, REQUESTER_EMAIL, BEFRIENDED_EMAIL, UNKNOWN);
        assertEquals(NOT_SAVED, friendDAOImpl.save(emptyFriend));
    }

    @Test
    public void willReadFriend(){
        Friend expectedFriend = new Friend(FRIEND_ID, REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);

        FriendHibernateModel friendHibernateModel = new FriendHibernateModel(expectedFriend);
        when(mockSession.get(FriendHibernateModel.class, FRIEND_ID)).thenReturn(friendHibernateModel);
        Friend actual = friendDAOImpl.read(FRIEND_ID);
        assertEquals(expectedFriend, actual);
    }

    @Test
    public void canUpdateStatus(){
        friend = new Friend(FRIEND_ID, REQUESTER_EMAIL, BEFRIENDED_EMAIL, PENDING);
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
        List<Friend> expectedFriendsList = setExpectedFriendsList();

        List<FriendHibernateModel> modelIAmOwner = convertFriendListToModelList(getMyAcceptedFriendsWhenIAmOwner());
        List<FriendHibernateModel> modelIAmNotOwner = convertFriendListToModelList(getMyAcceptedFriendsWhenIAmNotOwner());
        when(mockQuery.list()).thenReturn(modelIAmOwner).thenReturn(modelIAmNotOwner);
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        assertEquals(expectedFriendsList, friendDAOImpl.getMyAcceptedFriends(REQUESTER_EMAIL));
    }

    private List<Friend> setExpectedFriendsList(){
        List<Friend> expectedFriendsList = new ArrayList<Friend>();
        expectedFriendsList.addAll(getMyAcceptedFriendsWhenIAmOwner());
        expectedFriendsList.addAll(getMyAcceptedFriendsWhenIAmNotOwner());
        return expectedFriendsList;
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
        myAcceptedFriendsWhenIAmNotOwner.add(new Friend(3, "user3", REQUESTER_EMAIL, ACCEPTED));
        myAcceptedFriendsWhenIAmNotOwner.add(new Friend(4, "user4", REQUESTER_EMAIL, ACCEPTED));
        return myAcceptedFriendsWhenIAmNotOwner;
    }

    private List<Friend> getMyAcceptedFriendsWhenIAmOwner() {
        List<Friend> myAcceptedFriendsWhenIAmOwner = new ArrayList<Friend>();
        myAcceptedFriendsWhenIAmOwner.add(new Friend(1, REQUESTER_EMAIL, "user1", ACCEPTED));
        myAcceptedFriendsWhenIAmOwner.add(new Friend(2, REQUESTER_EMAIL, "user2", ACCEPTED));
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

    @Test
    public void queryForMyRequestsMadeByMe(){
        String expectedString = queryStringGetMyRequestsMadeByMe();

        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);

        Query query = friendDAOImpl.queryStringForFriendRequestsMadeByMe(REQUESTER_EMAIL);
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

    private String queryStringGetMyRequestsMadeByMe() {
        return "from FriendHibernateModel "
                + "where REQUESTER_EMAIL = :email and STATUS = :status";
    }

    @Test
    public void shouldGetListOfMyFriendRequestsMadeByMe(){
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);
        List<Friend> myFriendRequests = getListOfMyFriendRequestsMadeByMe();
        List<FriendHibernateModel> myFriendRequestsModel = convertFriendListToModelList(myFriendRequests);
        when(mockQuery.list()).thenReturn(myFriendRequestsModel);

        assertEquals(myFriendRequests, friendDAOImpl.getFriendRequestsMadeByMe(REQUESTER_EMAIL));
    }

    @Test
    public void shouldGetListOfMyFriendInvitations(){
        when(mockSessionFactory.getCurrentSession().createQuery(anyString())).thenReturn(mockQuery);
        List<Friend> myFriendInvites = getListOfMyFriendInvites();
        List<FriendHibernateModel> myFriendInvitesModel = convertFriendListToModelList(myFriendInvites);
        when(mockQuery.list()).thenReturn(myFriendInvitesModel);

        assertEquals(myFriendInvites, friendDAOImpl.getFriendRequestsMadeOnMe(REQUESTER_EMAIL));
    }

    private List<Friend> getListOfMyFriendInvites() {
        List<Friend> myFriendInvites = new ArrayList<Friend>();
        myFriendInvites.add(new Friend(REQUESTER_EMAIL, "user1", PENDING));
        myFriendInvites.add(new Friend(REQUESTER_EMAIL, "user2", PENDING));
        return myFriendInvites;
    }

    private List<Friend> getListOfMyFriendRequestsMadeByMe() {
        List<Friend> myFriendRequests = new ArrayList<Friend>();
        myFriendRequests.add(new Friend(REQUESTER_EMAIL, "user1", PENDING));
        myFriendRequests.add(new Friend(REQUESTER_EMAIL, "user2", PENDING));
        return myFriendRequests;
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

        assertTrue(friendDAOImpl.friendshipExists(email1, email2));
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

        assertTrue(friendDAOImpl.friendshipExists(email1, email2));
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
        assertFalse(friendDAOImpl.friendshipExists(email1, email2));
    }

}
