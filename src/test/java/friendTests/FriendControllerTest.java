package friendTests;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.controllers.FriendController;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModel;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModelFacade;
import uk.co.socialcalendar.useCases.FriendFacadeImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
	FriendModelFacade mockFriendModelFacade;

	Model model;

	HttpServletRequest mockHttpServletRequest;
	HttpServletResponse mockHttpServletResponse;
	HttpSession mockSession;

	@Before
	public void setup(){
		friendController = new FriendController();
		mockFriendFacade = mock(FriendFacadeImpl.class);
		mockFriendModelFacade = mock(FriendModelFacade.class);
		friendController.setFriendFacade(mockFriendFacade);
		friendController.setFriendModelFacade(mockFriendModelFacade);
		setupHttpSessions();
	}

	public void setupHttpSessions(){
		model = new ExtendedModelMap();
		mockHttpServletRequest = mock(HttpServletRequest.class);
		mockHttpServletResponse = mock(HttpServletResponse.class);
		mockSession = mock(HttpSession.class);
		when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("USER_NAME")).thenReturn(USER_ID);

	}
	@Test
	public void friendPageRendersFriendView(){
		mav = friendController.friendPage(model, mockHttpServletRequest, mockHttpServletResponse);
		assertEquals(FRIEND_VIEW,mav.getViewName());
	}

	@Test
	public void friendPageShowsFriendList(){
//		List<Friend> expectedFriendList = setFriendExpectedAcceptedList();
		List<FriendModel>expectedFriendModelList = new ArrayList<FriendModel>();
		FriendModel friendModel = new FriendModel();
		friendModel.setName("name");
		friendModel.setEmail("email");
		friendModel.setFriendId(1);
		friendModel.setFacebookId("123");
		expectedFriendModelList.add(friendModel);


//		when(mockFriendFacade.getConfirmedFriends(anyString())).thenReturn(expectedFriendList);
		when(mockFriendModelFacade.getFriendModelList(anyString())).thenReturn(expectedFriendModelList);


		mav = friendController.friendPage(model, mockHttpServletRequest, mockHttpServletResponse);

		assertEquals(mav.getModelMap().get("friendList"), expectedFriendModelList);
	}




	@Test
	public void friendPageShouldShowFriendRequests(){
		List<Friend> expectedFriendRequests = setExpectedRequestList();

		when(mockFriendFacade.getFriendRequests(anyString())).thenReturn(expectedFriendRequests);

		mav = friendController.friendPage(model, mockHttpServletRequest, mockHttpServletResponse);
		assertEquals(mav.getModelMap().get("friendRequests"), expectedFriendRequests);

	}


	@Test
	public void friendPageShouldSetSectionAsFriends(){
		mav = friendController.friendPage(model, mockHttpServletRequest, mockHttpServletResponse);
		assertEquals(mav.getModelMap().get("section"), FRIENDS_PAGE_SECTION);
	}

	@Test
	public void modelShouldIncludeEmptyFriendObject(){
		mav = friendController.friendPage(model, mockHttpServletRequest, mockHttpServletResponse);
		assertEquals(mav.getModelMap().get("friend"), new FriendModel());

	}

	private List<Friend> setFriendExpectedAcceptedList() {
		List<Friend> expectedFriendList = new ArrayList<Friend>();
		expectedFriendList.add(new Friend(USER_ID, "name2", FriendStatus.ACCEPTED));
		expectedFriendList.add(new Friend("name3", USER_ID, FriendStatus.ACCEPTED));
		return expectedFriendList;
	}

	private List<Friend> setExpectedRequestList() {
		List<Friend> expectedFriendRequests = new ArrayList<Friend>();
		expectedFriendRequests.add(new Friend("name2",USER_ID, FriendStatus.PENDING));
		expectedFriendRequests.add(new Friend("name3",USER_ID, FriendStatus.PENDING));
		return expectedFriendRequests;
	}


}
