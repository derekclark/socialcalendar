package friend.persistence;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.InMemoryFriendDAO;
import uk.co.socialcalendar.useCases.friend.FriendDAO;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static uk.co.socialcalendar.entities.FriendStatus.*;

public class FriendDAOTest {
	Friend myAcceptedFriend1, myAcceptedFriend2, myPendingFriend1,
			unrelatedFriend1, unrelatedFriend2, myAcceptedFriend3, myPendingFriend2,
			myPendingFriend3, myPendingFriend4;
	FriendDAO friendDAO;
	private final static int FRIEND_ID_INVALID = -1;
	private final static String MY_EMAIL = "MY_EMAIL";
	private final static String FRIEND_EMAIL2 = "EMAIL2";
	private final static String FRIEND_EMAIL3 = "EMAIL3";
	private final static String FRIEND_EMAIL4 = "EMAIL4";
	private final static String FRIEND_EMAIL5 = "EMAIL5";
	private final static String FRIEND_EMAIL6 = "EMAIL6";
	private final static String FRIEND_EMAIL7 = "EMAIL7";
	private final static String FRIEND_EMAIL8 = "EMAIL8";
	private final static String INVALID_USER = "invalid user";
	private static final int NOT_SAVED = -1;
	private final static String VALID_EMAIL = "validEmail";

	@Before
	public void setup(){
		friendDAO = new InMemoryFriendDAO();
	}
	
	public void saveFriends(){
		createManyFriends();
		saveManyFriends();
	}

	private void saveManyFriends() {
		myAcceptedFriend1.setFriendId(friendDAO.save(myAcceptedFriend1));
		myAcceptedFriend2.setFriendId(friendDAO.save(myAcceptedFriend2));
		myPendingFriend1.setFriendId(friendDAO.save(myPendingFriend1));
		unrelatedFriend1.setFriendId(friendDAO.save(unrelatedFriend1));
		unrelatedFriend2.setFriendId(friendDAO.save(unrelatedFriend2));
		myAcceptedFriend3.setFriendId(friendDAO.save(myAcceptedFriend3));
		myPendingFriend2.setFriendId(friendDAO.save(myPendingFriend2));
		myPendingFriend3.setFriendId(friendDAO.save(myPendingFriend3));
		myPendingFriend4.setFriendId(friendDAO.save(myPendingFriend4));
	}

	private void createManyFriends() {
		myAcceptedFriend1 = new Friend(MY_EMAIL, FRIEND_EMAIL2,ACCEPTED);
		myAcceptedFriend2 = new Friend(MY_EMAIL, FRIEND_EMAIL3,ACCEPTED);
		myAcceptedFriend3 = new Friend(FRIEND_EMAIL5, MY_EMAIL,ACCEPTED);
		myPendingFriend1 = new Friend(MY_EMAIL, FRIEND_EMAIL4,PENDING);
		myPendingFriend2 = new Friend(FRIEND_EMAIL5, MY_EMAIL,PENDING);
		myPendingFriend3 = new Friend(MY_EMAIL, FRIEND_EMAIL6,PENDING);
		myPendingFriend4 = new Friend(FRIEND_EMAIL8, MY_EMAIL,PENDING);
		unrelatedFriend1 = new Friend(FRIEND_EMAIL2, FRIEND_EMAIL3,ACCEPTED);
		unrelatedFriend2 = new Friend(FRIEND_EMAIL3, FRIEND_EMAIL4,PENDING);
	}

	@Test
	public void canSaveFriend(){
		myAcceptedFriend1 = new Friend(VALID_EMAIL, VALID_EMAIL, ACCEPTED);
		assertThat(friendDAO.save(myAcceptedFriend1), greaterThan(0));
	}
	
	@Test
	public void shouldNotSaveFriendIfBeFriendedIsNull(){
		myAcceptedFriend1 = new Friend(VALID_EMAIL, null, ACCEPTED);
		assertEquals(NOT_SAVED, friendDAO.save(myAcceptedFriend1));
	}
	
	@Test
	public void shouldNotSaveFriendIfBeFriendedIsEmpty(){
		myAcceptedFriend1 = new Friend(VALID_EMAIL, "", ACCEPTED);
		assertEquals(NOT_SAVED, friendDAO.save(myAcceptedFriend1));
	}

	@Test
	public void shouldNotSaveFriendIfRequesterIsNull(){
		myAcceptedFriend1 = new Friend(null, VALID_EMAIL, ACCEPTED);
		assertEquals(NOT_SAVED, friendDAO.save(myAcceptedFriend1));
	}

