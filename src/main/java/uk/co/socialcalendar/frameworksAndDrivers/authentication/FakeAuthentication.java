package uk.co.socialcalendar.frameworksAndDrivers.authentication;

import uk.co.socialcalendar.interfaceAdapters.models.facebook.FacebookUserData;
import uk.co.socialcalendar.interfaceAdapters.utilities.Authentication;

public class FakeAuthentication implements Authentication {

    boolean authenticated;
    String userFacebookId;
    FacebookUserData fb;

    public FakeAuthentication(){
        fb = new FacebookUserData();
    }

    public String getEmail(){
        return fb.getEmail();
    }

    public String getName(){
        return fb.getName();
    }

    public String getFacebookId(){
        return fb.getId();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated==true;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getUserFacebookId() {
        return userFacebookId;
    }

    public void setUserFacebookId(String userFacebookId) {
        this.userFacebookId = userFacebookId;
    }





}
