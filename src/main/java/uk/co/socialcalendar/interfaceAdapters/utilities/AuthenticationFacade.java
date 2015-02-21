package uk.co.socialcalendar.interfaceAdapters.utilities;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationFacade {

    Authentication authentication;

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Map<String,Object> getAuthenticationAttrbutes(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        modelMap.put("isAuthenticated", authentication.isAuthenticated());
        modelMap.put("userFacebookId", authentication.getUserFacebookId());
        modelMap.put("oauthToken", authentication.getOauthToken());
        return modelMap;
    }

}
