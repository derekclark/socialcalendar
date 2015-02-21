package UtilityTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.interfaceAdapters.utilities.AuthenticationFacade;
import uk.co.socialcalendar.interfaceAdapters.utilities.FakeAuthentication;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthenticationFacadeTest {

    AuthenticationFacade facade = new AuthenticationFacade();

    FakeAuthentication fakeAuthentication;
    public static final String USER_FACEBOOK_ID = "123";
    public static final String OAUTH_TOKEN = "456";


    @Before
    public void setup(){
        fakeAuthentication = new FakeAuthentication();
        facade.setAuthentication(fakeAuthentication);

    }

    @Test
    public void canCreateInstance(){
        assertTrue(facade instanceof AuthenticationFacade);
    }

    @Test
    public void addIsAuthenticatedToModel(){

    }

    @Test
    public void shouldSetAuthenticatedModelAttribute(){
        fakeAuthentication.setAuthenticated(true);
        Map<String,Object> map = facade.getAuthenticationAttrbutes();
        assertEquals(true, map.get("isAuthenticated"));
    }

    @Test
    public void shouldSetUserFacebookIdModelAttribute(){
        fakeAuthentication.setUserFacebookId(USER_FACEBOOK_ID);
        Map<String,Object> map = facade.getAuthenticationAttrbutes();
        assertEquals(USER_FACEBOOK_ID, map.get("userFacebookId"));
    }

    @Test
    public void shouldSetOauthTokenModelAttribute(){
        fakeAuthentication.setOauthToken(OAUTH_TOKEN);
        Map<String,Object> map = facade.getAuthenticationAttrbutes();
        assertEquals(OAUTH_TOKEN, map.get("oauthToken"));
    }



}
