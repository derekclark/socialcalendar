package UtilityTests.Authentication;

import org.junit.Before;
import org.junit.Test;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import uk.co.socialcalendar.frameworksAndDrivers.authentication.AuthenticationImpl;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class AuthenticationImplTest {
    AuthenticationImpl authenticationImpl;
    public final static String apiKey = "1";
    public final static String apiSecret = "1";
    public final static String callback = "1";

    public final static Token EMPTY_TOKEN = null;


    Verifier mockVerifier;
    OAuthService mockService;
    HttpServletResponse mockResponse;
    OAuthRequest mockOauthRequest;


    @Before
    public void setup(){
        authenticationImpl = new AuthenticationImpl();
        mockService = mock(OAuthService.class);
        mockVerifier =  mock(Verifier.class);
        mockOauthRequest = mock(OAuthRequest.class);
        mockResponse = mock(HttpServletResponse.class);
//        authenticationImpl.setService(mockService);
//        authenticationImpl.setVerifier(mockVerifier);
//        authenticationImpl.setOauthRequest(mockOauthRequest);
    }

    @Test
    public void canCreateInstance(){
        assertTrue(authenticationImpl instanceof AuthenticationImpl);
    }

//    @Test
//    public void canGetAuthorizationUrl(){
//        String expected = "authorizationUrl";
//        Token token = new Token("token", "apiSecret");
//        when(mockService.getAuthorizationUrl(EMPTY_TOKEN)).thenReturn("authorizationUrl");
//        String actual = authenticationImpl.getAuthorizationUrl(null);
//        assertEquals(expected, actual);
//    }

//    @Test
//    public void canGetAccessToken(){
//        Token expectedToken = new Token("token", "apiSecret");
//        when(mockService.getAccessToken(EMPTY_TOKEN, mockVerifier)).thenReturn(expectedToken);
//        Token actualToken = authenticationImpl.getToken("");
//        assertEquals(expectedToken, actualToken);
//    }

//    @Test
//    public void getFacebookUserData() throws IOException {
//        Token accessToken = new Token("token", "apiSecret");
//        FacebookUserData fb = new FacebookUserData();
//
//
////        when(mockOauthRequest.send()).thenReturn(response);
//        authenticationImpl.getResponse(accessToken, "", mockResponse);
//    }

}
