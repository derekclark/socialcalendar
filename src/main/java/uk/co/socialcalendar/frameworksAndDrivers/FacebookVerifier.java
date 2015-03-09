package uk.co.socialcalendar.frameworksAndDrivers;

import org.scribe.model.Token;

public interface FacebookVerifier {
    public Token getToken(String code);
}
