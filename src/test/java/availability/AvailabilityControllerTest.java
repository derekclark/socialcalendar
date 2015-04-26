package availability;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.AvailabilityController;
import uk.co.socialcalendar.friend.controllers.FriendCommonModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityControllerTest {
    AvailabilityController controller;
    SessionAttributes sessionAttributes;
    FriendCommonModel mockFriendCommonModel;
    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;
    ModelAndView mav;

    @Before
    public void setup(){
        controller = new AvailabilityController();
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
}
