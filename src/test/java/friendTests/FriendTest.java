package friendTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;



public class FriendTest {
	public static final int FRIEND_ID_1 = 1;
	public static final String FRIEND_NAME_REQUESTER="FRIEND_REQUESTER_NAME";
	public static final String FRIEND_NAME_REQUESTEE="FRIEND_REQUESTEE_NAME";
	public static final String FRIEND_STATUS_PENDING="PENDING";
	public static final String FRIEND_STATUS_ACCEPTED="ACCEPTED";
	public static final String FRIEND_STATUS_DECLINED="DECLINED";
	public static final String FRIEND_STATUS_UNKNOWN="UNKNOWN";
	Friend friend = new Friend(FRIEND_NAME_REQUESTER, FRIEND_NAME_REQUESTEE, FriendStatus.PENDING);
	
	@Before
	public void setup(){
	}

	@Test
	public void testNoArgsConstructor(){
		friend=new Friend();
		assertTrue(friend instanceof Friend);		
	}
	
	@Test
	public void shouldCreateFriendInstance(){
		assertTrue(friend instanceof Friend);
	}
	
	@Test
	public void canCreateFriendWithPendingStatus(){
		Friend friend = new Friend(FRIEND_NAME_REQUESTER, FRIEND_NAME_REQUESTEE, FriendStatus.PENDING);
		assertEquals(FRIEND_NAME_REQUESTER,friend.getRequesterName());
		assertEquals(FRIEND_NAME_REQUESTEE,friend.getRequesteeName());
		assertEquals(FRIEND_STATUS_PENDING,friend.getStatusString());
	}

	@Test
	public void canCreateFriendWithAcceptedStatus(){
		Friend friend = new Friend(FRIEND_NAME_REQUESTER, FRIEND_NAME_REQUESTEE, FriendStatus.ACCEPTED);
		assertEquals(FRIEND_NAME_REQUESTER,friend.getRequesterName());
		assertEquals(FRIEND_NAME_REQUESTEE,friend.getRequesteeName());
		assertEquals(FRIEND_STATUS_ACCEPTED,friend.getStatusString());
	}

	@Test
	public void canCreateFriendWithDeclinedStatus(){
		Friend friend = new Friend(FRIEND_NAME_REQUESTER, FRIEND_NAME_REQUESTEE, FriendStatus.DECLINED);
		assertEquals(FRIEND_NAME_REQUESTER,friend.getRequesterName());
		assertEquals(FRIEND_NAME_REQUESTEE,friend.getRequesteeName());
		assertEquals(FRIEND_STATUS_DECLINED,friend.getStatusString());
	}

	@Test
	public void canSetRequesterName(){
		friend.setRequesterName(FRIEND_NAME_REQUESTER);
		assertEquals(FRIEND_NAME_REQUESTER,friend.getRequesterName());
	}
	
	@Test
	public void canSetRequesteeName(){
		friend.setRequesteeName(FRIEND_NAME_REQUESTEE);
		assertEquals(FRIEND_NAME_REQUESTEE,friend.getRequesteeName());		
	}
	
	@Test
	public void canSetStatusToPending(){
		friend.setStatus(FriendStatus.PENDING);
		assertEquals(FRIEND_STATUS_PENDING,friend.getStatusString());
	}

	@Test
	public void canSetStatusToAccepted(){
		friend.setStatus(FriendStatus.ACCEPTED);
		assertEquals(FRIEND_STATUS_ACCEPTED,friend.getStatusString());
	}
	
	@Test
	public void canSetStatusToDeclined(){
		friend.setStatus(FriendStatus.DECLINED);
		assertEquals(FRIEND_STATUS_DECLINED,friend.getStatusString());
	}
	
	@Test
	public void statusSetToUnknownIfNoStatusSpecified(){
		friend = new Friend();
		assertEquals(FRIEND_STATUS_UNKNOWN, friend.getStatusString());
	}

	@Test
	public void canSetFriendId(){
		friend = new Friend();
		friend.setFriendId(FRIEND_ID_1);
		assertEquals(FRIEND_ID_1, friend.getFriendId());
	}

}

