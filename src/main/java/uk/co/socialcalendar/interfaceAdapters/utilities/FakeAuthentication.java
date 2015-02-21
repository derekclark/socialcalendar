package uk.co.socialcalendar.interfaceAdapters.utilities;

public class FakeAuthentication implements Authentication{
    boolean authenticated;
    String userFacebookId;
    String oauthToken;

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

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

}
