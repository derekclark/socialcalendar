package uk.co.socialcalendar.frameworksAndDrivers;

import org.scribe.model.Token;
import uk.co.socialcalendar.interfaceAdapters.utilities.Auth;

public class FakeAuthentication implements Auth{
    public boolean wasGetTokenCalled() {
        return wasGetTokenCalled;
    }

    private boolean wasGetTokenCalled;

    public FakeAuthentication(){
        wasGetTokenCalled = false;
    }

    @Override
    public Token getToken(String code) {
        wasGetTokenCalled = true;
        return new Token("OAUTH_TOKEN", "API_SECRET");
    }
}
