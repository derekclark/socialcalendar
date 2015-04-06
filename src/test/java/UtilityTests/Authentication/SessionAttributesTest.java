package UtilityTests.Authentication;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import uk.co.socialcalendar.interfaceAdapters.utilities.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionAttributesTest {
    private static final String USER_ID = "userId";
    SessionAttributes sessionAttributes;
    Model model;

    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;

    @Before
    public void setup(){
        sessionAttributes = new SessionAttributes();
        setupHttpSessions();
    }

    public void setupHttpSessions(){
        model = new ExtendedModelMap();
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("USER_ID")).thenReturn(USER_ID);

    }

    @Test
    public void canCreateInstance(){
        assertTrue(sessionAttributes instanceof SessionAttributes);
    }

    @Test
    public void getLoggedInUserId(){
        assertEquals("userId", sessionAttributes.getLoggedInUserId(mockHttpServletRequest));
    }
}