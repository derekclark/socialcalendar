package authentication;


import org.junit.Before;
import org.junit.Test;
import org.scribe.model.Verifier;
import uk.co.socialcalendar.authentication.ScribeAdapter;
import uk.co.socialcalendar.authentication.facebookAuth.FacebookUserData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScribeAdapterTest {
    public static final String ID = "1234";
    public static final String FIRST_NAME = "derek";
    public static final String LAST_NAME = "clark";
    public static final String GENDER = "male";
    public static final String LINK = "link";
    public static final String LOCALE = "locale";
    public static final String NAME = "derek clark";
    public static final int TIMEZONE = 1;
    public static final String UPDATED_TIME = "12:00";
    public static final String EMAIL = "dc@mail.com";
    public static final boolean VERIFIED = true;
    ScribeAdapter scribeAdapter;

    @Before
    public void setup(){
        scribeAdapter = new ScribeAdapter();
    }

    @Test
    public void canCreateInstance(){
        assertTrue(scribeAdapter instanceof ScribeAdapter);
    }

//    @Test
//    public void canSetOauthRequest(){
//        OAuthRequest oAuthRequest = new OAuthRequest();
//        scribeAdapter.setOauthRequest(oAuthRequest);
//
//
//    }

    @Test
    public void canSetVerifier(){
        Verifier verifier = new Verifier("code");
        scribeAdapter.setVerifier(verifier);
    }

    @Test
    public void convertFacebookJsonReponseToFacebookObject(){
        String jsonString = "{\"id\":\"" + ID + "\"" +
                ",\"firstName\":\"" + FIRST_NAME + "\"" +
                ",\"lastName\":\"" + LAST_NAME + "\"" +
                ",\"gender\":\"" + GENDER + "\"" +
                ",\"link\":\"" + LINK + "\"" +
                ",\"locale\":\"" + LOCALE + "\"" +
                ",\"name\":\"" + NAME + "\"" +
                ",\"timezone\":\"" + TIMEZONE + "\"" +
                ",\"email\":\"" + EMAIL + "\"" +
                ",\"updatedTime\":\"" + UPDATED_TIME + "\"" +
                ",\"verified\":\"" + VERIFIED + "\"" +
                "}";
        FacebookUserData fb = scribeAdapter.unmarshallToObject(jsonString, FacebookUserData.class);
        assertEquals(ID,fb.getId());
        assertEquals(FIRST_NAME, fb.getFirstName());
        assertEquals(LAST_NAME, fb.getLastName());
        assertEquals(GENDER, fb.getGender());
        assertEquals(LINK, fb.getLink());
        assertEquals(LOCALE, fb.getLocale());
        assertEquals(NAME, fb.getName());
        assertEquals(TIMEZONE, fb.getTimezone());
        assertTrue(fb.isVerified());
        assertEquals(UPDATED_TIME, fb.getUpdatedTime());
        assertEquals(EMAIL, fb.getEmail());
    }
}
