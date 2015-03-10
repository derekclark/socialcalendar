package uk.co.socialcalendar.interfaceAdapters.utilities;

import org.scribe.model.Token;
import uk.co.socialcalendar.interfaceAdapters.models.FacebookUserData;

import javax.servlet.http.HttpServletResponse;

public interface Authentication {
    public Token getToken(String code);

    public String getcode();

    public FacebookUserData getResponse(Token accessToken, String fbResource, HttpServletResponse response);

    public boolean isAuthenticated();

    public String getUserFacebookId();

    public String getOauthToken();

    public String getAuthorizationUrl(Token token);

}
