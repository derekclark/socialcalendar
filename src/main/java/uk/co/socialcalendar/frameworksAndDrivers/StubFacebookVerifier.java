package uk.co.socialcalendar.frameworksAndDrivers;

import org.scribe.model.Token;

public class StubFacebookVerifier implements FacebookVerifier{

    public boolean wasCalled() {
        return wasCalled;
    }

    private boolean wasCalled;

    public StubFacebookVerifier(){
        wasCalled = false;
    }
    @Override
    public Token getToken(String code) {
        wasCalled = true;
        return new Token("OAUTH_TOKEN", "API_SECRET");
    }
}
