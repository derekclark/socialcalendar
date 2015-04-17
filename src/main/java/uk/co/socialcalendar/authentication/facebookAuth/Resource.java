package uk.co.socialcalendar.authentication.facebookAuth;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

public class Resource implements OauthRequestResource {
    @Override
    public OAuthRequest getFacebookUserData(String fbResource) {
        return new OAuthRequest(Verb.GET, fbResource);
    }
}
