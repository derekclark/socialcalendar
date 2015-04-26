package availability;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.AvailabilityController;
import uk.co.socialcalendar.friend.controllers.FriendCommonModel;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.entities.User;

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

    FriendModelFacade mockFriendModelFacade;

    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;
    ModelAndView mav;

    @Before
    public void setup(){
        controller = new AvailabilityController();
        sessionAttributes = new SessionAttributes();
        mockFriendModelFacade = mock(FriendModelFacade.class);
        controller.setFriendModelFacade(mockFriendModelFacade);
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
        assertEquals("availabilityCreate",mav.getViewName());
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
        FriendModel friendModel1 = new FriendModel(new User("friendEmail1","friendName1","facebookId1"));
        FriendModel friendModel2 = new FriendModel(new User("friendEmail2","friendName2","facebookId2"));

        List<FriendModel> expectedFriendList = new ArrayList<FriendModel>();
        expectedFriendList.add(friendModel1);
        expectedFriendList.add(friendModel2);

        when(mockFriendModelFacade.getFriendModelList(ME)).thenReturn(expectedFriendList);

        assertNotNull(mav.getModelMap().get("friendList"));
    }

}
