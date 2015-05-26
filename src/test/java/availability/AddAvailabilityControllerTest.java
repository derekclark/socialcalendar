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
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    FakeAvailabilityFacadeImpl fakeAvailabilityFacade;
    UserFacade mockUserFacade;
    User user;
    FriendModelFacade mockFriendModelFacade;

    SessionAttributes mockSessionAttributes;

    @Before
    public void setup(){
        controller = new AddAvailabilityController();
        model = new ExtendedModelMap();
        user = new User(ME, MY_NAME, "facebookId");
        fakeAvailabilityFacade = new FakeAvailabilityFacadeImpl();
        setMocks();
        controller.setAvailabilityFacade(fakeAvailabilityFacade);
        controller.setUserFacade(mockUserFacade);
        controller.setFriendModelFacade(mockFriendModelFacade);
    }

    public void setMocks(){
        setupMockUserFacade();
        setupMockSessionAttributes();
        mockFriendModelFacade = mock(FriendModelFacade.class);
    }

    public void setupMockUserFacade(){
        mockUserFacade = mock(UserFacade.class);
        when(mockUserFacade.getUser(ME)).thenReturn(user);

    }
    public void setupMockSessionAttributes(){
        mockSessionAttributes = mock(SessionAttributes.class);
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
        assertEquals("availabilityCreate",mav.getViewName());
    }

    public ModelAndView callAddAvailability(String title, String startDate, String endDate) throws IOException, ServletException {
        List<String> selectedFriends = new ArrayList<String>();
        return controller.addAvailability(title, startDate, endDate, selectedFriends, model, mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void addAvailabilityReturnsExpectedMessage() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("You have just created a new availability",mav.getModelMap().get("alertMessage"));
    }

    @Test
    public void addAvailabilityShouldCallCreateAvailability() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertTrue(fakeAvailabilityFacade.isCreateMethodCalled());
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

    @Test
    public void setsAvailabilityWithTitle() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals(TITLE, fakeAvailabilityFacade.getAvailability().getTitle());
    }

    @Test
    public void setsSectionAsAvailability() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("availability",mav.getModel().get("section"));
    }

    @Test
    public void modelReturnsNewAvailabilityAttribute() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertNotNull(mav.getModelMap().get("newAvailability"));
    }

    @Test
    public void modelReturnsFriendList() throws IOException, ServletException {
        FriendModel friendModel1 = new FriendModel(new User("friendEmail1","friendName1","facebookId1"));
        FriendModel friendModel2 = new FriendModel(new User("friendEmail2","friendName2","facebookId2"));

        List<FriendModel> expectedFriendList = new ArrayList<FriendModel>();
        expectedFriendList.add(friendModel1);
        expectedFriendList.add(friendModel2);

        when(mockFriendModelFacade.getFriendModelList(ME)).thenReturn(expectedFriendList);

        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertNotNull(mav.getModelMap().get("friendList"));
    }


}
