package availability;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import testSupport.HttpMocks;
import uk.co.socialcalendar.availability.controllers.AmendAvailabilityController;
import uk.co.socialcalendar.availability.controllers.AvailabilityCommonModel;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.message.Message;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AmendAvailabilityControllerTest {
    AmendAvailabilityController controller;
    HttpMocks httpMocks;
    UserFacade mockUserFacade;
    ModelAndView mav;
    AvailabilityFacade mockAvailabilityFacade;
    AvailabilityCommonModel mockAvailabilityCommonModel;
    String me;
    User user1=new User("USER1", "NAME1", "FACEBOOK1");
    User user2=new User("USER2", "NAME2", "FACEBOOK2");

    private static final String ME = "me";
    private static final String NOT_ME = "not_me";
    private static final String MY_NAME = "my name";
    private static final String MY_FACEBOOK_ID = "facebookid";
    private static final LocalDateTime START_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 0);
    private static final LocalDateTime END_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 30);

    @Before
    public void setup(){
        controller = new AmendAvailabilityController();
        setupMocks();
    }

    @Test
    public void rendersCorrectView() throws IOException, ServletException {
        mockAvailability(ME);
        mav=callAmendAvailability(1);
        assertEquals("amendAvailability",mav.getViewName());
    }

    @Test
    public void returnsExpectedAvailabilityToAmend() throws IOException, ServletException {
        Availability expectedAvailability = new Availability(ME, MY_NAME, "title", START_DATE, END_DATE, "status");
        expectedAvailability.setId(1);
        when(mockAvailabilityFacade.get(anyInt())).thenReturn(expectedAvailability);
        mav=callAmendAvailability(1);
        assertEquals(expectedAvailability,mav.getModelMap().get("amendAvailability"));
    }

    @Test
    public void modelReturnsCommonAttributes() throws IOException, ServletException {
        mockExpectedModel();
        mockAvailability(ME);
        mav=callAmendAvailability(1);
        assertEquals("availability",mav.getModelMap().get("section"));
        assertEquals(new Availability(),mav.getModelMap().get("newAvailability"));
        assertNotNull(mav.getModelMap().get("friendList"));
    }

    @Test
    public void modelReturnsMessageObject() throws IOException, ServletException {
        mockExpectedModel();
        mockAvailability(ME);
        mav=callAmendAvailability(1);
        assertEquals(new Message(), mav.getModelMap().get("newMessage"));
    }

    @Test
    public void detectIfIOwnAvailability() throws IOException, ServletException {
        mockAvailability(ME);
        mav=callAmendAvailability(1);
        assertTrue((boolean) mav.getModelMap().get("isThisMyAvailability"));

    }

    @Test
    public void detectIfIDontOwnAvailability() throws IOException, ServletException {
        mockAvailability(NOT_ME);
        mav=callAmendAvailability(1);
        assertFalse((boolean) mav.getModelMap().get("isThisMyAvailability"));
    }

    @Test
    public void returnsSharedFriendsList() throws IOException, ServletException {
        Set<User> userList = new HashSet<User>();
        userList.add(user1);
        userList.add(user2);
        Availability expectedAvailability = new Availability(1, ME, MY_NAME, "title", START_DATE, END_DATE,
                "status", userList);
        when(mockAvailabilityFacade.get(anyInt())).thenReturn(expectedAvailability);

        Set<FriendModel> expectedList = setExpectedList();

        mav=callAmendAvailability(1);

        Set<FriendModel> actualList = (Set<FriendModel>) mav.getModelMap().get("friendsSharedAvailability");

        assertTrue(expectedList.equals(actualList));

    }

    public Set<FriendModel> setExpectedList(){
        FriendModel friendModel1 = new FriendModel(user1);
        FriendModel friendModel2 = new FriendModel(user2);
        Set<FriendModel> expectedList = new HashSet<FriendModel>();
        expectedList.add(friendModel1);
        expectedList.add(friendModel2);

        return expectedList;
    }
    public Availability mockAvailability(String owner){
        Availability expectedAvailability = new Availability(owner, MY_NAME, "title", START_DATE, END_DATE, "status");
        expectedAvailability.setId(1);
        when(mockAvailabilityFacade.get(anyInt())).thenReturn(expectedAvailability);
        return expectedAvailability;
    }

    public void setupMocks(){
        setupUserMock();
        setupHttpMocks();
        setupAvailabilityMocks();
        setupAvailabilityCommonModelMock();
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

    public void setupAvailabilityMocks(){
        mockAvailabilityFacade = mock(AvailabilityFacade.class);
        controller.setAvailabilityFacade(mockAvailabilityFacade);
    }

    private void setupAvailabilityCommonModelMock() {
        mockAvailabilityCommonModel = mock(AvailabilityCommonModel.class);
        controller.setAvailabilityCommonModel(mockAvailabilityCommonModel);
    }

    private Map<String, Object> mockExpectedModel() {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        expectedMap.put("section", "availability");
        expectedMap.put("newAvailability", new Availability());
        expectedMap.put("friendList", new ArrayList<FriendModel>());
        when(mockAvailabilityCommonModel.getAttributes(anyString())).thenReturn(expectedMap);
        return expectedMap;
    }

    public ModelAndView callAmendAvailability(int id) throws IOException, ServletException {
        return controller.amendAvailability(id, httpMocks.getModel(),
                httpMocks.getMockHttpServletRequest(), httpMocks.getMockHttpServletResponse());
    }

}
