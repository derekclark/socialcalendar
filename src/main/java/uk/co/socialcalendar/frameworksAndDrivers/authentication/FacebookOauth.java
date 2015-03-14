package uk.co.socialcalendar.frameworksAndDrivers.authentication;

import org.scribe.model.Token;
import org.springframework.web.HttpRequestHandler;
import uk.co.socialcalendar.interfaceAdapters.models.facebook.FacebookUserData;
import uk.co.socialcalendar.interfaceAdapters.utilities.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FacebookOauth implements HttpRequestHandler {
    Authentication authentication;
    Oauth oauth;
    HttpSession httpSession;
    public final static String OAUTH_CODE = "OAUTH_CODE";
    public final static String OAUTH_TOKEN = "OAUTH_TOKEN";
    private static final Token EMPTY_TOKEN = null;
    public static final String HOMEPAGE = "/";
    private Token accessToken;
    private String apiSecret;

    public void setOauth(Oauth oauth) {
        this.oauth = oauth;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public FacebookOauth(String apiKey, String apiSecret, String callback) {
        this.apiSecret = apiSecret;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter(OAUTH_TOKEN);
        String code = request.getParameter(OAUTH_CODE);
        if (isSet(token)) {
            handleGetFacebookUserData(response, token);
            return;
        }else if (isSet(code)) {
            handleGetToken(response, code);
            redirect(HOMEPAGE, response);
        }else {
            handleGetAuthCode(response);
            return;
        }
    }

    private void handleGetFacebookUserData(HttpServletResponse response, String token) throws IOException {
        Token accessToken = new Token(token, apiSecret);
        getFacebookUserData(accessToken, response);
    }

    private void handleGetToken(HttpServletResponse response, String code) throws IOException {
        accessToken = getToken(code);
        FacebookUserData fb = getFacebookUserData(accessToken, response);
        setSessionAttributes(fb);
    }

    private void handleGetAuthCode(HttpServletResponse response) throws IOException {
//        getAuthCode();
        String authorizationUrl = oauth.getAuthorizationUrl(EMPTY_TOKEN);
        httpSession.setAttribute("IS_AUTHENTICATED", false);
        redirect(authorizationUrl, response);
    }

    public FacebookUserData getFacebookUserData(Token token, HttpServletResponse response) throws IOException {
        return oauth.getResponse(accessToken,"facebookProtectedResourceUrl", response);
    }

    public boolean isSet(String string){
        if (string == null || string.length()==0){
            return false;
        }
        return true;
    }

    public boolean hasOauthCode(String code) {
        if (code == null){
            return false;
        }
        return true;
    }

    public Token getToken(String code){
        return oauth.getToken(code);
    }

//    public String getAuthCode(){
//        return authentication.getcode();
//    }

    public void setSessionAttributes(FacebookUserData fb){
        httpSession.setAttribute("OAUTH_TOKEN", accessToken.getToken());
        httpSession.setAttribute("IS_AUTHENTICATED", true);
        httpSession.setAttribute("USER_EMAIL", fb.getEmail());
        httpSession.setAttribute("USER_NAME", fb.getName());
        httpSession.setAttribute("FACEBOOK_ID", fb.getId());
    }

    public void redirect(String url, HttpServletResponse response) throws IOException {
        String urlWithSessionID = response.encodeRedirectURL(url);
        response.sendRedirect(urlWithSessionID);
    }
}