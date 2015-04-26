package availability;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.AvailabilityController;
import uk.co.socialcalendar.friend.controllers.FriendCommonModel;
import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.friend.entities.FriendStatus;
import uk.co.socialcalendar.friend.useCases.FriendFacade;
import uk.co.socialcalendar.friend.useCases.FriendFacadeImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityControllerTest {
    public static final String ME = "me";
    AvailabilityController controller;
    SessionAttributes sessionAttributes;
    FriendCommonModel mockFriendCommonModel;

    FriendFacade mockFriendFacade;

    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;
    ModelAndView mav;

    @Before
    public void setup(){
        controller = new AvailabilityController();
        sessionAttributes = new SessionAttributes();
        mockFriendFacade = mock(FriendFacadeImpl.class);
        controller.setFriendFacade(mockFriendFacade);
        controller.setSessionAttributes(sessionAttributes);
        mockHttp();
        mav = controller.addAvailability(model, mockHttpServletRequest, mockHttpServletResponse);

    }

    public void mockHttp(){
        model = new ExtendedModelMap();
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
    }

    @Test
    public void canCreateInstance(){
        assertTrue(controller instanceof AvailabilityController);
    }

    @Test
    public void rendersAvailabilityView(){
        assertEquals("addAvailability",mav.getViewName());
    }

    @Test
    public void sectionIsAvailability(){
        assertEquals("availability",mav.getModelMap().get("section"));
    }

    @Test
    public void modelReturnsNewAvailabilityAttribute() {
        assertNotNull(mav.getModelMap().get("newAvailability"));
    }

    @Test
    public void modelReturnsFriendList(){
        Friend friend1=new Friend(1, ME,"friend1", FriendStatus.ACCEPTED);
        Friend friend2=new Friend(2,"friend2", ME, FriendStatus.ACCEPTED);

        List<Friend> expectedFriendList = new ArrayList<Friend>();
        expectedFriendList.add(friend1);
        expectedFriendList.add(friend2);

        when(mockFriendFacade.getMyAcceptedFriends(ME)).thenReturn(expectedFriendList);

        assertNotNull(mav.getModelMap().get("friendList"));
    }

}
