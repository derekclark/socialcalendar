package friend.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testSupport.InMemoryHibernateDB;
import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.friend.entities.FriendValidator;
import uk.co.socialcalendar.friend.persistence.FriendDAOHibernateImpl;
import uk.co.socialcalendar.friend.persistence.FriendHibernateModel;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.socialcalendar.friend.entities.FriendStatus.*;


public class FriendDAOHibernateImplTest {
    public static final String SOMEONE_ELSE3 = "user3";
    public static final String SOMEONE_ELSE4 = "user4";
    public static final String SOMEONE_ELSE1 = "user1";
    public static final String SOMEONE_ELSE2 = "user2";
    FriendDAOHibernateImpl friendDAOImpl;
    Friend friend;
    SessionFactory mockSessionFactory;
    Session testSession;
    public static final String ME = "me";
    private final static String BEFRIENDED_EMAIL = "befriendedEmail";
    private static final int NOT_SAVED = -1;

    
	@Before
	public void setup(){
        friendDAOImpl = new FriendDAOHibernateImpl();
        friendDAOImpl.setFriendValidator(new FriendValidator());
        friend = new Friend(ME, BEFRIENDED_EMAIL, ACCEPTED);
        setupTestDatabase();
	}

    @After
    public void teardown(){
        Transaction t = testSession.getTransaction();
        t.rollback();
    }

//    @AfterClass
//    public static void classTearDown(){
//        HibernateUtil.shutdown();
//    }

    public void setupTestDatabase(){
        getHibernateTestInstance();
        mockSessionFactory = mock(SessionFactory.class);
        friendDAOImpl.setSessionFactory(mockSessionFactory);
        when (mockSessionFactory.getCurrentSession()).thenReturn(testSession);
    }

    public void getHibernateTestInstance(){
        testSession = InMemoryHibernateDB.getSessionFactory().openSession();
        testSession.beginTransaction();
    }

    @Test
    public void canSaveFriend(){
        assertThat(friendDAOImpl.save(friend), greaterThan(0));
    }

    @Test
    public void willNotSaveFriendWithNullRequesterEmail(){
        Friend invalidFriend = new Friend(null, BEFRIENDED_EMAIL, ACCEPTED);
        assertEquals(NOT_SAVED, friendDAOImpl.save(invalidFriend));
    }

    @Test
    public void willNotSaveFriendWithEmptyRequesterEmail(){
        Friend invalidFriend = new Friend("", BEFRIENDED_EMAIL,ACCEPTED);
        assertEquals(NOT_SAVED, friendDAOImpl.save(invalidFriend));
    }

    @Test
    public void willNotSaveFriendWithNullBeFriendedEmail(){
        Friend invalidFriend = new Friend(ME, null, ACCEPTED);
        assertEquals(NOT_SAVED, friendDAOImpl.save(invalidFriend));
    }

    @Test
    public void willNotSaveFriendWithEmptyBeFriendedEmail(){
        Friend invalidFriend = new Friend(ME, "", ACCEPTED);
        assertEquals(NOT_SAVED, friendDAOImpl.save(invalidFriend));
    }

    @Test
    public void willNotSaveFriendWithInvalidStatus(){
        Friend invalidFriend = new Friend(ME, BEFRIENDED_EMAIL, UNKNOWN);
        assertEquals(NOT_SAVED, friendDAOImpl.save(invalidFriend));
    }

    @Test
    public void willReadFriend(){
        Friend expectedFriend = new Friend(ME, BEFRIENDED_EMAIL, DECLINED);
        expectedFriend.setFriendId(friendDAOImpl.save(expectedFriend));
        System.out.println(expectedFriend.getFriendId());
        Friend actual = friendDAOImpl.read(expectedFriend.getFriendId());
        assertEquals(expectedFriend, actual);
    }

    @Test
    public void canUpdateStatus(){
        friend.setFriendId(friendDAOImpl.save(friend));
        friendDAOImpl.updateStatus(friend.getFriendId(), DECLINED);
        Friend actualFriend = friendDAOImpl.read(friend.getFriendId());
        assertEquals(DECLINED, actualFriend.getStatus());
    }

