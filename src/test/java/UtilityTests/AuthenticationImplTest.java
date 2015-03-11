package UtilityTests;

import org.junit.Before;
import org.junit.Test;
import org.scribe.builder.ServiceBuilder;
import uk.co.socialcalendar.frameworksAndDrivers.AuthenticationImpl;

import static org.junit.Assert.assertTrue;

public class AuthenticationImplTest {
    AuthenticationImpl authenticationImpl;
    public final static String apiKey = "1";
    public final static String apiSecret = "1";
    public final static String callback = "1";

    ServiceBuilder serviceBuilder;

    @Before
    public void setup(){
        authenticationImpl = new AuthenticationImpl(apiKey, apiSecret, callback);
    }

    @Test
    public void canCreateInstance(){
        assertTrue(authenticationImpl instanceof AuthenticationImpl);
    }

//    @Test
//    public void canGetAuthorizationUrl(){
//        String expected = "authUrl";
//        Token token = new Token("token", "apiSecret");
//        String actual = authenticationImpl.getAuthorizationUrl(null);
//        assertEquals(expected, actual);
//    }
}
