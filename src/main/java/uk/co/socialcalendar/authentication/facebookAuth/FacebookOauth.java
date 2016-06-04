package uk.co.socialcalendar.authentication.facebookAuth;

import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.HttpRequestHandler;
import uk.co.socialcalendar.authentication.Oauth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FacebookOauth implements HttpRequestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FacebookOauth.class);
    Oauth oauth;
    HttpSession httpSession;
    public final static String OAUTH_CODE = "code";
    public final static String OAUTH_TOKEN = "token";
    private static final Token EMPTY_TOKEN = null;
    public static final String HOMEPAGE = "/";
    private Token accessToken;
    private String apiSecret;
    @Value("${facebookProtectedResourceUrl}")
    private String facebookProtectedResourceUrl;

    HttpServletRequest request;
    HttpServletResponse response;

    public void setOauth(Oauth oauth) {
        this.oauth = oauth;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public FacebookOauth(String apiKey, String apiSecret, String callback) {
        LOG.info("in FacebookOauth constructor");
        this.apiSecret = apiSecret;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        String token = request.getParameter(OAUTH_TOKEN);
        String code = request.getParameter(OAUTH_CODE);
        LOG.info("in handleRequest; token=" + token + " code=" + code);
        if (isSet(token)) {
            LOG.info("got token");
            handleGetFacebookUserData(response, token);
            return;
        }else if (isSet(code)) {
            LOG.info("got code, get token");
            handleGetToken(response, code);
            redirect(HOMEPAGE, response);
        }else {
            LOG.info("get code");
            handleGetAuthCode(request,response);
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

    private void handleGetAuthCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        getAuthCode();
        String authorizationUrl = oauth.getAuthorizationUrl(EMPTY_TOKEN);
        HttpSession session = request.getSession();
        session.setAttribute("IS_AUTHENTICATED", false);
        LOG.info("in getAuthcode, authorizationUrl=" + authorizationUrl);
        redirect(authorizationUrl, response);
    }

    public FacebookUserData getFacebookUserData(Token token, HttpServletResponse response) throws IOException {
        return oauth.getResponse(accessToken,facebookProtectedResourceUrl, response);
    }

    public boolean isSet(String string){
        if (string == null || string.length()==0){
            return false;
        }
        return true;
    }

    public Token getToken(String code){
        return oauth.getToken(code);
    }

    //put this method in SessionAttributes??????
    public void setSessionAttributes(FacebookUserData fb){
        HttpSession session = request.getSession();
        session.setAttribute("token", accessToken);
        session.setAttribute("IS_AUTHENTICATED", true);
        session.setAttribute("USER_EMAIL", fb.getEmail());
        session.setAttribute("USER_ID", fb.getEmail());
        session.setAttribute("USER_NAME", fb.getName());
        session.setAttribute("FACEBOOK_ID", fb.getId());
    }

    public void redirect(String url, HttpServletResponse response) throws IOException {
        String urlWithSessionID = response.encodeRedirectURL(url);
        response.sendRedirect(urlWithSessionID);
    }
}