	@Test
	public void shouldNotSaveFriendIfRequesterIsEmpty(){
		myAcceptedFriend1 = new Friend("", VALID_EMAIL, ACCEPTED);
		assertEquals(NOT_SAVED, friendDAO.save(myAcceptedFriend1));
	}

	@Test
	public void shouldNotSaveFriendIfStatusIsUNKNOWN(){
		myAcceptedFriend1 = new Friend(VALID_EMAIL, VALID_EMAIL, UNKNOWN);
		assertEquals(NOT_SAVED, friendDAO.save(myAcceptedFriend1));
	}
	
	@Test
	public void canReadFriend(){
		myAcceptedFriend1 = new Friend(VALID_EMAIL, VALID_EMAIL, ACCEPTED);
		myAcceptedFriend1.setFriendId(friendDAO.save(myAcceptedFriend1));

		int friendId = myAcceptedFriend1.getFriendId();
		Friend returnedFriend = friendDAO.read(friendId);
		assertEquals(myAcceptedFriend1.getFriendId(), returnedFriend.getFriendId());
	}
	
	@Test
	public void shouldNotReadNonExistentFriend(){
		myAcceptedFriend1 = new Friend(VALID_EMAIL, VALID_EMAIL, ACCEPTED);
		myAcceptedFriend1.setFriendId(FRIEND_ID_INVALID);
		assertNull(friendDAO.read(FRIEND_ID_INVALID));
	}

	@Test
	public void canUpdateFriendStatus(){
		saveFriends();
		friendDAO.updateStatus(myPendingFriend1.getFriendId(), DECLINED);
		Friend updatedFriend = friendDAO.read(myPendingFriend1.getFriendId());
		assertEquals(FriendStatus.DECLINED, updatedFriend.getStatus());
	}

	@Test
	public void returnMyAcceptedFriends(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getMyAcceptedFriends(MY_EMAIL);
		assertEquals(myAcceptedFriend1.getFriendId(), actualFriendList.get(0).getFriendId());
		assertEquals(myAcceptedFriend2.getFriendId(), actualFriendList.get(1).getFriendId());
		assertEquals(myAcceptedFriend3.getFriendId(), actualFriendList.get(2).getFriendId());
		assertEquals(3,actualFriendList.size());
	}
	
	@Test
	public void returnNoAcceptedFriends(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getMyAcceptedFriends(INVALID_USER);
		assertEquals(0, actualFriendList.size());
	}
	
	@Test
	public void returnSeveralFriendInvites(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getFriendRequestsMadeOnMe(MY_EMAIL);
		assertEquals(2, actualFriendList.size());
	}

	@Test
	public void returnNoFriendInvites(){
		saveFriends();
		List<Friend> actualFriendList = friendDAO.getFriendRequestsMadeOnMe(INVALID_USER);
		assertEquals(0, actualFriendList.size());
	}
	
	@Test
	public void shouldAcceptFriendRequest(){
		saveFriends();
		assertTrue(friendDAO.updateStatus(myPendingFriend1.getFriendId(), ACCEPTED));
	}

	@Test
	public void acceptingFriendRequestShouldSetFriendStatusToAccepted(){
		saveFriends();
		friendDAO.updateStatus(myPendingFriend1.getFriendId(), ACCEPTED);
		assertEquals(ACCEPTED, myPendingFriend1.getStatus());
	}

	@Test
	public void shouldDeclineFriendRequest(){
		saveFriends();
		assertTrue(friendDAO.updateStatus(myPendingFriend1.getFriendId(), DECLINED));
	}

	@Test
	public void decliningFriendRequestShouldSetFriendStatusToDeclined(){
		saveFriends();
		friendDAO.updateStatus(myPendingFriend1.getFriendId(),DECLINED);
		assertEquals(DECLINED, myPendingFriend1.getStatus());
	}

	@Test
	public void getFriendRequestsMadeOnMe(){
		saveFriends();
		List<Friend> expectedFriendList = new ArrayList<Friend>();
		expectedFriendList.add(myPendingFriend2);
		expectedFriendList.add(myPendingFriend4);

		List<Friend> actualFriendList = friendDAO.getFriendRequestsMadeOnMe(MY_EMAIL);

		assertEquals(2,actualFriendList.size());
		assertEquals(expectedFriendList, actualFriendList);
	}

	@Test
	public void getFriendRequestsMadeByMe(){
		saveFriends();
		List<Friend> expectedFriendList = new ArrayList<Friend>();
		expectedFriendList.add(myPendingFriend1);
		expectedFriendList.add(myPendingFriend3);

		List<Friend> actualFriendList = friendDAO.getFriendRequestsMadeByMe(MY_EMAIL);

		assertEquals(2,actualFriendList.size());
		assertEquals(expectedFriendList, actualFriendList);
	}

}
