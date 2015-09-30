package friend.controllers;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.user.entities.User;

import static org.junit.Assert.*;

public class FriendModelTest {
     FriendModel model;
     private static final String USER_EMAIL = "userEmail1";
     private static final String USER_NAME = "userName1";
     private static final String USER_FACEBOOK = "userFacebook1";
     private final static int FRIEND_ID = 1;
     private final static String FACEBOOK_ID = "2";
     private final static String EMAIL = "emailAddress";
     private final static User user1 = new User(USER_EMAIL, USER_NAME, USER_FACEBOOK);

    @Before
    public void setup(){
        model = new FriendModel();
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
         model = new FriendModel(user1);
         assertEquals(USER_EMAIL, model.getEmail());
         assertEquals(USER_NAME, model.getName());
         assertEquals(USER_FACEBOOK, model.getFacebookId());
     }

     @Test
     public void testNullEquality(){
         FriendModel fm = new FriendModel(user1);
         FriendModel fm2 = new FriendModel(user1);
         assertNotEquals(fm, null);
     }

    @Test
    public void testEqualitySameObject(){
        FriendModel fm = new FriendModel(user1);
        assertEquals(fm,fm);
    }

    @Test
    public void shouldBeEqualIfSameValues(){
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user1);
        assertEquals(fm,fm2);
    }

    @Test
    public void shouldNotBeEqualIfEmailDifferent(){
        User user2 = new User("", USER_NAME, USER_FACEBOOK);
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user2);
        assertNotEquals(fm,fm2);
    }

    @Test
    public void shouldNotBeEqualIfNameDifferent(){
        User user2 = new User(USER_EMAIL, "", USER_FACEBOOK);
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user2);
        assertNotEquals(fm, fm2);
    }

    @Test
    public void shouldNotBeEqualIfFacebookIdDifferent(){
        User user2 = new User(USER_EMAIL, USER_NAME, "");
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user2);
        assertNotEquals(fm, fm2);
    }

    @Test
    public void testInEquality(){
        User user2 = new User("", USER_NAME, USER_FACEBOOK);
        FriendModel fm = new FriendModel(user1);
        FriendModel fm2 = new FriendModel(user2);
        assertNotEquals(fm, fm2);
    }

}
