package uk.co.socialcalendar.interfaceAdapters.utilities;

import org.scribe.model.Token;

public interface Auth {
    public Token getToken(String code);

}
