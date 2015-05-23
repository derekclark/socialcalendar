package availability;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.availability.controllers.AddAvailabilityController;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacadeImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddAvailabilityControllerTest {
    AddAvailabilityController controller;
    ModelAndView mav;
    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;
    AvailabilityFacadeImpl mockAvailabilityFacade;

    @Before
    public void setup(){
        controller = new AddAvailabilityController();
        mockAvailabilityFacade = mock(AvailabilityFacadeImpl.class);
        setMocks();
    }

    public void setMocks(){
        controller.setAvailabilityFacade(mockAvailabilityFacade);
    }

    @Test
    public void canCreateInstance(){
        assertTrue(controller instanceof AddAvailabilityController);
    }

    @Test
    public void addAvailabilityRendersAvailabilityView() throws IOException, ServletException {
        List<String> selectedFriends = new ArrayList<String>();
        mav = controller.addAvailability(selectedFriends, new Availability(),model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals("availability",mav.getViewName());
    }

    @Test
    public void addAvailabilityReturnsExpectedMessage() throws IOException, ServletException {
        List<String> selectedFriends = new ArrayList<String>();
        mav = controller.addAvailability(selectedFriends, new Availability(),model, mockHttpServletRequest, mockHttpServletResponse);
        assertEquals("You have just created a new availability",mav.getModelMap().get("message"));
    }

    @Test
    public void addAvailabilityShouldCallCreateAvailability() throws IOException, ServletException {
        List<String> selectedFriends = new ArrayList<String>();
        Availability availability = new Availability("ownerEmail", "ownerName", "title", new DateTime(), new DateTime(), "status");
        mav = controller.addAvailability(selectedFriends, availability,model, mockHttpServletRequest, mockHttpServletResponse);
        verify(mockAvailabilityFacade).create(availability);
    }
}
