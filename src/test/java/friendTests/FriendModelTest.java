package friendTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModel;

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


//     @Test
//     public void canGetAndSetBeFriendedEmail(){
//         model.setBeFriendedEmail(EMAIL);
//         assertEquals(EMAIL,model.getBeFriendedEmail());
//     }

     @Test
     public void canGetAndSetName(){
         model.setName(EMAIL);
         assertEquals(EMAIL,model.getName());

     }


 }
