package uk.co.socialcalendar.authentication.facebookAuth;

import org.scribe.model.OAuthRequest;

public interface OauthRequestResource {
    public OAuthRequest getFacebookUserData(String resource);
}
