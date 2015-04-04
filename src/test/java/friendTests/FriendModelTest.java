package friendTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

 public class FriendModelTest {
     FriendModel model;

     private final static int FRIEND_ID = 1;
     private final static String FACEBOOK_ID = "2";
     private final static String EMAIL = "emailAddress";
     private final static String NAME = "name";

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
         User user1 = new User("userEmail1", "userName1", "userFacebook1");
         model = new FriendModel(user1);
         assertEquals("userEmail1", model.getEmail());
         assertEquals("userName1", model.getName());
         assertEquals("userFacebook1", model.getFacebookId());

     }
 }
