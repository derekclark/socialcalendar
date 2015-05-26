package availability;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.controllers.AddAvailabilityController;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddAvailabilityControllerTest {
    public static final String END_DATE = "2012-01-27 14:15";
    public static final String START_DATE = "2012-01-25 11:12";
    public static final String TITLE = "title";
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String ME = "me";
    public static final String MY_NAME = "myName";
    AddAvailabilityController controller;
    ModelAndView mav;
    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;
    FakeAvailabilityFacadeImpl fakeAvailabilityFacade;
    UserFacade mockUserFacade;
    User user;

    SessionAttributes mockSessionAttributes;

    @Before
    public void setup(){
        controller = new AddAvailabilityController();

        user = new User(ME, MY_NAME, "facebookId");
        fakeAvailabilityFacade = new FakeAvailabilityFacadeImpl();
        setMocks();
        setupHttpSessions();
    }

    public void setMocks(){
        mockUserFacade = mock(UserFacade.class);
        controller.setAvailabilityFacade(fakeAvailabilityFacade);
        controller.setUserFacade(mockUserFacade);
        when(mockUserFacade.getUser(ME)).thenReturn(user);
    }

    public void setupHttpSessions(){
        model = new ExtendedModelMap();
        mockSessionAttributes = mock(SessionAttributes.class);
        mockSession = mock(HttpSession.class);
        controller.setSessionAttributes(mockSessionAttributes);
        when(mockSessionAttributes.getLoggedInUserId((HttpServletRequest) anyObject())).thenReturn(ME);
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
        return controller.addAvailability(title, startDate, endDate, selectedFriends, model, mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void addAvailabilityReturnsExpectedMessage() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("You have just created a new availability",mav.getModelMap().get("message"));
    }

    @Test
    public void addAvailabilityShouldCallCreateAvailability() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        Availability expectedAvailability = createExpectedAvailability();
        assertTrue(fakeAvailabilityFacade.isCreateMethodCalled());
    }

    public Availability createExpectedAvailability(){
        LocalDateTime startDateFormatted = LocalDateTime.parse(START_DATE, DateTimeFormat.forPattern(DATE_PATTERN));
        LocalDateTime endDateFormatted = LocalDateTime.parse(END_DATE, DateTimeFormat.forPattern(DATE_PATTERN));
        return new Availability(ME,MY_NAME, TITLE,
                startDateFormatted, endDateFormatted, "status");
    }

    @Test
    public void setsAvailabilityWithOwnerEmail() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals(ME, fakeAvailabilityFacade.getAvailability().getOwnerEmail());
    }

    @Test
    public void setsAvailabilityWithOwnerName() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals(MY_NAME, fakeAvailabilityFacade.getAvailability().getOwnerName());
    }

    @Test
    public void setsAvailabilityWithStartDate() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        LocalDateTime expectedDate = LocalDateTime.parse(START_DATE, DateTimeFormat.forPattern(DATE_PATTERN));

        assertEquals(expectedDate, fakeAvailabilityFacade.getAvailability().getStartDate());
    }

    @Test
    public void setsAvailabilityWithEndDate() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        LocalDateTime expectedDate = LocalDateTime.parse(END_DATE, DateTimeFormat.forPattern(DATE_PATTERN));

        assertEquals(expectedDate, fakeAvailabilityFacade.getAvailability().getEndDate());
    }

    @Test
    public void setsAvailabilityWithStatus() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("status", fakeAvailabilityFacade.getAvailability().getStatus());
    }


}
