package uk.co.socialcalendar.frameworksAndDrivers;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.springframework.web.HttpRequestHandler;
import uk.co.socialcalendar.interfaceAdapters.models.FacebookUserData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class FacebookOauth implements HttpRequestHandler {
    public final static String OAUTH_CODE = "OAUTH_CODE";
    public final static String OAUTH_TOKEN = "OAUTH_TOKEN";
    private Token accessToken;
    private String apiSecret;
    private OAuthService service;
    FacebookResponse facebookResponse;
    OAuthRequest oauthRequest;
    FacebookVerifier facebookVerifier;

    FacebookAuthCode facebookAuthCode;
    HttpSession httpSession;

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void setFacebookAuthCode(FacebookAuthCode facebookAuthCode) {
        this.facebookAuthCode = facebookAuthCode;
    }

    public void setFacebookVerifier(FacebookVerifier facebookVerifier) {
        this.facebookVerifier = facebookVerifier;
    }

    public void setFacebookResponse(FacebookResponse facebookResponse) {
        this.facebookResponse = facebookResponse;
    }

    public void setOauthRequest(OAuthRequest oauthRequest) {
        this.oauthRequest = oauthRequest;
    }

    public FacebookOauth(String apiKey, String apiSecret, String callback) {
        service = createService(apiKey, apiSecret, callback);
        this.apiSecret = apiSecret;
    }

    public OAuthService createService(String apiKey, String apiSecret, String callback) {
        return new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback(callback)
                .build();
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter(OAUTH_TOKEN);
        String code = request.getParameter(OAUTH_CODE);
        if (isSet(token)) {
            Token accessToken = new Token(token, apiSecret);
            getFacebookUserData(accessToken, response);
            return;
        }else if (isSet(code)) {
            accessToken = getToken(code);
            FacebookUserData fb = getFacebookUserData(accessToken, response);
            setSessionAttributes(fb);
        }else {
            getAuthCode();
        }
    }

    public FacebookUserData getFacebookUserData(Token token, HttpServletResponse response) {
        return facebookResponse.getResponse(accessToken,"facebookProtectedResourceUrl", response);
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
        return facebookVerifier.getToken(code);
    }

    public String getAuthCode(){
        return facebookAuthCode.getcode();
    }

    public void setSessionAttributes(FacebookUserData fb){
        httpSession.setAttribute("OAUTH_TOKEN", accessToken.getToken());
        httpSession.setAttribute("IS_AUTHENTICATED", true);
        httpSession.setAttribute("USER_EMAIL", fb.getEmail());
        httpSession.setAttribute("USER_NAME", fb.getName());
        httpSession.setAttribute("FACEBOOK_ID", fb.getId());
    }
}