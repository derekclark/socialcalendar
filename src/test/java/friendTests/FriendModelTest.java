package friendTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModel;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class FriendModelTest {
     FriendModel model;
     public static final String USER_EMAIL = "userEmail1";
     public static final String USER_NAME = "userName1";
     public static final String USER_FACEBOOK = "userFacebook1";
     private final static int FRIEND_ID = 1;
     private final static String FACEBOOK_ID = "2";
     private final static String EMAIL = "emailAddress";

    @Before
    public void setup(){
        model = new FriendModel();

    }
    @Test
    public void canCreateInstance(){
        assertTrue(model instanceof FriendModel);
    }

    @Test
    public void canGetAndSetFriendId(){
        model.setFriendId(FRIEND_ID);
        assertEquals(FRIEND_ID,model.getFriendId());
    }

     @Test
     public void canGetAndSetFacebookId(){
         model.setFacebookId(FACEBOOK_ID);
         assertEquals(FACEBOOK_ID,model.getFacebookId());
     }

     @Test
     public void canGetAndSetEmail(){
         model.setEmail(EMAIL);
         assertEquals(EMAIL,model.getEmail());
     }

     @Test
     public void canGetAndSetName(){
         model.setName(EMAIL);
         assertEquals(EMAIL,model.getName());
     }

     @Test
     public void canCreateInstanceFromUserObject(){
         User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
         model = new FriendModel(user1);
         assertEquals(USER_EMAIL, model.getEmail());
         assertEquals(USER_NAME, model.getName());
         assertEquals(USER_FACEBOOK, model.getFacebookId());

     }

     @Test
     public void testNullEquality(){
         User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
         FriendModel fm = new FriendModel(user1);
         FriendModel fm2 = new FriendModel(user1);
         assertFalse(fm.equals(null));
     }

    @Test
    public void testEqualitySameObject(){
        User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
        FriendModel fm = new FriendModel(user1);
        assertTrue(fm.equals(fm));
        assertEquals(fm.hashcode(), fm.hashcode());
    }

    @Test
    public void shouldBeEqualIfSameValues(){
        User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user1);
        assertTrue(fm.equals(fm2));
        assertEquals(fm.hashcode(), fm2.hashcode());
    }

    @Test
    public void shouldNotBeEqualIfEmailDifferent(){
        User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
        User user2 = new User("", USER_NAME, USER_FACEBOOK);
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user2);
        assertFalse(fm.equals(fm2));
        assertNotEquals(fm.hashcode(), fm2.hashcode());
    }

    @Test
    public void shouldNotBeEqualIfNameDifferent(){
        User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
        User user2 = new User(USER_EMAIL, "", USER_FACEBOOK);
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user2);
        assertFalse(fm.equals(fm2));
        assertNotEquals(fm.hashcode(), fm2.hashcode());
    }

    @Test
    public void shouldNotBeEqualIfFacebookIdDifferent(){
        User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
        User user2 = new User(USER_EMAIL, USER_NAME, "");
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user2);
        assertFalse(fm.equals(fm2));
        assertNotEquals(fm.hashcode(), fm2.hashcode());
    }


    @Test
    public void testInEquality(){
        User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
        User user2 = new User("", USER_NAME, USER_FACEBOOK);

        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user2);
        assertFalse(fm.equals(fm2));
        assertNotEquals(fm.hashcode(), fm2.hashcode());

    }

    @Test
    public void testToString(){
        User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);
        FriendModel fm = new FriendModel(user1);
        String expectedString = "name=" + USER_NAME + " email=" + USER_EMAIL + " facebookId=" + USER_FACEBOOK + " friendId=0";
        assertEquals(expectedString, fm.toString());
    }

}
