package user.entities;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {
    User user;
    private final static String EMAIL = "email";
    private final static String NAME = "name";
    private final static String FACEBOOK_ID = "facebookId";

    @Before
    public void setup(){
        user = new User();
    }
    @Test
    public void canCreateInstance(){
        assertTrue(user instanceof User);
    }

    @Test
    public void canGetAndSetEmail(){
        user.setEmail(EMAIL);
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    public void canGetAndSetName(){
        user.setName(NAME);
        assertEquals(NAME, user.getName());
    }

    @Test
    public void canGetAndSetFacebookId(){
        user.setFacebookId(FACEBOOK_ID);
        assertEquals(FACEBOOK_ID, user.getFacebookId());
    }

}
