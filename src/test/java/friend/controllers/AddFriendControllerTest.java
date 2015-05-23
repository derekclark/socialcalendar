package friend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.friend.controllers.AddFriendController;
import uk.co.socialcalendar.friend.controllers.FriendCommonModel;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.friend.useCases.FriendFacadeImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@Controller
public class AddFriendControllerTest {
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "me";
    public static final String REQUESTEE_EMAIL = "requesteeEmail";
    AddFriendController controller;
    ModelAndView mav;
    SessionAttributes sessionAttributes;
    FriendCommonModel mockFriendCommonModel;
    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;
    User user;
    FriendFacadeImpl mockFriendFacade;

    @Before
    public void setup(){
        controller = new AddFriendController();
        sessionAttributes = new SessionAttributes();
        mockFriendCommonModel = mock(FriendCommonModel.class);
        mockFriendFacade = mock(FriendFacadeImpl.class);
        controller.setSessionAttributes(sessionAttributes);
        controller.setFriendCommonModel(mockFriendCommonModel);
        controller.setFriendFacade(mockFriendFacade);
        setupHttpSessions();
    }

    public void setupHttpSessions(){
        model = new ExtendedModelMap();
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("USER_ID")).thenReturn(USER_ID);
    }

    @Test
    public void addFriendRendersFriendView(){
        createExpectedModelAndView();

        mav = controller.addFriend(REQUESTEE_EMAIL, model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(FRIEND_VIEW,mav.getViewName());
    }

    @Test
    public void populateSectionAttribute(){
        createExpectedModelAndView();

        mav = controller.addFriend(REQUESTEE_EMAIL, model, mockHttpServletRequest, mockHttpServletResponse);
        assertNotNull(mav.getModelMap().get("section"));
    }

    @Test
    public void allModelAttributesArePopulated(){
        createExpectedModelAndView();
        mav = controller.addFriend(REQUESTEE_EMAIL, model, mockHttpServletRequest, mockHttpServletResponse);
        assertNotNull(mav.getModelMap().get("newFriend"));
        assertNotNull(mav.getModelMap().get("friendList"));
        assertNotNull(mav.getModelMap().get("userName"));
        assertNotNull(mav.getModelMap().get("friendRequests"));
        assertNotNull(mav.getModelMap().get("isAuthenticated"));
        assertNotNull(mav.getModelMap().get("oauthToken"));
        assertNotNull(mav.getModelMap().get("userFacebookId"));
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
    public void addFriendShowsAConfirmationMessageAfterwards(){
        mav = controller.addFriend(REQUESTEE_EMAIL, model,
                mockHttpServletRequest, mockHttpServletResponse);
        assertEquals("You have sent a friend request to " + REQUESTEE_EMAIL, mav.getModelMap().get("message"));
    }

    @Test
    public void addFriendShouldCallCreateFriendRequest(){
        mav = controller.addFriend(REQUESTEE_EMAIL, model,
                mockHttpServletRequest, mockHttpServletResponse);
        verify(mockFriendFacade).createFriendRequest(USER_ID, REQUESTEE_EMAIL);
    }
}
