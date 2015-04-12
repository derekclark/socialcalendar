package friendTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModel;

import static org.junit.Assert.assertEquals;
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
 }
