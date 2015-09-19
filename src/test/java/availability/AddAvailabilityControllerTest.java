package availability;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import testSupport.HttpMocks;
import uk.co.socialcalendar.availability.controllers.AddAvailabilityController;
import uk.co.socialcalendar.availability.controllers.AvailabilityCommonModel;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddAvailabilityControllerTest {
    private static final String END_DATE = "2012-01-27 14:15";
    private static final String START_DATE = "2012-01-25 11:12";
    private static final String TITLE = "title";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String ME = "me";
    private static final String MY_NAME = "myName";
    private static final String MY_FACEBOOK_ID = "facebookId";
    AddAvailabilityController controller;
    ModelAndView mav;
    FakeAvailabilityFacadeImpl fakeAvailabilityFacade;
    UserFacade mockUserFacade;
    User user;
    FriendModelFacade mockFriendModelFacade;
    HttpMocks httpMocks;
    AvailabilityCommonModel mockAvailabilityCommonModel;

    @Before
    public void setup(){
        controller = new AddAvailabilityController();
        user = new User(ME, MY_NAME, MY_FACEBOOK_ID);
        fakeAvailabilityFacade = new FakeAvailabilityFacadeImpl();
        setupMocks();
    }

    public void setupMocks(){
        setupUserMock();
        mockFriendModelFacade = mock(FriendModelFacade.class);
        setupHttpMocks();
        setMockAvailabilityCommonModel();
        controller.setAvailabilityFacade(fakeAvailabilityFacade);
        controller.setFriendModelFacade(mockFriendModelFacade);
    }

    public void setMockAvailabilityCommonModel(){
        mockAvailabilityCommonModel = mock(AvailabilityCommonModel.class);
        controller.setAvailabilityCommonModel(mockAvailabilityCommonModel);
    }

    public void setupHttpMocks(){
        httpMocks = new HttpMocks();
        controller.setSessionAttributes(httpMocks.getMockSessionAttributes());
    }

    public void setupUserMock(){
        mockUserFacade = mock(UserFacade.class);
        controller.setUserFacade(mockUserFacade);
        User user = new User(ME, MY_NAME, MY_FACEBOOK_ID);
        when(mockUserFacade.getUser(ME)).thenReturn(user);
    }

    @Test
    public void addAvailabilityRendersAvailabilityView() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("availabilityCreate",mav.getViewName());
    }

    public ModelAndView callAddAvailability(String title, String startDate, String endDate) throws IOException, ServletException {
        List<String> selectedFriends = new ArrayList<String>();
        return controller.addAvailability(title, startDate, endDate, selectedFriends, httpMocks.getModel(),
                httpMocks.getMockHttpServletRequest(), httpMocks.getMockHttpServletResponse());
    }

    @Test
    public void addAvailabilityReturnsExpectedMessage() throws IOException, ServletException {
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("You have just created a new availability",mav.getModelMap().get("message"));
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
    public void modelReturnsSection() throws IOException, ServletException {
        mockExpectedModel();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertEquals("availability",mav.getModel().get("section"));
    }

    @Test
    public void modelReturnsNewAvailability() throws IOException, ServletException {
        mockExpectedModel();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertNotNull(mav.getModelMap().get("newAvailability"));
    }

    @Test
    public void modelReturnsFriendList() throws IOException, ServletException {
        mockExpectedModel();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE);
        assertNotNull(mav.getModelMap().get("friendList"));
    }

    private Map<String, Object> mockExpectedModel() {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        expectedMap.put("section", "availability");
        expectedMap.put("newAvailability", new Availability());

        List<FriendModel> expectedFriendList = setExpectedFriendList();
        expectedMap.put("friendList", expectedFriendList);

        when(mockFriendModelFacade.getFriendModelList(ME)).thenReturn(expectedFriendList);
        when(mockAvailabilityCommonModel.getAttributes(anyString())).thenReturn(expectedMap);
        return expectedMap;
    }

    public List<FriendModel> setExpectedFriendList(){
        FriendModel friendModel1 = new FriendModel(new User("friendEmail1","friendName1","facebookId1"));
        FriendModel friendModel2 = new FriendModel(new User("friendEmail2","friendName2","facebookId2"));

        List<FriendModel> expectedFriendList = new ArrayList<FriendModel>();
        expectedFriendList.add(friendModel1);
        expectedFriendList.add(friendModel2);

        return expectedFriendList;
    }

}
