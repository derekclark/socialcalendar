package uk.co.socialcalendar.frameworksAndDrivers.authentication;

import org.scribe.model.Token;
import uk.co.socialcalendar.interfaceAdapters.models.facebook.FacebookUserData;

import javax.servlet.http.HttpServletResponse;

public class FakeScribeAdapter implements Oauth {

    public static final String OAUTH_TOKEN = "token";
    public static final String API_SECRET = "API_SECRET";
    private boolean wasGetTokenCalled;
    private boolean wasGetCodeCalled;
    private boolean wasGetResponseCalled;

    public boolean wasGetAuthorizationUrlCalled() {
        return wasGetAuthorizationUrlCalled;
    }

    private boolean wasGetAuthorizationUrlCalled;
    FacebookUserData fb;
    String oauthToken;
    public boolean wasGetTokenCalled() {
        return wasGetTokenCalled;
    }

    public boolean wasGetResponseCalled() {
        return wasGetResponseCalled;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public FakeScribeAdapter(){
        wasGetTokenCalled = false;
        wasGetCodeCalled = false;
        wasGetAuthorizationUrlCalled = false;
        wasGetResponseCalled = false;
        fb = new FacebookUserData();

    }

    @Override
    public FacebookUserData getResponse(Token accessToken, String fbResource, HttpServletResponse response) {
        fb.setEmail("userEmail");
        fb.setName("userName");
        fb.setId("facebookId");
        wasGetResponseCalled = true;
        return fb;
    }

    @Override
    public String getOauthToken() {
        return oauthToken;
    }

    @Override
    public String getAuthorizationUrl(Token token) {
        wasGetAuthorizationUrlCalled = true;
        return "/authorizationUrl";
    }

    @Override
    public Token getToken(String code) {
        wasGetTokenCalled = true;
        return new Token(OAUTH_TOKEN, API_SECRET);
    }

}
