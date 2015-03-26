package uk.co.socialcalendar.frameworksAndDrivers.authentication;

import com.google.gson.Gson;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import uk.co.socialcalendar.interfaceAdapters.models.facebook.FacebookUserData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ScribeAdapter implements Oauth {

    OAuthService service;

    OAuthRequest oauthRequest;
    public final static Token EMPTY_TOKEN = null;

    public void setOauthRequest(OAuthRequest oauthRequest) {
        this.oauthRequest = oauthRequest;
    }

    public void setVerifier(Verifier verifier) {
        this.verifier = verifier;
    }

    Verifier verifier;
    public void setService(OAuthService service) {
        this.service = service;
    }

    public ScribeAdapter(String apiKey, String apiSecret, String callback){
        service = createService(apiKey, apiSecret, callback);
    }

    public ScribeAdapter(){

    }

    @Override
    public FacebookUserData getResponse(Token accessToken, String fbResource, HttpServletResponse response) throws IOException {
        OAuthRequest request = new OAuthRequest(Verb.GET, fbResource);
        service.signRequest(accessToken, request);
        Response apiResponse = request.send();
        response.setContentType("application/json");
        response.setStatus(apiResponse.getCode());
        response.getWriter().write(apiResponse.getBody());
        Gson gson = new Gson();
        FacebookUserData fb = gson.fromJson(apiResponse.getBody(), FacebookUserData.class);
        return fb;

    }

    @Override
    public String getOauthToken() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(Token token) {
        return service.getAuthorizationUrl(EMPTY_TOKEN);

    }

    @Override
    public Token getToken(String code) {

        Verifier verifier = new Verifier(code);
        return service.getAccessToken(EMPTY_TOKEN, verifier);

    }

    public OAuthService createService(String apiKey, String apiSecret, String callback) {
        System.out.println("createService!!!!!!!!!!!! apikey=" + apiKey + " apisecret=" + apiSecret);

        return new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback(callback)
                .build();
    }


}
