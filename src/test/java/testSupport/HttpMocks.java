package testSupport;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import uk.co.socialcalendar.authentication.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpMocks {
    private static final String ME = "me";
    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    SessionAttributes mockSessionAttributes;

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
    }

    public void setupMockSessionAttributes(){
        mockSessionAttributes = mock(SessionAttributes.class);
        when(mockSessionAttributes.getLoggedInUserId((HttpServletRequest) anyObject())).thenReturn(ME);
    }

}
