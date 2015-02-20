package friendTests;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.interfaceAdapters.controllers.AddFriendController;
import uk.co.socialcalendar.useCases.FriendFacadeImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Controller
public class AddFriendControllerTest {
    AddFriendController controller;
    FriendFacadeImpl mockFriendFacade;
    ModelAndView mav;
    Model model;

    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;
    public static final String USER_ID = "userId";
    public static final String FRIENDS_PAGE_SECTION = "friends";


    @Before
    public void setup(){
        controller = new AddFriendController();
        mockFriendFacade = mock(FriendFacadeImpl.class);
        controller.setFriendFacade(mockFriendFacade);
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
    public void addFriendPageShouldSetSectionAsFriends(){
        mav = controller.addFriend(model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(mav.getModelMap().get("section"), FRIENDS_PAGE_SECTION);
    }

    @Test
    public void addFriendPageShouldShowEmptyFriendObject(){
        mav = controller.addFriend(model, mockHttpServletRequest, mockHttpServletResponse);
        Friend expectedFriend = new Friend();
        assertEquals(mav.getModelMap().get("friend"), expectedFriend);
    }

}