    @Test
    public void getListOfMyAcceptedFriends(){
        List<Friend> expectedFriendsList = setExpectedFriendsList();
        saveFriendList(expectedFriendsList);
        assertEquals(expectedFriendsList, friendDAOImpl.getMyAcceptedFriends(ME));
    }

    private void saveFriendList(List<Friend> friendList){
        for (Friend f: friendList){
            f.setFriendId(friendDAOImpl.save(f));
        }
    }

    private List<Friend> setExpectedFriendsList(){
        List<Friend> expectedFriendsList = new ArrayList<Friend>();
        expectedFriendsList.addAll(getMyAcceptedFriendsWhenIAmOwner());
        expectedFriendsList.addAll(getMyAcceptedFriendsWhenIAmNotOwner());
        return expectedFriendsList;
    }

    private List<Friend> getMyAcceptedFriendsWhenIAmNotOwner() {
        List<Friend> myAcceptedFriendsWhenIAmNotOwner = new ArrayList<Friend>();
        myAcceptedFriendsWhenIAmNotOwner.add(new Friend(SOMEONE_ELSE3, ME, ACCEPTED));
        myAcceptedFriendsWhenIAmNotOwner.add(new Friend(SOMEONE_ELSE4, ME, ACCEPTED));
        return myAcceptedFriendsWhenIAmNotOwner;
    }

    private List<Friend> getMyAcceptedFriendsWhenIAmOwner() {
        List<Friend> myAcceptedFriendsWhenIAmOwner = new ArrayList<Friend>();
        myAcceptedFriendsWhenIAmOwner.add(new Friend(ME, SOMEONE_ELSE1, ACCEPTED));
        myAcceptedFriendsWhenIAmOwner.add(new Friend(ME, SOMEONE_ELSE2, ACCEPTED));
        return myAcceptedFriendsWhenIAmOwner;
    }

    @Test
    public void shouldGetListOfMyFriendRequestsMadeByMe(){
        List<Friend> myFriendRequests = getListOfMyFriendRequestsMadeByMe();
        saveFriendList(myFriendRequests);
        assertEquals(myFriendRequests, friendDAOImpl.getFriendRequestsMadeByMe(ME));
    }

    @Test
    public void shouldGetListOfMyFriendInvitations(){
        List<Friend> myFriendInvites = getListOfMyFriendInvites();
        saveFriendList(myFriendInvites);
        assertEquals(myFriendInvites, friendDAOImpl.getFriendRequestsMadeOnMe(ME));
    }

    private List<Friend> getListOfMyFriendInvites() {
        List<Friend> myFriendInvites = new ArrayList<Friend>();
        myFriendInvites.add(new Friend(SOMEONE_ELSE1, ME, PENDING));
        myFriendInvites.add(new Friend(FriendDAOHibernateImplTest.SOMEONE_ELSE2, ME, PENDING));
        return myFriendInvites;
    }

    private List<Friend> getListOfMyFriendRequestsMadeByMe() {
        List<Friend> myFriendRequests = new ArrayList<Friend>();
        myFriendRequests.add(new Friend(ME, SOMEONE_ELSE1, PENDING));
        myFriendRequests.add(new Friend(ME, FriendDAOHibernateImplTest.SOMEONE_ELSE2, PENDING));
        return myFriendRequests;
    }

    @Test
    public void canConvertFriendToFriendHibernateModel(){
        FriendHibernateModel expected = new FriendHibernateModel(friend);
        FriendHibernateModel actual = friendDAOImpl.convertToFriendHibernateModel(friend);
        assertEquals(expected,actual);
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
        friend = new Friend(1, ME, SOMEONE_ELSE1, PENDING);
        friendDAOImpl.save(friend);
        assertTrue(friendDAOImpl.friendshipExists(ME, SOMEONE_ELSE1));
    }

    @Test
    public void shouldReturnTrueIfFriendshipExistsWhenIAmNotOwnerOfFriendship(){
        friend = new Friend(1, SOMEONE_ELSE1, ME, PENDING);
        friendDAOImpl.save(friend);
        assertTrue(friendDAOImpl.friendshipExists(ME, SOMEONE_ELSE1));
    }

    @Test
    public void shouldReturnFalseIfFriendshipDoesNotExist(){
        assertFalse(friendDAOImpl.friendshipExists(ME, SOMEONE_ELSE1));
    }
}
