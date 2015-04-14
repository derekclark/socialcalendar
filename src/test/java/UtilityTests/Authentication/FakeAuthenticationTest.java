package UtilityTests.Authentication;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.frameworksAndDrivers.authentication.FakeAuthentication;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class FakeAuthenticationTest {

    FakeAuthentication authentication;

    @Before
    public void setup(){
        authentication = new FakeAuthentication();
    }

    @Test
    public void shouldAuthenticateUser(){
        authentication.setAuthenticated(true);
        assertTrue(authentication.isAuthenticated());
    }

    @Test
    public void shouldNotAuthenticateUser(){
        authentication.setAuthenticated(false);
        assertFalse(authentication.isAuthenticated());
    }

    @Test
    public void shouldGetUserFacebookId(){
        authentication.setUserFacebookId("123");
        assertEquals("123", authentication.getUserFacebookId());
    }



//    @Test
//    public void shouldGetOauthToken(){
//        authentication.setOauthToken("456");
//        assertEquals("456", authentication.getOauthToken());
//    }
}
