package uk.co.socialcalendar.frameworksAndDrivers;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import uk.co.socialcalendar.interfaceAdapters.models.FacebookUserData;
import uk.co.socialcalendar.interfaceAdapters.utilities.Authentication;

import javax.servlet.http.HttpServletResponse;

public class AuthenticationImpl implements Authentication {

    OAuthService service;
    public final static Token EMPTY_TOKEN = null;

    public AuthenticationImpl(String apiKey, String apiSecret, String callback){
        service = createService(apiKey, apiSecret, callback);
    }

    @Override
    public Token getToken(String code) {
        return null;
    }

    @Override
    public String getcode() {
        return null;
    }

    @Override
    public FacebookUserData getResponse(Token accessToken, String fbResource, HttpServletResponse response) {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public String getUserFacebookId() {
        return null;
    }

    @Override
    public String getOauthToken() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(Token token) {
        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        return null;
    }

    public OAuthService createService(String apiKey, String apiSecret, String callback) {
        return new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback(callback)
                .build();
    }

}
