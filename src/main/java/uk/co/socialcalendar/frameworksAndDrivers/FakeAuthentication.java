package uk.co.socialcalendar.frameworksAndDrivers;

import org.scribe.model.Token;
import uk.co.socialcalendar.interfaceAdapters.models.FacebookUserData;
import uk.co.socialcalendar.interfaceAdapters.utilities.Auth;

import javax.servlet.http.HttpServletResponse;

public class FakeAuthentication implements Auth{

    private boolean wasGetTokenCalled;
    private boolean wasGetCodeCalled;


    private boolean wasGetResponseCalled;

    public boolean wasGetTokenCalled() {
        return wasGetTokenCalled;
    }

    public boolean wasGetCodeCalled() {
        return wasGetCodeCalled;
    }

    public boolean wasGetResponseCalled() {
        return wasGetResponseCalled;
    }

    FacebookUserData fb;


    public FakeAuthentication(){
        wasGetTokenCalled = false;
        wasGetCodeCalled = false;
        fb = new FacebookUserData();
    }

    @Override
    public Token getToken(String code) {
        wasGetTokenCalled = true;
        return new Token("OAUTH_TOKEN", "API_SECRET");
    }

    @Override
    public String getcode() {
        wasGetCodeCalled = true;
        return "authUrl";
    }

    @Override
    public FacebookUserData getResponse(Token accessToken, String fbResource, HttpServletResponse response) {
        fb.setEmail("userEmail");
        fb.setName("userName");
        fb.setId("facebookId");
        wasGetResponseCalled = true;
        return fb;
    }

    public String getEmail(){
        return fb.getEmail();
    }

    public String getName(){
        return fb.getName();
    }

    public String getFacebookId(){
        return fb.getId();
    }

}
