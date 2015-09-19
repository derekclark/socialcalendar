package availability;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import testSupport.HttpMocks;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.controllers.AvailabilityCommonModel;
import uk.co.socialcalendar.availability.controllers.AvailabilityController;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityControllerTest {
    public static final String ME = "me";
    public static final String MY_NAME = "myName";
    AvailabilityController controller;
    SessionAttributes mockSessionAttributes;
    FriendModelFacade mockFriendModelFacade;
    UserFacade mockUserFacade;
    ModelAndView mav;
    AvailabilityCommonModel mockAvailabilityCommonModel;
    HttpMocks httpMocks;

    @Before
    public void setup(){
        controller = new AvailabilityController();
        setupMocks();

    }

    @Test
    public void rendersAvailabilityView(){
        mav = callAddAvailability();
        assertEquals("availabilityCreate",mav.getViewName());
    }

    @Test
    public void sectionIsAvailability(){
        mockExpectedModel();
        mav = callAddAvailability();
        assertEquals("availability", mav.getModelMap().get("section"));
    }

    @Test
    public void modelReturnsNewAvailabilityAttribute() {
        mockExpectedModel();
        mav = callAddAvailability();
        assertNotNull(mav.getModelMap().get("newAvailability"));
    }

    @Test
    public void modelReturnsFriendList(){
        mockExpectedModel();
        mav = callAddAvailability();
        List<FriendModel> expectedFriendList = setExpectedFriendList();
        when(mockFriendModelFacade.getFriendModelList(ME)).thenReturn(expectedFriendList);
        assertNotNull(mav.getModelMap().get("friendList"));
    }

    private Map<String, Object> mockExpectedModel() {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        expectedMap.put("section", "availability");
        expectedMap.put("newAvailability", new Availability());

        List<FriendModel> expectedFriendList = setExpectedFriendList();
        expectedMap.put("friendList", expectedFriendList);


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

    public ModelAndView callAddAvailability(){
        return controller.addAvailability(httpMocks.getModel(),
                httpMocks.getMockHttpServletRequest(), httpMocks.getMockHttpServletResponse());
    }

    private void setupAvailabilityCommonModelMock() {
        mockAvailabilityCommonModel = mock(AvailabilityCommonModel.class);
        controller.setAvailabilityCommonModel(mockAvailabilityCommonModel);
    }

    public void setupMocks(){
        setupFriendModelFacadeMock();
        setupHttpMocks();
        setupSessionAttributeMock();
        setupUserMock();
        setupAvailabilityCommonModelMock();
    }

    public void setupFriendModelFacadeMock(){
        mockFriendModelFacade = mock(FriendModelFacade.class);
        controller.setFriendModelFacade(mockFriendModelFacade);
    }

    public void setupSessionAttributeMock(){
        mockSessionAttributes = mock(SessionAttributes.class);
        controller.setSessionAttributes(mockSessionAttributes);
        when(mockSessionAttributes.getLoggedInUserId(httpMocks.getMockHttpServletRequest())).thenReturn(ME);
    }

    public void setupUserMock(){
        mockUserFacade = mock(UserFacade.class);
        controller.setUserFacade(mockUserFacade);
        User user = new User(ME, MY_NAME,"facebookId");
        when(mockUserFacade.getUser(ME)).thenReturn(user);
    }

    public void setupHttpMocks(){
        httpMocks = new HttpMocks();
        controller.setSessionAttributes(httpMocks.getMockSessionAttributes());
        controller.setUserFacade(httpMocks.getMockUserFacade());
    }
}
