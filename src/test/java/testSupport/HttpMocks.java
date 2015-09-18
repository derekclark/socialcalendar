package testSupport;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpMocks {
    private static final String ME = "me";
    private static final String MY_NAME = "myName";
    private static final String MY_FACEBOOK_ID = "facebookId";

    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    SessionAttributes mockSessionAttributes;

    UserFacade mockUserFacade;

    public UserFacade getMockUserFacade() {
        return mockUserFacade;
    }

    public void setMockUserFacade(UserFacade mockUserFacade) {
        this.mockUserFacade = mockUserFacade;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public HttpServletRequest getMockHttpServletRequest() {
        return mockHttpServletRequest;
    }

    public void setMockHttpServletRequest(HttpServletRequest mockHttpServletRequest) {
        this.mockHttpServletRequest = mockHttpServletRequest;
    }

    public HttpServletResponse getMockHttpServletResponse() {
        return mockHttpServletResponse;
    }

    public void setMockHttpServletResponse(HttpServletResponse mockHttpServletResponse) {
        this.mockHttpServletResponse = mockHttpServletResponse;
    }

    public SessionAttributes getMockSessionAttributes() {
        return mockSessionAttributes;
    }

    public void setMockSessionAttributes(SessionAttributes mockSessionAttributes) {
        this.mockSessionAttributes = mockSessionAttributes;
    }

    public HttpMocks(){
        model = new ExtendedModelMap();
        setupMockSessionAttributes();
        setupUserMock();
    }

    public void setupUserMock(){
        mockUserFacade = mock(UserFacade.class);
        User user = new User(ME, MY_NAME, "MY_FACEBOOK_ID");
        when(mockUserFacade.getUser(anyString())).thenReturn(user);
    }

    public void setupMockSessionAttributes(){
        mockSessionAttributes = mock(SessionAttributes.class);
        when(mockSessionAttributes.getLoggedInUserId((HttpServletRequest) anyObject())).thenReturn(ME);
    }

}
