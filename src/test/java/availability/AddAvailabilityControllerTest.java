package availability;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
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
    public static final String END_DATE = "2012-01-27 14:15";
    public static final String START_DATE = "2012-01-25 11:12";
    public static final String TITLE = "title";
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
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("availability",mav.getViewName());
    }

    public ModelAndView callAddAvailability(String title, String startDate, String endDate) throws IOException, ServletException {
        List<String> selectedFriends = new ArrayList<String>();
        return controller.addAvailability(TITLE, startDate, endDate, selectedFriends, model, mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void addAvailabilityReturnsExpectedMessage() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("You have just created a new availability",mav.getModelMap().get("message"));
    }

    @Test
    public void addAvailabilityShouldCallCreateAvailability() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        String pattern = "yyyy-MM-dd HH:mm";
        LocalDateTime startDateFormatted = LocalDateTime.parse(START_DATE, DateTimeFormat.forPattern(pattern));
        LocalDateTime endDateFormatted = LocalDateTime.parse(END_DATE, DateTimeFormat.forPattern(pattern));

        Availability expectedAvailability = new Availability("ownerEmail","ownerName", TITLE,
                startDateFormatted, endDateFormatted, "status");
        verify(mockAvailabilityFacade).create(expectedAvailability);
    }
}
