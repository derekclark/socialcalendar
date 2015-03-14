package uk.co.socialcalendar.interfaceAdapters.utilities;

import uk.co.socialcalendar.frameworksAndDrivers.authentication.Oauth;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationFacade {

    Authentication authentication;
    Oauth oauth;

    public void setOauth(Oauth oauth) {
        this.oauth = oauth;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Map<String,Object> getAuthenticationAttributes(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        modelMap.put("isAuthenticated", authentication.isAuthenticated());
        modelMap.put("userFacebookId", authentication.getUserFacebookId());
        modelMap.put("oauthToken", oauth.getOauthToken());
        return modelMap;
    }

}
