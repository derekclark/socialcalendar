package uk.co.socialcalendar.authentication;

import com.google.gson.Gson;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.socialcalendar.authentication.facebookAuth.AbstractFactory;
import uk.co.socialcalendar.authentication.facebookAuth.FacebookUserData;
import uk.co.socialcalendar.authentication.facebookAuth.OauthRequestResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ScribeAdapter implements Oauth {

    @Autowired public AbstractFactory abstractFactory;
    OAuthService service;
    Verifier verifier;
    public final static Token EMPTY_TOKEN = null;

    public void setAbstractFactory(AbstractFactory abstractFactory) {
        this.abstractFactory = abstractFactory;
    }

    public void setVerifier(Verifier verifier) {
        this.verifier = verifier;
    }

    public void setService(OAuthService service) {
        this.service = service;
    }

    public ScribeAdapter(String apiKey, String apiSecret, String callback){
        System.out.println("in scribeAdapter constructor");
        service = createService(apiKey, apiSecret, callback);
    }

    public ScribeAdapter(){
    }

    @Override
    public FacebookUserData getResponse(Token accessToken, String fbResource,
                                        HttpServletResponse response) throws IOException {
        OAuthRequest request = getOauthRequest(fbResource);
        service.signRequest(accessToken, request);
        Response apiResponse = getApiResponse(response, request);
        return unmarshallToObject(apiResponse.getBody(), FacebookUserData.class);
    }

    public Response getApiResponse(HttpServletResponse response, OAuthRequest request) throws IOException {
        Response apiResponse = request.send();
        response.setContentType("application/json");
        response.setStatus(apiResponse.getCode());
        PrintWriter printWriter = response.getWriter();
        printWriter.write(apiResponse.getBody());
        return apiResponse;
    }

    public OAuthRequest getOauthRequest(String fbResource) {
        OauthRequestResource resource = abstractFactory.getOauthRequestResource();
        return resource.getFacebookResource(fbResource);
    }

    public <T> T unmarshallToObject(String jsonString, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
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
