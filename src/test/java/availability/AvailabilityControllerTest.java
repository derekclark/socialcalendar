package availability;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.controllers.AvailabilityController;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityControllerTest {
    public static final String ME = "me";
    public static final String MY_NAME = "myName";
    AvailabilityController controller;
    SessionAttributes mockSessionAttributes;
    FriendModelFacade mockFriendModelFacade;
    UserFacade mockUserFacade;
    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    ModelAndView mav;

    @Before
    public void setup(){
        controller = new AvailabilityController();
        model = new ExtendedModelMap();
        setupMocks();
        mav = controller.addAvailability(model, mockHttpServletRequest, mockHttpServletResponse);
    }

    public void setupMocks(){
        setupFriendModelFacadeMock();
        mockHttp();
        setupSessionAttributeMock();
        setupUserMock();
    }

    public void setupFriendModelFacadeMock(){
        mockFriendModelFacade = mock(FriendModelFacade.class);
        controller.setFriendModelFacade(mockFriendModelFacade);
    }

    public void setupSessionAttributeMock(){
        mockSessionAttributes = mock(SessionAttributes.class);
        controller.setSessionAttributes(mockSessionAttributes);
        when(mockSessionAttributes.getLoggedInUserId(mockHttpServletRequest)).thenReturn(ME);
    }

    public void setupUserMock(){
        mockUserFacade = mock(UserFacade.class);
        controller.setUserFacade(mockUserFacade);
        User user = new User(ME, MY_NAME,"facebookId");
        when(mockUserFacade.getUser(ME)).thenReturn(user);
    }

    public void mockHttp(){
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
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
        List<FriendModel> expectedFriendList = setExpectedFriendList();
        when(mockFriendModelFacade.getFriendModelList(ME)).thenReturn(expectedFriendList);
        assertNotNull(mav.getModelMap().get("friendList"));
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
