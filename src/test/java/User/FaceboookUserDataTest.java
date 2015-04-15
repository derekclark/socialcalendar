package user;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.authentication.facebookAuth.FacebookUserData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FaceboookUserDataTest {
    public static final int TIMEZONE = 1;
    FacebookUserData facebookUserData;

    @Before
    public void setup(){
        facebookUserData = new FacebookUserData();
    }

    @Test
    public void canGetSetEmail(){
        facebookUserData.setEmail("email");
        assertEquals("email", facebookUserData.getEmail());
    }

    @Test
    public void canGetSetId(){
        facebookUserData.setId("id");
        assertEquals("id",facebookUserData.getId());
    }

    @Test
    public void canGetSetFirstName(){
        facebookUserData.setFirstName("firstName");
        assertEquals("firstName", facebookUserData.getFirstName());
    }

    @Test
    public void canGetSetLastName(){
        facebookUserData.setLastName("lastName");
        assertEquals("lastName", facebookUserData.getLastName());
    }

    @Test
    public void canGetSetGender(){
        facebookUserData.setGender("gender");
        assertEquals("gender", facebookUserData.getGender());
    }

    @Test
    public void canGetSetLink(){
        facebookUserData.setLink("link");
        assertEquals("link", facebookUserData.getLink());
    }

    @Test
    public void canGetSetLocale(){
        facebookUserData.setLocale("locale");
        assertEquals("locale", facebookUserData.getLocale());
    }

    @Test
    public void canGetSetName(){
        facebookUserData.setName("name");
        assertEquals("name", facebookUserData.getName());
    }

    @Test
    public void canGetSetTimeZone(){
        facebookUserData.setTimezone(TIMEZONE);
        assertEquals(TIMEZONE, facebookUserData.getTimezone());
    }

    @Test
    public void canGetSetUpdatedTime(){
        facebookUserData.setUpdatedTime("updatedTime");
        assertEquals("updatedTime", facebookUserData.getUpdatedTime());
    }

    @Test
    public void canGetSetIsVerified(){
        facebookUserData.setVerified(true);
        assertTrue(facebookUserData.isVerified());
    }
}
