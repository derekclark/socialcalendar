package friendTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.co.socialcalendar.frameworksAndDrivers.FriendDAOHibernateImpl;
import uk.co.socialcalendar.entities.FriendStatus;

public class FriendDAOHibernateImplTest {

	FriendDAOHibernateImpl friendDAOImpl;
	private final static String EMAIL_ADDRESS = "ownerEmail";

	@Before
	public void setup(){
		friendDAOImpl = new FriendDAOHibernateImpl();
	}
	
	@Test
	public void canCreateFriendDAOHibernateImplInstance(){
		assertTrue(friendDAOImpl instanceof FriendDAOHibernateImpl);		
	}
	
	@Test
	public void testSettingQueryStringForGettingAnOwnersAcceptedFriends(){
		String expectedString = "select friendEmail from Friend "
				+ "where ownerEmail = " + EMAIL_ADDRESS + " and status = " + FriendStatus.ACCEPTED;
		assertEquals(expectedString, friendDAOImpl.setQueryStringOwnersAcceptedFriends(EMAIL_ADDRESS) );
	}

	@Test
	public void testSettingQueryStringForGettingAnOwneesAcceptedFriends(){
		String expectedString = "select ownerEmail from Friends "
				+ "where friendEmail = " + EMAIL_ADDRESS + " and status = " + FriendStatus.ACCEPTED;
		assertEquals(expectedString, friendDAOImpl.setQueryStringOwneesAcceptedFriends(EMAIL_ADDRESS) );
	}

	
}
