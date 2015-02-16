package friendTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.useCases.FriendDAO;
import uk.co.socialcalendar.entities.FriendStatus;

public class FriendDAOTest {
	Friend friend;
	Friend friend1, friend2, friend3, friend4, friend5, friend6, friend7, friend8;
	FriendDAO friendDAO;
	FriendStatus friendStatus;
	private final static int FRIEND_ID_EXISTENT = 1;
	private final static int FRIEND_ID_NON_EXISTENT = -1;
	private final static String FRIEND_NAME1 = "NAME1";
	private final static String FRIEND_NAME2 = "NAME2";
	private final static String FRIEND_NAME3 = "NAME3";
	private final static String FRIEND_NAME4 = "NAME4";
	private final static String FRIEND_NAME5 = "NAME5";
	private final static String FRIEND_NAME6 = "NAME6";
//	private final static String FRIEND_REQUESTEE = "FRIEND_REQUESTEE_EMAIL";
//	private final static String FRIEND_REQUESTER = "FRIEND_REQUESTER_EMAIL";
//	private final static String FRIEND_REQUESTER_PRESENT = "FRIEND_REQUESTER_EMAIL_EXISTS";
	private final static String FRIEND_REQUESTER_NOT_PRESENT = "FRIEND_REQUESTER_EMAIL_NOT_EXIST";
//	private static final String FRIEND_EMAIL_DOES_NOT_EXIST = "DOES NOT EXIST";
	
	@Before
	public void setup(){
//		pendingFriend = new Friend(FRIEND_REQUESTER, FRIEND_REQUESTEE,FriendStatus.PENDING);
//		nonExistentFriend = new Friend(FRIEND_REQUESTER, FRIEND_REQUESTEE,FriendStatus.PENDING);
//		existentFriend = new Friend(FRIEND_EMAIL_DOES_NOT_EXIST, FRIEND_EMAIL_DOES_NOT_EXIST,FriendStatus.PENDING);
//		existentFriend.setFriendId(FRIEND_ID_EXISTENT);
		friend = new Friend(FRIEND_NAME1,FRIEND_NAME2,FriendStatus.ACCEPTED);
		friend.setFriendId(1);
		friendDAO = new InMemoryFriendDAO();
	}
	
	public void saveFriends(){
		friend1 = new Friend(FRIEND_NAME1, FRIEND_NAME2,FriendStatus.ACCEPTED);
		friend1.setFriendId(1);
		friend2 = new Friend(FRIEND_NAME1, FRIEND_NAME3,FriendStatus.ACCEPTED);
		friend2.setFriendId(2);
		friend3 = new Friend(FRIEND_NAME1, FRIEND_NAME4,FriendStatus.PENDING);
		friend3.setFriendId(3);
		friend4 = new Friend(FRIEND_NAME2, FRIEND_NAME3,FriendStatus.ACCEPTED);
		friend4.setFriendId(4);
		friend5 = new Friend(FRIEND_NAME3, FRIEND_NAME4,FriendStatus.PENDING);
		friend5.setFriendId(5);
		friend6 = new Friend(FRIEND_NAME5, FRIEND_NAME1,FriendStatus.ACCEPTED);
		friend6.setFriendId(6);
		friend7 = new Friend(FRIEND_NAME5, FRIEND_NAME1,FriendStatus.PENDING);
		friend7.setFriendId(7);
		friend8 = new Friend(FRIEND_NAME1, FRIEND_NAME6,FriendStatus.PENDING);
		friend8.setFriendId(8);

		friendDAO.save(friend1);
		friendDAO.save(friend2);
		friendDAO.save(friend3);
		friendDAO.save(friend4);
		friendDAO.save(friend5);
		friendDAO.save(friend6);
		friendDAO.save(friend7);
		friendDAO.save(friend8);
	}

	@Test
	public void canSaveFriend(){
		assertTrue(friendDAO.save(friend));
	}
	
	@Test
	public void shouldNotSaveFriendIfRequesteeNameIsNull(){
		friend.setRequesteeName(null);
		assertFalse(friendDAO.save(friend));
	}
	
	@Test
	public void shouldNotSaveFriendIfRequesteeNameIsEmpty(){
		friend.setRequesteeName("");
		assertFalse(friendDAO.save(friend));
	}

	@Test
	public void shouldNotSaveFriendIfRequesterNameIsNull(){
		friend.setRequesterName("");
		assertFalse(friendDAO.save(friend));
	}

	@Test
	public void shouldNotSaveFriendIfRequesterNameIsEmpty(){
		friend.setRequesterName("");
		assertFalse(friendDAO.save(friend));
	}

	@Test
	public void shouldNotSaveFriendIfStatusIsUNKNOWN(){
		friend.setStatus(FriendStatus.UNKNOWN);
		assertFalse(friendDAO.save(friend));
	}
	
	@Test
	public void canReadFriend(){
		assertEquals(friend.getFriendId(), friendDAO.read(friend).getFriendId());
	}
	
	@Test
	public void shouldNotReadNonExistentFriend(){
		friend.setFriendId(FRIEND_ID_NON_EXISTENT);
		assertNull(friendDAO.read(friend));
	}

	@Test
	public void canUpdateFriend(){
		assertTrue(friendDAO.update(friend));
	}

	@Test
	public void returnSeveralConfirmedFriendsWhereUserIsFriendRequesterAndRequestee(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getListOfConfirmedFriendsByRequesterName(FRIEND_NAME1);
		actualFriendList.addAll(friendDAO.getListOfConfirmedFriendsByRequesteeName(FRIEND_NAME1));
		assertEquals(friend1.getFriendId(), actualFriendList.get(0).getFriendId());
		assertEquals(friend2.getFriendId(), actualFriendList.get(1).getFriendId());
		assertEquals(friend6.getFriendId(), actualFriendList.get(2).getFriendId());
		assertEquals(3,actualFriendList.size());
	}
	
	@Test
	public void returnNoConfirmedFriends(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getListOfConfirmedFriendsByRequesterName(FRIEND_REQUESTER_NOT_PRESENT);
		assertEquals(0, actualFriendList.size());
	}
	
	@Test
	public void returnSeveralPendingFriends(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getListOfPendingFriendsByRequesterName(FRIEND_NAME1);
		assertEquals(2, actualFriendList.size());
	}

	@Test
	public void returnNoPendingFriends(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getListOfPendingFriendsByRequesterName(FRIEND_REQUESTER_NOT_PRESENT);
		assertEquals(0, actualFriendList.size());
	}
	
	@Test
	public void shouldAcceptFriendRequest(){
		assertTrue(friendDAO.acceptFriend(friend1));
	}

	@Test
	public void shouldDeclineFriendRequest(){
		assertTrue(friendDAO.declineFriend(friend1));
	}

	@Test
	public void getFriendRequests(){
		saveFriends();
		List<Friend> expectedFriendList = new ArrayList<Friend>();
		expectedFriendList.add(friend7);

		List<Friend> actualFriendList = friendDAO.getFriendRequests(FRIEND_NAME1);

		assertEquals(1,actualFriendList.size());
		assertEquals(expectedFriendList, actualFriendList);
	}
}
