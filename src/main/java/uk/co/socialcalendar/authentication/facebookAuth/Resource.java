package uk.co.socialcalendar.authentication.facebookAuth;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

public class Resource implements OauthRequestResource {
    @Override
    public OAuthRequest getFacebookResource(String fbResource) {
        return new OAuthRequest(Verb.GET, fbResource);
    }
}
