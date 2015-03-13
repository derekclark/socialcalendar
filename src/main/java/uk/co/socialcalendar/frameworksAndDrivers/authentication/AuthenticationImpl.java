package uk.co.socialcalendar.frameworksAndDrivers.authentication;

import uk.co.socialcalendar.interfaceAdapters.utilities.Authentication;

public class AuthenticationImpl implements Authentication {


    public AuthenticationImpl(){
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public String getUserFacebookId() {
        return null;
    }


}
