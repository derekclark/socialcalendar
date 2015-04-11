package friendTests;
import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.InMemoryFriendDAO;
import uk.co.socialcalendar.useCases.friend.FriendFacadeImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FriendFacadeImplTest {
	FriendFacadeImpl friendFacade = new FriendFacadeImpl();
	public static final String FRIEND_NAME_REQUESTER="REQUESTER NAME1";
	public static final String FRIEND_NAME_REQUESTEE="REQUESTEE NAME1";
	public static final String FRIEND_NAME1="NAME1";
	public static final String FRIEND_NAME2="NAME2";
	public static final String FRIEND_NAME3="NAME3";
	public static final String FRIEND_NAME4="NAME4";
	public static final String FRIEND_NAME5="NAME5";
	public static final String FRIEND_NAME6="NAME6";
	public static final String FRIEND_NAME7="NAME7";
	public static final String FRIEND_STATUS_PENDING="PENDING";
	public static final String FRIEND_STATUS_ACCEPTED="ACCEPTED";
	public static final String FRIEND_STATUS_DECLINED="DECLINED";

	Friend userFriendAccepted1;
	Friend userFriendAccepted2;
	Friend userFriendPending1;
	Friend userFriendPending2;
	Friend userFriendPending3;
	Friend userFriendDeclined;
	Friend nonRelatedFriend;

	InMemoryFriendDAO friendDAO;

	@Before
	public void setup(){
		userFriendAccepted1 = new Friend(FRIEND_NAME1,FRIEND_NAME3,FriendStatus.ACCEPTED);
		userFriendAccepted2 = new Friend(FRIEND_NAME6,FRIEND_NAME1,FriendStatus.ACCEPTED);
		userFriendPending1 = new Friend(FRIEND_NAME1,FRIEND_NAME4,FriendStatus.PENDING);
		userFriendPending2 = new Friend(FRIEND_NAME2,FRIEND_NAME1,FriendStatus.PENDING);
		userFriendPending3 = new Friend(FRIEND_NAME7,FRIEND_NAME1,FriendStatus.PENDING);
		userFriendDeclined = new Friend(FRIEND_NAME1,FRIEND_NAME2,FriendStatus.DECLINED);
		nonRelatedFriend = new Friend(FRIEND_NAME2,FRIEND_NAME3,FriendStatus.ACCEPTED);
		userFriendAccepted1.setFriendId(1);
		userFriendAccepted2.setFriendId(2);
		userFriendPending1.setFriendId(3);
		userFriendPending2.setFriendId(4);
		userFriendPending3.setFriendId(5);
		userFriendDeclined.setFriendId(6);

		friendDAO = new InMemoryFriendDAO();
		friendFacade.setFriendDAO(friendDAO);

		saveFriends();

	}

	private void saveFriends() {
		friendDAO.save(userFriendAccepted1);
		friendDAO.save(userFriendAccepted2);
		friendDAO.save(userFriendPending1);
		friendDAO.save(userFriendPending2);
		friendDAO.save(userFriendPending3);
		friendDAO.save(userFriendDeclined);
		friendDAO.save(nonRelatedFriend);
	}

	@Test
	public void shouldCreateFriendServiceInstance(){
		assertTrue(friendFacade instanceof FriendFacadeImpl);
	}
	
	@Test
	public void friendRequestCreatesAnewFriendObject(){
		int friendId=friendFacade.createFriendRequest
				(FRIEND_NAME_REQUESTER, FRIEND_NAME_REQUESTEE);
		assertThat(friendId, greaterThan(0));
	}

	@Test
	public void friendRequestCreatesFriendObjectWithPendingStatus(){
		int friendId=friendFacade.createFriendRequest
				(FRIEND_NAME_REQUESTER,FRIEND_NAME_REQUESTEE);
		Friend friend = friendDAO.read(friendId);
		assertEquals(FriendStatus.PENDING,friend.getStatus());
	}

	@Test
	public void declineFriendRequestSavesFriendUpdate(){
		assertTrue(friendFacade.declineFriendRequest(userFriendPending1.getFriendId()));
		assertEquals(FriendStatus.DECLINED, friendDAO.read(userFriendPending1.getFriendId()).getStatus());

	}

	@Test
	public void acceptFriendRequestSavesFriendUpdate(){
		assertTrue(friendFacade.acceptFriendRequest(userFriendPending1.getFriendId()));
		assertEquals(FriendStatus.ACCEPTED, friendDAO.read(userFriendPending1.getFriendId()).getStatus());

	}

	@Test
	public void canSaveValidFriend(){
		Friend friend = new Friend("name1", "name2", FriendStatus.ACCEPTED);
		assertTrue(friendFacade.saveFriend(friend));
	}

	@Test
	public void getConfirmedFriends(){
		List<Friend> expectedFriendsList = expectedFriendList();
		List<Friend> actualFriendList = friendFacade.getMyAcceptedFriends(FRIEND_NAME1);

		assertEquals(2, actualFriendList.size());
		assertTrue(actualFriendList.contains(userFriendAccepted1));
		assertTrue(actualFriendList.contains(userFriendAccepted2));
	}


	@Test
	public void getFriendRequests(){
		List<Friend> actualFriendRequests = friendFacade.getFriendRequests(FRIEND_NAME1);

		assertEquals(2, actualFriendRequests.size());
		assertEquals(actualFriendRequests.get(0), userFriendPending2);
		assertEquals(actualFriendRequests.get(1), userFriendPending3);
	}

	@Test
	public void getFriendRequestsMadeByMe(){
		List<Friend> actualFriendRequests = friendFacade.getFriendRequestsMadeByMe(FRIEND_NAME1);

		assertEquals(1, actualFriendRequests.size());
		assertEquals(actualFriendRequests.get(0), userFriendPending1);
	}

	private List<Friend> expectedFriendList() {
		List<Friend> friendsList = new ArrayList<Friend>();
		friendsList.add(userFriendAccepted1);
		friendsList.add(userFriendAccepted2);
		friendsList.add(userFriendPending1);
		friendsList.add(userFriendDeclined);
		friendsList.add(nonRelatedFriend);
		return friendsList;
	}

	@Test
	public void getFriendFromId(){
		Friend actualFriend = friendFacade.getFriend(userFriendAccepted1.getFriendId());
		assertEquals(actualFriend,userFriendAccepted1);
	}


}
