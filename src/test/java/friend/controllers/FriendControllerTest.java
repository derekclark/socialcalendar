package friend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.friend.controllers.FriendCommonModel;
import uk.co.socialcalendar.friend.controllers.FriendController;
import uk.co.socialcalendar.authentication.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FriendControllerTest {
	public static final String FRIEND_VIEW = "friend";
	public static final String USER_ID = "userId";
	FriendController controller;
	ModelAndView mav;
	SessionAttributes sessionAttributes;
	FriendCommonModel mockFriendCommonModel;
	Model model;
	HttpServletRequest emptyHttpServletRequest;
	HttpServletRequest mockHttpServletRequest;
	HttpServletResponse mockHttpServletResponse;
	HttpSession mockSession;
	User user;

	@Before
	public void setup(){
		controller = new FriendController();
		sessionAttributes = new SessionAttributes();
		mockFriendCommonModel = mock(FriendCommonModel.class);
		controller.setSessionAttributes(sessionAttributes);
		controller.setFriendCommonModel(mockFriendCommonModel);
		setupHttpSessions();
	}

	public void setupHttpSessions(){
		model = new ExtendedModelMap();
		mockHttpServletRequest = mock(HttpServletRequest.class);
		mockHttpServletResponse = mock(HttpServletResponse.class);

		mockSession = mock(HttpSession.class);
		when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("USER_ID")).thenReturn(USER_ID);
		when(mockSession.getAttribute("IS_AUTHENTICATED")).thenReturn(true);
		when(mockSession.getAttribute("FACEBOOK_ID")).thenReturn("facebookId");
	}


	@Test
	public void friendPageRendersFriendView(){
		createExpectedModelAndView();

		mav = controller.friendPage(model, mockHttpServletRequest, mockHttpServletResponse);
		assertEquals(FRIEND_VIEW,mav.getViewName());
	}

	@Test
	public void populateSectionAttribute(){
		createExpectedModelAndView();

		mav = controller.friendPage(model, mockHttpServletRequest, mockHttpServletResponse);
		assertNotNull(mav.getModelMap().get("section"));
	}


	@Test
	public void allModelAttributesArePopulated(){
		createExpectedModelAndView();
		mav = controller.friendPage(model, mockHttpServletRequest, mockHttpServletResponse);
		assertNotNull(mav.getModelMap().get("newFriend"));
		assertNotNull(mav.getModelMap().get("friendList"));
		assertNotNull(mav.getModelMap().get("userName"));
		assertNotNull(mav.getModelMap().get("friendRequests"));
//		assertNotNull(mav.getModelMap().get("isAuthenticated"));
//		assertNotNull(mav.getModelMap().get("oauthToken"));
//		assertNotNull(mav.getModelMap().get("userFacebookId"));
	}

	private Map<String, Object> createExpectedModelAndView() {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		expectedMap.put("section", "");
		expectedMap.put("newFriend", "");
		expectedMap.put("friendList", "");
		expectedMap.put("userName", "");
		expectedMap.put("friendRequests", "");
		expectedMap.put("isAuthenticated", "");
		expectedMap.put("oauthToken", "");
		expectedMap.put("userFacebookId","");
		when(mockFriendCommonModel.getCommonModelAttributes(anyString())).thenReturn(expectedMap);
		return expectedMap;
	}


	@Test
	public void shouldRedirectToLoginPageWhenAuthenticatedUserIsNull(){
		setupNullLoggedInUser();
		mav = controller.friendPage(model, emptyHttpServletRequest, mockHttpServletResponse);
		assertEquals("login", mav.getViewName());
	}

	@Test
	public void shouldRedirectToLoginPageWhenAuthenticatedUserIsEmpty(){
		setupEmptyLoggedInUser();
		mav = controller.friendPage(model, emptyHttpServletRequest, mockHttpServletResponse);
		assertEquals("login", mav.getViewName());
	}

	private void setupNullLoggedInUser() {
		emptyHttpServletRequest = mock(HttpServletRequest.class);
		when(emptyHttpServletRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("USER_ID")).thenReturn(null);
	}

	private void setupEmptyLoggedInUser() {
		emptyHttpServletRequest = mock(HttpServletRequest.class);
		when(emptyHttpServletRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("USER_ID")).thenReturn("");
	}

}
