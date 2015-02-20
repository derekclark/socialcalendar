package friendTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModel;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModelFacade;
import uk.co.socialcalendar.useCases.FriendFacade;
import uk.co.socialcalendar.useCases.UserFacade;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FriendModelFacadeTest {
    FriendModelFacade friendModelFacade;
    UserFacade mockUserFacade;
    FriendFacade mockFriendFacade;
    User user1, user2;
    List<Friend> friendList;

    public final static int FRIEND_ID1 = 1;
    public final static String USER_EMAIL1 = "userEmail1";
    public final static String BEFRIENDED_EMAIL1 = "beFriendedEmail1";
    public final static String FACEBOOK_ID1 = "123";
    public final static String FRIEND_NAME1 = "friendName1";
    public final static int FRIEND_ID2 = 2;
    public final static String USER_EMAIL2 = "userEmail2";
    public final static String BEFRIENDED_EMAIL2 = "beFriendedEmail2";
    public final static String FACEBOOK_ID2 = "456";
    public final static String FRIEND_NAME2 = "friendName2";


    @Before
    public void setup(){
        friendModelFacade = new FriendModelFacade();
        mockUserFacade = mock(UserFacade.class);
        mockFriendFacade = mock(FriendFacade.class);
        friendModelFacade.setFriendFacade(mockFriendFacade);
        friendModelFacade.setUserFacade(mockUserFacade);
    }

    @Test
    public void canPopulate2FriendsInModelList(){
        create2Friends();
        List<FriendModel>expectedFriendModelList = create2FriendModels();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(USER_EMAIL1);
        assertEquals(expectedFriendModelList, actualFriendModelList);
    }

    private void create2Friends() {
        friendList = createAListOf2Friends();
        when(mockFriendFacade.getConfirmedFriends(USER_EMAIL1)).thenReturn(friendList);
        create2Users();
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL1)).thenReturn(user1);
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL2)).thenReturn(user2);
    }

    public List<Friend> createAListOf2Friends(){
        List<Friend> friendList = new ArrayList<Friend>();
        Friend friend = new Friend();
        friend.setRequesterEmail(USER_EMAIL1);
        friend.setBeFriendedEmail(BEFRIENDED_EMAIL1);
        friend.setStatus(FriendStatus.ACCEPTED);
        friend.setFriendId(FRIEND_ID1);
        friendList.add(friend);

        friend.setRequesterEmail(USER_EMAIL2);
        friend.setBeFriendedEmail(BEFRIENDED_EMAIL2);
        friend.setStatus(FriendStatus.ACCEPTED);
        friend.setFriendId(FRIEND_ID2);
        friendList.add(friend);

        return friendList;

    }

    public void create2Users(){
        user1 = new User();
        user1.setName(FRIEND_NAME1);
        user1.setFacebookId(FACEBOOK_ID1);
        user1.setEmail(BEFRIENDED_EMAIL1);

        user2 = new User();
        user2.setName(FRIEND_NAME2);
        user2.setFacebookId(FACEBOOK_ID2);
        user2.setEmail(BEFRIENDED_EMAIL2);
    }

    public List<FriendModel> create2FriendModels(){
        List<FriendModel> expectedFriendModelList = new ArrayList<FriendModel>();
        FriendModel friendModel = new FriendModel();
        friendModel.setFriendId(FRIEND_ID1);
        friendModel.setFacebookId(FACEBOOK_ID1);
        friendModel.setEmail(BEFRIENDED_EMAIL1);
        friendModel.setName(FRIEND_NAME1);
        expectedFriendModelList.add(friendModel);

        friendModel.setFriendId(FRIEND_ID2);
        friendModel.setFacebookId(FACEBOOK_ID2);
        friendModel.setEmail(BEFRIENDED_EMAIL2);
        friendModel.setName(FRIEND_NAME2);
        expectedFriendModelList.add(friendModel);

        return expectedFriendModelList;
    }
}
