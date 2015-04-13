package friendTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;

import static org.junit.Assert.*;
import static uk.co.socialcalendar.entities.FriendStatus.*;

public class FriendTest {
	public static final int FRIEND_ID = 1;
	public static final String REQUESTER_EMAIL ="FRIEND_REQUESTER_NAME";
	public static final String BEFRIENDED_EMAIL ="FRIEND_REQUESTEE_NAME";
	Friend friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, PENDING);
	
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
		Friend friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, PENDING);
		assertEquals(REQUESTER_EMAIL,friend.getRequesterEmail());
		assertEquals(BEFRIENDED_EMAIL,friend.getBeFriendedEmail());
		assertEquals(PENDING,friend.getStatus());
	}

	@Test
	public void canCreateFriendWithAcceptedStatus(){
		Friend friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL,ACCEPTED);
		assertEquals(REQUESTER_EMAIL,friend.getRequesterEmail());
		assertEquals(BEFRIENDED_EMAIL,friend.getBeFriendedEmail());
		assertEquals(ACCEPTED,friend.getStatus());
	}

	@Test
	public void canCreateFriendWithDeclinedStatus(){
		Friend friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, DECLINED);
		assertEquals(REQUESTER_EMAIL,friend.getRequesterEmail());
		assertEquals(BEFRIENDED_EMAIL,friend.getBeFriendedEmail());
		assertEquals(DECLINED,friend.getStatus());
	}

	@Test
	public void canSetRequesterName(){
		friend.setRequesterEmail(REQUESTER_EMAIL);
		assertEquals(REQUESTER_EMAIL,friend.getRequesterEmail());
	}
	
	@Test
	public void canSetRequesteeName(){
		friend.setBeFriendedEmail(BEFRIENDED_EMAIL);
		assertEquals(BEFRIENDED_EMAIL,friend.getBeFriendedEmail());
	}
	
	@Test
	public void canSetStatusToPending(){
		friend.setStatus(FriendStatus.PENDING);
		assertEquals(PENDING,friend.getStatus());
	}

	@Test
	public void canSetStatusToAccepted(){
		friend.setStatus(FriendStatus.ACCEPTED);
		assertEquals(ACCEPTED,friend.getStatus());
	}
	
	@Test
	public void canSetStatusToDeclined(){
		friend.setStatus(FriendStatus.DECLINED);
		assertEquals(DECLINED,friend.getStatus());
	}
	
	@Test
	public void statusSetToUnknownIfNoStatusSpecified(){
		friend = new Friend();
		assertEquals(UNKNOWN, friend.getStatus());
	}

	@Test
	public void canSetFriendId(){
		friend = new Friend();
		friend.setFriendId(FRIEND_ID);
		assertEquals(FRIEND_ID, friend.getFriendId());
	}

	@Test
	public void shouldBeEqual(){
		Friend friend1 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend1.setFriendId(1);
		Friend friend2 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend2.setFriendId(1);
		assertTrue(friend1.equals(friend2));
		assertEquals(friend1.hashcode(), friend2.hashcode());
	}

	@Test
	public void shouldNotBeEqualIfRequesterEmailDifferent(){
		Friend friend1 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend1.setFriendId(1);
		Friend friend2 = new Friend("", BEFRIENDED_EMAIL, ACCEPTED);
		friend2.setFriendId(1);
		assertFalse(friend1.equals(friend2));
		assertNotEquals(friend1.hashcode(), friend2.hashcode());
	}

	@Test
	public void shouldNotBeEqualIfBefriendedEmailDifferent(){
		Friend friend1 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend1.setFriendId(1);
		Friend friend2 = new Friend(REQUESTER_EMAIL, "", ACCEPTED);
		friend2.setFriendId(1);
		assertFalse(friend1.equals(friend2));
		assertNotEquals(friend1.hashcode(), friend2.hashcode());
	}

	@Test
	public void shouldNotBeEqualIfStatusDifferent(){
		Friend friend1 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend1.setFriendId(1);
		Friend friend2 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, PENDING);
		friend2.setFriendId(1);
		assertFalse(friend1.equals(friend2));
		assertNotEquals(friend1.hashcode(), friend2.hashcode());
	}

	@Test
	public void shouldNotBeEqualIfIdDifferent(){
		Friend friend1 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend1.setFriendId(1);
		Friend friend2 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend2.setFriendId(2);
		assertFalse(friend1.equals(friend2));
		assertNotEquals(friend1.hashcode(), friend2.hashcode());
	}

	@Test
	public void shouldNotBeEqualIfCheckingAgainstNull(){
		Friend friend1 = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend1.setFriendId(1);
		assertFalse(friend1.equals(null));
	}

	@Test
	public void testToString(){
		Friend friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		friend.setFriendId(1);
		String expectedString = "requester email=" + REQUESTER_EMAIL + " befriended email=" + BEFRIENDED_EMAIL
				+ " status=" + ACCEPTED.toString() + " friendId=1";
		assertEquals(expectedString, friend.toString());

	}


	@Test
	public void shouldCreateFriendWithFriendId(){
		Friend friend = new Friend(FRIEND_ID, REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
		assertTrue(friend instanceof Friend);
	}
}
