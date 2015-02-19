package friendTests;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.controllers.FriendController;
import uk.co.socialcalendar.useCases.FriendFacadeImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FriendControllerTest {

	public static final String FRIEND_VIEW = "friend";
	public static final String USER_ID = "userId";
	public static final String FRIENDS_PAGE_SECTION = "friends";
	public static final String ACCEPTED_FRIEND_REQUEST = "NAME2";
	FriendController friendController;
	FriendFacadeImpl mockFriendFacade;
	ModelAndView mav;

	@Before
	public void setup(){
		friendController = new FriendController();
		mockFriendFacade = mock(FriendFacadeImpl.class);
		friendController.setFriendFacade(mockFriendFacade);
	}
	
	@Test
	public void friendPageRendersFriendView(){
		mav = friendController.friendPage(USER_ID);
		assertEquals(FRIEND_VIEW,mav.getViewName());
	}

	@Test
	public void friendPageShowsExpectedFriendList(){
		List<Friend> expectedFriendList = setFriendExpectedAcceptedList();
		when(mockFriendFacade.getConfirmedFriends(anyString())).thenReturn(expectedFriendList);

		mav = friendController.friendPage(USER_ID);

		assertEquals(mav.getModelMap().get("friendList"), expectedFriendList);
	}

	private List<Friend> setFriendExpectedAcceptedList() {
		List<Friend> expectedFriendList = new ArrayList<Friend>();
		expectedFriendList.add(new Friend(USER_ID, "name2", FriendStatus.ACCEPTED));
		expectedFriendList.add(new Friend("name3", USER_ID, FriendStatus.ACCEPTED));
		return expectedFriendList;
	}

	@Test
	public void friendPageShouldShowFriendRequests(){
		List<Friend> expectedFriendRequests = setExpectedRequestList();

		when(mockFriendFacade.getFriendRequests(anyString())).thenReturn(expectedFriendRequests);

		mav = friendController.friendPage(USER_ID);
		assertEquals(mav.getModelMap().get("friendRequests"), expectedFriendRequests);

	}

	private List<Friend> setExpectedRequestList() {
		List<Friend> expectedFriendRequests = new ArrayList<Friend>();
		expectedFriendRequests.add(new Friend("name2",USER_ID, FriendStatus.PENDING));
		expectedFriendRequests.add(new Friend("name3",USER_ID, FriendStatus.PENDING));
		return expectedFriendRequests;
	}

	@Test
	public void friendPageShouldSetSectionAsFriends(){
		mav = friendController.friendPage(USER_ID);
		assertEquals(mav.getModelMap().get("section"), FRIENDS_PAGE_SECTION);
	}

	@Test
	public void addFriendPageShouldSetSectionAsFriends(){
		mav = friendController.addFriend(USER_ID);
		assertEquals(mav.getModelMap().get("section"), FRIENDS_PAGE_SECTION);
	}

	@Test
	public void addFriendPageShouldShowEmptyFriendObject(){
		mav = friendController.addFriend(USER_ID);
		Friend expectedFriend = new Friend();
		assertEquals(mav.getModelMap().get("friend"), expectedFriend);
	}

}
