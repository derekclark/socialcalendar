package UtilityTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.frameworksAndDrivers.FacebookOauth;
import uk.co.socialcalendar.frameworksAndDrivers.FakeAuthentication;
import uk.co.socialcalendar.frameworksAndDrivers.FakeHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class FacebookOauthTest {

    public final static String OAUTH_CODE = "OAUTH_CODE";
    public final static String OAUTH_TOKEN = "OAUTH_TOKEN";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_NAME = "userName";
    public static final String FACEBOOK_ID = "facebookId";
    public static final String HOMEPAGE = "/";
    public static final String AUTHORIZATION_URL = "/authorizationUrl";

    FacebookOauth facebook;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;
    FakeAuthentication fakeAuthentication;
    FakeHttpSession fakeHttpSession;

    String apiKey, apiSecret, callback;

    @Before
    public void setup() {
        apiKey="1";
        apiSecret="1";
        callback = "1";
        facebook = new FacebookOauth(apiKey, apiSecret, callback);
        fakeAuthentication = new FakeAuthentication();
        fakeHttpSession = new FakeHttpSession();
        facebook.setAuthentication(fakeAuthentication);
        facebook.setHttpSession(fakeHttpSession);
        setupHttpSessions();

    }

    public void setupHttpSessions(){
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        when(mockRequest.getSession()).thenReturn(fakeHttpSession);
    }


    @Test
    public void canCreateInstance(){
        assertTrue(facebook instanceof FacebookOauth);
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
        setupGetTokenCall();
        facebook.handleRequest(mockRequest, mockResponse);
        assertTrue(fakeAuthentication.wasGetTokenCalled());
    }

    @Test
    public void getsFacebookDataAfterGettingToken() throws ServletException, IOException {
        setupGetTokenCall();
        facebook.handleRequest(mockRequest, mockResponse);
        assertEquals(USER_EMAIL, fakeAuthentication.getEmail());
        assertEquals(USER_NAME, fakeAuthentication.getName());
    }

    @Test
    public void willSetSessionVariablesAfterGettingToken() throws ServletException, IOException {
        setupGetTokenCall();
        facebook.handleRequest(mockRequest, mockResponse);
        assertEquals(OAUTH_TOKEN, fakeHttpSession.getAttribute(OAUTH_TOKEN));
        assertEquals("true", fakeHttpSession.getAttribute("IS_AUTHENTICATED").toString());
        assertEquals(USER_EMAIL,fakeHttpSession.getAttribute("USER_EMAIL"));
        assertEquals(USER_NAME,fakeHttpSession.getAttribute("USER_NAME"));
        assertEquals(FACEBOOK_ID,fakeHttpSession.getAttribute("FACEBOOK_ID"));
    }

    private void setupGetTokenCall() {
        when(mockRequest.getParameter(OAUTH_CODE)).thenReturn(OAUTH_CODE);
        when(mockRequest.getParameter(OAUTH_TOKEN)).thenReturn("");
    }

    @Test
    public void willGetFacebookDataIfTokenSet() throws ServletException, IOException {
        when(mockRequest.getParameter(OAUTH_CODE)).thenReturn(OAUTH_CODE);
        when(mockRequest.getParameter(OAUTH_TOKEN)).thenReturn(OAUTH_TOKEN);
        facebook.handleRequest(mockRequest, mockResponse);
        assertTrue(fakeAuthentication.wasGetResponseCalled());
    }

    @Test
    public void willGetCodeIfNotSet() throws ServletException, IOException {
        setupGetCodeCall();
        facebook.handleRequest(mockRequest, mockResponse);
        assertTrue(fakeAuthentication.wasGetCodeCalled());
    }

    private void setupGetCodeCall() {
        when(mockRequest.getParameter(OAUTH_CODE)).thenReturn("");
        when(mockRequest.getParameter(OAUTH_TOKEN)).thenReturn("");
    }

    @Test
    public void afterGettingTokenRedirectToHomePage() throws IOException, ServletException {
        setupGetTokenCall();
        when(mockResponse.encodeRedirectURL(HOMEPAGE)).thenReturn(HOMEPAGE);
        facebook.handleRequest(mockRequest, mockResponse);
        verify(mockResponse).sendRedirect(HOMEPAGE);
    }

    @Test
    public void afterGettingAuthCodeRedirectToAuthorizationUrl() throws ServletException, IOException {
        setupGetCodeCall();
        when(mockResponse.encodeRedirectURL(AUTHORIZATION_URL)).thenReturn(AUTHORIZATION_URL);
        facebook.handleRequest(mockRequest, mockResponse);
        verify(mockResponse).sendRedirect(AUTHORIZATION_URL);
    }

    @Test
    public void afterGettingAuthCodeIsAuthenticatedShouldBeFalse() throws ServletException, IOException {
        setupGetCodeCall();
        when(mockResponse.encodeRedirectURL(AUTHORIZATION_URL)).thenReturn(AUTHORIZATION_URL);
        facebook.handleRequest(mockRequest, mockResponse);
        assertEquals("false", fakeHttpSession.getAttribute("IS_AUTHENTICATED").toString());
    }

}
