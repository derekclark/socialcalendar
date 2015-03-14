package uk.co.socialcalendar.frameworksAndDrivers.authentication;

import org.scribe.model.Token;
import uk.co.socialcalendar.interfaceAdapters.models.facebook.FacebookUserData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Oauth {
    public FacebookUserData getResponse(Token accessToken, String fbResource, HttpServletResponse response) throws IOException;

    public String getOauthToken();

    public String getAuthorizationUrl(Token token);
    public Token getToken(String code);

}
