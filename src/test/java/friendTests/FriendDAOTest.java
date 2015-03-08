package friendTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.frameworksAndDrivers.InMemoryFriendDAO;
import uk.co.socialcalendar.useCases.FriendDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class FriendDAOTest {
	Friend friend;
	Friend friend1, friend2, friend3, friend4, friend5, friend6, friend7, friend8, friend9, pendingFriendRequest;
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
	private final static String FRIEND_NAME7 = "NAME7";
	private final static String FRIEND_NAME8 = "NAME8";
	private final static String FRIEND_REQUESTER_NOT_PRESENT = "FRIEND_REQUESTER_EMAIL_NOT_EXIST";

	@Before
	public void setup(){
		friend = createFriend(new Friend(FRIEND_NAME1,FRIEND_NAME2,FriendStatus.ACCEPTED),1);
		friendDAO = new InMemoryFriendDAO();
	}
	
	public void saveFriends(){
		createManyFriends();
		saveManyFriends();
	}

	private void saveManyFriends() {
		friendDAO.save(friend1);
		friendDAO.save(friend2);
		friendDAO.save(friend3);
		friendDAO.save(friend4);
		friendDAO.save(friend5);
		friendDAO.save(friend6);
		friendDAO.save(friend7);
		friendDAO.save(friend8);
		friendDAO.save(friend9);
		friendDAO.save(pendingFriendRequest);
	}

	private void createManyFriends() {
		friend1 = createFriend(new Friend(FRIEND_NAME1, FRIEND_NAME2, FriendStatus.ACCEPTED),1);
		friend2 = createFriend(new Friend(FRIEND_NAME1, FRIEND_NAME3,FriendStatus.ACCEPTED),2);
		friend3 = createFriend(new Friend(FRIEND_NAME1, FRIEND_NAME4,FriendStatus.PENDING),3);
		friend4 = createFriend(new Friend(FRIEND_NAME2, FRIEND_NAME3,FriendStatus.ACCEPTED),4);
		friend5 = createFriend(new Friend(FRIEND_NAME3, FRIEND_NAME4,FriendStatus.PENDING),5);
		friend6 = createFriend(new Friend(FRIEND_NAME5, FRIEND_NAME1,FriendStatus.ACCEPTED),6);
		friend7 = createFriend(new Friend(FRIEND_NAME5, FRIEND_NAME1,FriendStatus.PENDING),7);
		friend8 = createFriend(new Friend(FRIEND_NAME1, FRIEND_NAME6,FriendStatus.PENDING),8);
		friend9 = createFriend(new Friend(FRIEND_NAME8, FRIEND_NAME1,FriendStatus.PENDING),9);
		pendingFriendRequest = createFriend(new Friend(FRIEND_NAME1,FRIEND_NAME7,FriendStatus.PENDING),10);
	}

	private Friend createFriend(Friend friend, int friendId){
		Friend newFriend = new Friend(friend.getRequesterEmail(), friend.getBeFriendedEmail(), friend.getStatus());
		newFriend.setFriendId(friendId);
		return newFriend;
	}

	@Test
	public void canSaveFriend(){
		assertTrue(friendDAO.save(friend));
	}
	
	@Test
	public void shouldNotSaveFriendIfRequesteeNameIsNull(){
		friend.setBeFriendedEmail(null);
		assertFalse(friendDAO.save(friend));
	}
	
	@Test
	public void shouldNotSaveFriendIfRequesteeNameIsEmpty(){
		friend.setBeFriendedEmail("");
		assertFalse(friendDAO.save(friend));
	}

	@Test
	public void shouldNotSaveFriendIfRequesterNameIsNull(){
		friend.setRequesterEmail("");
		assertFalse(friendDAO.save(friend));
	}

	@Test
	public void shouldNotSaveFriendIfRequesterNameIsEmpty(){
		friend.setRequesterEmail("");
		assertFalse(friendDAO.save(friend));
	}

	@Test
	public void shouldNotSaveFriendIfStatusIsUNKNOWN(){
		friend.setStatus(FriendStatus.UNKNOWN);
		assertFalse(friendDAO.save(friend));
	}
	
	@Test
	public void canReadFriend(){
		saveFriends();
		int friendId = friend.getFriendId();
		Friend returnedFriend = friendDAO.read(friendId);
		assertEquals(friend.getFriendId(), returnedFriend.getFriendId());
	}
	
	@Test
	public void shouldNotReadNonExistentFriend(){
		friend.setFriendId(FRIEND_ID_NON_EXISTENT);
		assertNull(friendDAO.read(-1));
	}

	@Test
	public void canUpdateFriendStatus(){
		saveFriends();
		friendDAO.updateStatus(pendingFriendRequest.getFriendId(), FriendStatus.DECLINED);
		Friend updatedFriend = friendDAO.read(pendingFriendRequest.getFriendId());
		assertEquals(FriendStatus.DECLINED, updatedFriend.getStatus());
	}

	@Test
	public void returnMyAcceptedFriends(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getMyAcceptedFriends(FRIEND_NAME1);
		assertEquals(friend1.getFriendId(), actualFriendList.get(0).getFriendId());
		assertEquals(friend2.getFriendId(), actualFriendList.get(1).getFriendId());
		assertEquals(friend6.getFriendId(), actualFriendList.get(2).getFriendId());
		assertEquals(3,actualFriendList.size());
	}
	
	@Test
	public void returnNoAcceptedFriends(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getMyAcceptedFriends(FRIEND_REQUESTER_NOT_PRESENT);
		assertEquals(0, actualFriendList.size());
	}
	
	@Test
	public void returnSeveralFriendInvites(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getMyFriendInvites(FRIEND_NAME1);
		assertEquals(2, actualFriendList.size());
	}

	@Test
	public void returnNoFriendInvites(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getMyFriendInvites(FRIEND_REQUESTER_NOT_PRESENT);
		assertEquals(0, actualFriendList.size());
	}
	
	@Test
	public void shouldAcceptFriendRequest(){
		saveFriends();
		assertTrue(friendDAO.updateStatus(pendingFriendRequest.getFriendId(), FriendStatus.ACCEPTED));
	}

	@Test
	public void acceptingFriendRequestShouldSetFriendStatusToAccepted(){
		saveFriends();
		friendDAO.updateStatus(pendingFriendRequest.getFriendId(), FriendStatus.ACCEPTED);
		assertEquals(FriendStatus.ACCEPTED,pendingFriendRequest.getStatus());
	}

	@Test
	public void shouldDeclineFriendRequest(){
		saveFriends();
		assertTrue(friendDAO.updateStatus(pendingFriendRequest.getFriendId(), FriendStatus.DECLINED));
	}

	@Test
	public void decliningFriendRequestShouldSetFriendStatusToDeclined(){
		saveFriends();
		friendDAO.updateStatus(pendingFriendRequest.getFriendId(), FriendStatus.DECLINED);
		assertEquals(FriendStatus.DECLINED,pendingFriendRequest.getStatus());
	}

	@Test
	public void getMyFriendInvites(){
		saveFriends();
		List<Friend> expectedFriendList = new ArrayList<Friend>();
		expectedFriendList.add(friend7);
		expectedFriendList.add(friend9);

		List<Friend> actualFriendList = friendDAO.getMyFriendInvites(FRIEND_NAME1);

		assertEquals(2,actualFriendList.size());
		assertEquals(expectedFriendList, actualFriendList);
	}
}
