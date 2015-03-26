package UtilityTests.Authentication;

import org.junit.Before;
import org.junit.Test;
import org.scribe.model.Token;
import uk.co.socialcalendar.frameworksAndDrivers.FakeHttpSession;
import uk.co.socialcalendar.frameworksAndDrivers.authentication.FacebookOauth;
import uk.co.socialcalendar.frameworksAndDrivers.authentication.FakeAuthentication;
import uk.co.socialcalendar.frameworksAndDrivers.authentication.FakeScribeAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class FacebookOauthTest {
    public final static String OAUTH_CODE = "code";
    public final static String OAUTH_TOKEN = "token";
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
    FakeScribeAdapter fakeScribeAdapter;

    String apiKey, apiSecret, callback;

    @Before
    public void setup() {
        apiKey="1";
        apiSecret="API_SECRET";
        callback = "1";
        facebook = new FacebookOauth(apiKey, apiSecret, callback);
        fakeAuthentication = new FakeAuthentication();
        fakeScribeAdapter = new FakeScribeAdapter();
        facebook.setAuthentication(fakeAuthentication);
        facebook.setOauth(fakeScribeAdapter);
        setupHttpSessions();
    }

    public void setupHttpSessions(){
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        fakeHttpSession = new FakeHttpSession();
        facebook.setHttpSession(fakeHttpSession);
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
        assertTrue(fakeScribeAdapter.wasGetTokenCalled());
    }

    @Test
    public void getsFacebookDataAfterGettingToken() throws ServletException, IOException {
        setupGetTokenCall();
        facebook.handleRequest(mockRequest, mockResponse);
        assertEquals(USER_EMAIL, fakeHttpSession.getAttribute("USER_EMAIL"));
        assertEquals(USER_NAME, fakeHttpSession.getAttribute("USER_NAME"));
    }

    @Test
    public void willSetSessionVariablesAfterGettingToken() throws ServletException, IOException {
        setupGetTokenCall();
        facebook.handleRequest(mockRequest, mockResponse);
        Token expectedToken = new Token(OAUTH_TOKEN, apiSecret);
        assertEquals(expectedToken.toString(), fakeHttpSession.getAttribute(OAUTH_TOKEN).toString());
        assertEquals("true", fakeHttpSession.getAttribute("IS_AUTHENTICATED").toString());
        assertEquals(USER_EMAIL,fakeHttpSession.getAttribute("USER_EMAIL"));
        assertEquals(USER_NAME,fakeHttpSession.getAttribute("USER_NAME"));
        assertEquals(FACEBOOK_ID,fakeHttpSession.getAttribute("FACEBOOK_ID"));
    }

    private void setupGetTokenCall() {
        when(mockRequest.getParameter(OAUTH_CODE)).thenReturn(OAUTH_CODE);
        when(mockRequest.getParameter(OAUTH_TOKEN)).thenReturn("");
//        when(fakeScribeAdapter.getToken(OAUTH_CODE)).thenReturn(new Token(OAUTH_TOKEN, apiSecret));
    }

    @Test
    public void willGetFacebookDataIfTokenSet() throws ServletException, IOException {
        when(mockRequest.getParameter(OAUTH_CODE)).thenReturn(OAUTH_CODE);
        when(mockRequest.getParameter(OAUTH_TOKEN)).thenReturn(OAUTH_TOKEN);
        facebook.handleRequest(mockRequest, mockResponse);
        assertTrue(fakeScribeAdapter.wasGetResponseCalled());
    }

    @Test
    public void willGetAuthCodeIfNotSet() throws ServletException, IOException {
        setupGetCodeCall();
        facebook.handleRequest(mockRequest, mockResponse);
        assertTrue(fakeScribeAdapter.wasGetAuthorizationUrlCalled());
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
