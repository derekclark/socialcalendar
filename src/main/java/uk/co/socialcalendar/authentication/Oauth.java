package uk.co.socialcalendar.authentication;

import org.scribe.model.Token;
import uk.co.socialcalendar.authentication.models.FacebookUserData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Oauth {
    public FacebookUserData getResponse(Token accessToken, String fbResource, HttpServletResponse response) throws IOException;
    public String getAuthorizationUrl(Token token);
    public Token getToken(String code);

}
