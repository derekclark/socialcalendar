package UtilityTests;

import org.junit.Before;
import org.junit.Test;
import org.scribe.model.OAuthRequest;
import org.scribe.oauth.OAuthService;
import uk.co.socialcalendar.frameworksAndDrivers.FacebookOauth;
import uk.co.socialcalendar.frameworksAndDrivers.FakeHttpSession;
import uk.co.socialcalendar.frameworksAndDrivers.StubFacebookAuthCode;
import uk.co.socialcalendar.frameworksAndDrivers.StubFacebookVerifier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FacebookOauthTest {

    public final static String OAUTH_CODE = "OAUTH_CODE";
    public final static String OAUTH_TOKEN = "OAUTH_TOKEN";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_NAME = "userName";
    public static final String FACEBOOK_ID = "facebookId";

    FacebookOauth facebook;
    String apiKey, apiSecret, callback;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
//    HttpSession mockSession;
    OAuthService mockService;
    OAuthRequest mockOauthRequest;
    StubFacebookResponse stubFacebookResponse;
    StubFacebookVerifier stubFacebookVerifier;
    StubFacebookAuthCode stubFacebookAuthCode;
    FakeHttpSession fakeHttpSession;

    @Before
    public void setup() {
        apiKey="1";
        apiSecret="1";
        callback = "1";
        facebook = new FacebookOauth(apiKey, apiSecret, callback);

        stubFacebookResponse = new StubFacebookResponse();
        stubFacebookVerifier = new StubFacebookVerifier();
        stubFacebookAuthCode = new StubFacebookAuthCode();
        fakeHttpSession = new FakeHttpSession();
        facebook.setFacebookResponse(stubFacebookResponse);
        facebook.setFacebookVerifier(stubFacebookVerifier);
        facebook.setFacebookAuthCode(stubFacebookAuthCode);
        facebook.setHttpSession(fakeHttpSession);
        setupHttpSessions();
        setupMocks();
    }

    public void setupHttpSessions(){
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);

        mockService = mock(OAuthService.class);
        mockOauthRequest = mock(OAuthRequest.class);
//        mockHttpServletResponse = mock(HttpServletResponse.class);
//        mockSession = mock(HttpSession.class);
        when(mockHttpServletRequest.getSession()).thenReturn(fakeHttpSession);
        facebook.setOauthRequest(mockOauthRequest);
    }

    public void setupMocks(){

    }

    @Test
    public void canCreateInstance(){
        assertTrue(facebook instanceof FacebookOauth);
    }

    @Test
    public void canCreateOauthService(){
        OAuthService service = facebook.createService(apiKey, apiSecret, callback);
        assertTrue(service instanceof OAuthService);
    }

    @Test
    public void detectStringIsNotEmpty(){
        assertTrue(facebook.isSet("set"));
    }

    @Test
    public void detectStringIsEmpty(){
        assertFalse(facebook.isSet(""));
    }

    @Test
    public void detectStringIsNull(){
        assertFalse(facebook.isSet(null));
    }

    @Test
    public void willGetTokenIfNotSet() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(OAUTH_CODE)).thenReturn(OAUTH_CODE);
        when(mockHttpServletRequest.getParameter(OAUTH_TOKEN)).thenReturn("");
        facebook.handleRequest(mockHttpServletRequest, mockHttpServletResponse);
        assertTrue(stubFacebookVerifier.wasCalled());
    }

    @Test
    public void getsFacebookDataAfterGettingToken() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(OAUTH_CODE)).thenReturn(OAUTH_CODE);
        when(mockHttpServletRequest.getParameter(OAUTH_TOKEN)).thenReturn("");
        facebook.handleRequest(mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(USER_EMAIL, stubFacebookResponse.getEmail());
        assertEquals(USER_NAME, stubFacebookResponse.getName());
    }

    @Test
    public void willSetSessionVariablesAfterGettingToken() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(OAUTH_CODE)).thenReturn(OAUTH_CODE);
        when(mockHttpServletRequest.getParameter(OAUTH_TOKEN)).thenReturn("");
        facebook.handleRequest(mockHttpServletRequest, mockHttpServletResponse);
        assertEquals(OAUTH_TOKEN, fakeHttpSession.getAttribute(OAUTH_TOKEN));
        assertEquals("true", fakeHttpSession.getAttribute("IS_AUTHENTICATED").toString());
        assertEquals(USER_EMAIL,fakeHttpSession.getAttribute("USER_EMAIL"));
        assertEquals(USER_NAME,fakeHttpSession.getAttribute("USER_NAME"));
        assertEquals(FACEBOOK_ID,fakeHttpSession.getAttribute("FACEBOOK_ID"));

    }

    @Test
    public void willGetFacebookDataIfTokenSet() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(OAUTH_CODE)).thenReturn(OAUTH_CODE);
        when(mockHttpServletRequest.getParameter(OAUTH_TOKEN)).thenReturn(OAUTH_TOKEN);
        facebook.handleRequest(mockHttpServletRequest, mockHttpServletResponse);
        assertTrue(stubFacebookResponse.wasCalled());
    }



    @Test
    public void willGetCodeIfNotSet() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(OAUTH_CODE)).thenReturn("");
        when(mockHttpServletRequest.getParameter(OAUTH_TOKEN)).thenReturn("");
        facebook.handleRequest(mockHttpServletRequest, mockHttpServletResponse);
        assertTrue(stubFacebookAuthCode.wasCalled());
    }

}
