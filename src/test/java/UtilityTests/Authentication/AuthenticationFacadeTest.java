package UtilityTests.Authentication;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.frameworksAndDrivers.authentication.FakeAuthentication;
import uk.co.socialcalendar.frameworksAndDrivers.authentication.FakeScribeAdapter;
import uk.co.socialcalendar.interfaceAdapters.utilities.AuthenticationFacade;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthenticationFacadeTest {

    AuthenticationFacade facade = new AuthenticationFacade();

    FakeAuthentication fakeAuthentication;
    FakeScribeAdapter fakeScribeAdapter;
    public static final String USER_FACEBOOK_ID = "123";
    public static final String OAUTH_TOKEN = "456";


    @Before
    public void setup(){
        fakeAuthentication = new FakeAuthentication();
        fakeScribeAdapter = new FakeScribeAdapter();
        facade.setAuthentication(fakeAuthentication);
        facade.setOauth(fakeScribeAdapter);

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
        Map<String,Object> map = facade.getAuthenticationAttributes();
        assertEquals(true, map.get("isAuthenticated"));
    }

    @Test
    public void shouldSetUserFacebookIdModelAttribute(){
        fakeAuthentication.setUserFacebookId(USER_FACEBOOK_ID);
        Map<String,Object> map = facade.getAuthenticationAttributes();
        assertEquals(USER_FACEBOOK_ID, map.get("userFacebookId"));
    }

    @Test
    public void shouldSetOauthTokenModelAttribute(){
        fakeScribeAdapter.setOauthToken(OAUTH_TOKEN);
        Map<String,Object> map = facade.getAuthenticationAttributes();
        assertEquals(OAUTH_TOKEN, map.get("oauthToken"));
    }



}