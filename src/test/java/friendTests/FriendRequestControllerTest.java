package friendTests;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.controllers.friend.FriendCommonModel;
import uk.co.socialcalendar.interfaceAdapters.controllers.friend.FriendRequestController;
import uk.co.socialcalendar.interfaceAdapters.utilities.SessionAttributes;
import uk.co.socialcalendar.interfaceAdapters.utilities.StubMailNotification;
import uk.co.socialcalendar.useCases.friend.FriendFacadeImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class FriendRequestControllerTest {
    FriendRequestController controller;
    FriendFacadeImpl mockFriendFacade;
    ModelAndView mav;
    Friend friend;
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";
    public static final String FRIENDS_PAGE_SECTION = "friends";
    StubMailNotification stubMailNotification = new StubMailNotification();

    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;

    SessionAttributes sessionAttributes;
    FriendCommonModel mockFriendCommonModel;

    @Before
    public void setup(){
        controller = new FriendRequestController();
        mockFriendFacade = mock(FriendFacadeImpl.class);
        controller.setFriendFacade(mockFriendFacade);
        friend = new Friend("NAME1","NAME2", FriendStatus.PENDING);
        friend.setFriendId(1);

        sessionAttributes = new SessionAttributes();
        mockFriendCommonModel = mock(FriendCommonModel.class);
        controller.setSessionAttributes(sessionAttributes);
        controller.setFriendCommonModel(mockFriendCommonModel);

        setupTestDoubles();
        setupHttpSessions();
        createExpectedModelAndView();
    }

    public void setupTestDoubles(){
        when(mockFriendFacade.getFriend(anyInt())).thenReturn(friend);
        controller.setUserNotification(stubMailNotification);
    }

    public void setupHttpSessions(){
        model = new ExtendedModelMap();
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("USER_NAME")).thenReturn(USER_ID);

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
        expectedMap.put("userFacebookId", "");
        when(mockFriendCommonModel.getCommonModelAttributes(anyString())).thenReturn(expectedMap);
        return expectedMap;
    }



    @Test
    public void acceptingFriendRequestShouldShowAcceptedConfirmationMessage() throws ServletException, UnsupportedEncodingException {
        mav = controller.acceptFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(mav.getModelMap().get("message"), "You have just accepted a friend request from " + friend.getRequesterEmail());
    }

    @Test
    public void acceptingFriendRequestShouldRenderFriendsPage() throws ServletException, UnsupportedEncodingException {
        mav = controller.acceptFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(mav.getViewName(),FRIEND_VIEW);
    }

    @Test
    public void decliningFriendRequestShouldShowDeclinedConfirmationMessage() throws ServletException, UnsupportedEncodingException {
        mav = controller.declineFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(mav.getModelMap().get("message"), "You have just declined a friend request from " + friend.getRequesterEmail());
    }

    @Test
    public void decliningFriendRequestShouldRenderFriendsPage() throws ServletException, UnsupportedEncodingException {
        mav = controller.declineFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(mav.getViewName(),FRIEND_VIEW);
    }

    @Test
    public void canNotifyUserOfAcceptedRequest(){
        friend.setStatus(FriendStatus.ACCEPTED);
        boolean result = controller.notifyUser(friend);

        assertTrue(result);
        assertEquals(1, stubMailNotification.getMessagesSent());
        assertEquals(friend.getRequesterEmail(),stubMailNotification.getRecipient());
        assertEquals(friend.getBeFriendedEmail(),stubMailNotification.getSender());
        assertEquals("Accepted Friend Request", stubMailNotification.getSubject());
        assertEquals(friend.getBeFriendedEmail() + " has accepted your friend request", stubMailNotification.getMessage());
    }

    @Test
    public void canNotifyUserOfDeclinedRequest(){
        friend.setStatus(FriendStatus.DECLINED);
        boolean result = controller.notifyUser(friend);

        assertTrue(result);
        assertEquals(1, stubMailNotification.getMessagesSent());
        assertEquals(friend.getRequesterEmail(),stubMailNotification.getRecipient());
        assertEquals(friend.getBeFriendedEmail(),stubMailNotification.getSender());
        assertEquals("Declined Friend Request", stubMailNotification.getSubject());
        assertEquals(friend.getBeFriendedEmail() + " has declined your friend request", stubMailNotification.getMessage());
    }

    @Test
    public void decliningFriendRequestSendsNotification() throws ServletException, UnsupportedEncodingException {

        mav = controller.declineFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(1, stubMailNotification.getMessagesSent());
    }

    @Test
    public void acceptingFriendRequestSendsNotification() throws ServletException, UnsupportedEncodingException {
        mav = controller.acceptFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(1, stubMailNotification.getMessagesSent());
    }

    @Test
    public void acceptingFriendRequestSetsFriendStatusToAccepted() throws ServletException, UnsupportedEncodingException {
        mav = controller.acceptFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        verify(mockFriendFacade, times(1)).acceptFriendRequest(anyInt());
    }

    @Test
    public void decliningFriendRequestSetsFriendStatusToAccepted() throws ServletException, UnsupportedEncodingException {
        mav = controller.declineFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        verify(mockFriendFacade, times(1)).declineFriendRequest(anyInt());
    }

    @Test
    public void acceptFriendRendersFriendView() throws ServletException, UnsupportedEncodingException {

        mav = controller.acceptFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(FRIEND_VIEW,mav.getViewName());
    }

    @Test
    public void acceptFriendRequestPopulatesAllModelAttributes() throws ServletException, UnsupportedEncodingException {

        mav = controller.acceptFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertNotNull(mav.getModelMap().get("newFriend"));
        assertNotNull(mav.getModelMap().get("friendList"));
        assertNotNull(mav.getModelMap().get("userName"));
        assertNotNull(mav.getModelMap().get("friendRequests"));
        assertNotNull(mav.getModelMap().get("isAuthenticated"));
        assertNotNull(mav.getModelMap().get("oauthToken"));
        assertNotNull(mav.getModelMap().get("userFacebookId"));
        assertNotNull(mav.getModelMap().get("section"));
    }

    @Test
    public void declineFriendRequestPopulatesAllModelAttributes() throws ServletException, UnsupportedEncodingException {

        mav = controller.declineFriendRequest(friend.getFriendId(), model, mockHttpServletRequest, mockHttpServletResponse);
        assertNotNull(mav.getModelMap().get("newFriend"));
        assertNotNull(mav.getModelMap().get("friendList"));
        assertNotNull(mav.getModelMap().get("userName"));
        assertNotNull(mav.getModelMap().get("friendRequests"));
        assertNotNull(mav.getModelMap().get("isAuthenticated"));
        assertNotNull(mav.getModelMap().get("oauthToken"));
        assertNotNull(mav.getModelMap().get("userFacebookId"));
        assertNotNull(mav.getModelMap().get("section"));
    }



}
