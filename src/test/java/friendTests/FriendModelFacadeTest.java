package friendTests;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModel;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModelFacade;
import uk.co.socialcalendar.useCases.friend.FriendFacade;
import uk.co.socialcalendar.useCases.user.UserFacade;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.socialcalendar.entities.FriendStatus.ACCEPTED;

public class FriendModelFacadeTest {
    FriendModelFacade friendModelFacade;
    UserFacade mockUserFacade;
    FriendFacade mockFriendFacade;
    User user1, user2, myUser;
    List<Friend> friendList;

    public final static int FRIEND_ID1 = 1;
    public final static String MY_EMAIL = "myEmail1";
    public final static String BEFRIENDED_EMAIL1 = "friendsEmail1";
    public final static String FACEBOOK_ID1 = "123";
    public final static String FRIEND_NAME1 = "friendName1";
    public final static int FRIEND_ID2 = 2;
    public final static String BEFRIENDED_EMAIL2 = "friendEmail2";
    public final static String FACEBOOK_ID2 = "456";
    public final static String FRIEND_NAME2 = "friendName2";

    FriendModel friendModel;

    @Before
    public void setup(){
        friendModelFacade = new FriendModelFacade();
        friendModel = new FriendModel();
        mockUserFacade = mock(UserFacade.class);
        mockFriendFacade = mock(FriendFacade.class);
        friendModelFacade.setFriendFacade(mockFriendFacade);
        friendModelFacade.setUserFacade(mockUserFacade);
    }

    @Test
    public void setsEmail(){
        create1FriendWithFacebookId();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(MY_EMAIL);
        assertEquals(BEFRIENDED_EMAIL1, actualFriendModelList.get(0).getEmail());
    }

    @Test
    public void setsFriendId(){
        create1FriendWithFacebookId();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(MY_EMAIL);
        assertEquals(FRIEND_ID1, actualFriendModelList.get(0).getFriendId());
    }

    @Test
    public void setsNameWhenIAmTheRequester(){
        create1FriendWithFacebookId();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(MY_EMAIL);
        assertEquals(FRIEND_NAME1, actualFriendModelList.get(0).getName());
    }

    @Test
    public void setsNameWhenIAmTheBeFriended(){
        create1FriendWhereIAmBeFriended();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(MY_EMAIL);
        assertEquals(FRIEND_NAME1, actualFriendModelList.get(0).getName());
    }

    @Test
    public void setsHasFacebookIdIfUserHasOne(){
        create1FriendWithFacebookId();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(MY_EMAIL);
        assertEquals(true, actualFriendModelList.get(0).isHasFacebookId());
    }

    @Test
    public void setsHasFacebookIdToFalseIfUserDoesntHaveOne(){
        create1FriendWithNoFacebookId();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(MY_EMAIL);
        assertEquals(false, actualFriendModelList.get(0).isHasFacebookId());
    }

    @Test
    public void canPopulate2FriendsInModelList(){
        create2Friends();
        List<FriendModel> expectedFriendModelList = create2FriendModels();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(MY_EMAIL);
        assertEquals(expectedFriendModelList, actualFriendModelList);
    }

    @Test
    public void canHandleNullFacebookId(){
        create1FriendWithNullFacebookId();
        List<FriendModel> actualFriendModelList = friendModelFacade.getFriendModelList(MY_EMAIL);
        assertEquals(false, actualFriendModelList.get(0).isHasFacebookId());
    }

    private void create1FriendWithFacebookId() {
        friendList = createAListOf1FriendWhereIAmRequester();
        when(mockFriendFacade.getMyAcceptedFriends(MY_EMAIL)).thenReturn(friendList);
        create1UserWithFacebookId();
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL1)).thenReturn(user1);
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL2)).thenReturn(user2);
    }

    private void create1FriendWhereIAmBeFriended() {
        friendList = createAListOf1FriendWhereIAmBeFriended();
        when(mockFriendFacade.getMyAcceptedFriends(MY_EMAIL)).thenReturn(friendList);
        create1UserWithFacebookId();
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL1)).thenReturn(user1);
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL2)).thenReturn(user2);
    }

    private void create1FriendWithNoFacebookId() {
        friendList = createAListOf1FriendWhereIAmRequester();
        when(mockFriendFacade.getMyAcceptedFriends(MY_EMAIL)).thenReturn(friendList);
        create1UserWithNoFacebookId();
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL1)).thenReturn(user1);
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL2)).thenReturn(user2);
    }

    private void create1FriendWithNullFacebookId() {
        friendList = createAListOf1FriendWhereIAmRequester();
        when(mockFriendFacade.getMyAcceptedFriends(MY_EMAIL)).thenReturn(friendList);
        create1UserWithNullFacebookId();
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL1)).thenReturn(user1);
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL2)).thenReturn(user2);
    }

    public List<Friend> createAListOf1FriendWhereIAmRequester(){
        List<Friend> friendList = new ArrayList<Friend>();
        friendList.add(createFriend(FRIEND_ID1, MY_EMAIL, BEFRIENDED_EMAIL1, ACCEPTED));
        return friendList;
    }

    public List<Friend> createAListOf1FriendWhereIAmBeFriended(){
        List<Friend> friendList = new ArrayList<Friend>();
        friendList.add(createFriend(FRIEND_ID1, BEFRIENDED_EMAIL1, MY_EMAIL, ACCEPTED));
        return friendList;
    }

    public void create1UserWithFacebookId(){
        user1 = new User(BEFRIENDED_EMAIL1, FRIEND_NAME1, FACEBOOK_ID1);
    }

    public void create1UserWithNoFacebookId(){
        user1 = new User(BEFRIENDED_EMAIL1, FRIEND_NAME1, "");
    }

    public void create1UserWithNullFacebookId(){
        user1 = new User(BEFRIENDED_EMAIL1, FRIEND_NAME1, null);
    }

    private void create2Friends() {
        friendList = createAListOf2Friends();
        when(mockFriendFacade.getMyAcceptedFriends(MY_EMAIL)).thenReturn(friendList);
        create2Users();
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL1)).thenReturn(user1);
        when(mockUserFacade.getUser(BEFRIENDED_EMAIL2)).thenReturn(user2);
    }

    public List<Friend> createAListOf2Friends(){
        List<Friend> friendList = new ArrayList<Friend>();
        friendList.add(createFriend(FRIEND_ID1, MY_EMAIL, BEFRIENDED_EMAIL1, ACCEPTED));
        friendList.add(createFriend(FRIEND_ID2, MY_EMAIL, BEFRIENDED_EMAIL2, ACCEPTED));
        return friendList;
    }

    private Friend createFriend(int friendId, String requesterEmail, String beFriendedEmail, FriendStatus status){
        Friend friend = new Friend(requesterEmail, beFriendedEmail, status);
        friend.setFriendId(friendId);
        return friend;
    }

    public void create2Users(){
        user1 = new User(BEFRIENDED_EMAIL1, FRIEND_NAME1, FACEBOOK_ID1);
        user2 = new User(BEFRIENDED_EMAIL2, FRIEND_NAME2, FACEBOOK_ID2);
    }

    private FriendModel createFriendModel(int id, String email, String name, String facebookId, boolean hasFacebookId){
        FriendModel friendModel = new FriendModel();
        friendModel.setFriendId(id);
        friendModel.setFacebookId(facebookId);
        friendModel.setEmail(email);
        friendModel.setName(name);
        friendModel.setHasFacebookId(hasFacebookId);
        return friendModel;
    }

    public List<FriendModel> create2FriendModels(){
        List<FriendModel> expectedFriendModelList = new ArrayList<FriendModel>();
        expectedFriendModelList.add(createFriendModel(FRIEND_ID1, BEFRIENDED_EMAIL1, FRIEND_NAME1, FACEBOOK_ID1, true));
        expectedFriendModelList.add(createFriendModel(FRIEND_ID2, BEFRIENDED_EMAIL2, FRIEND_NAME2, FACEBOOK_ID2, false));
        return expectedFriendModelList;
    }

    @Test
    public void returnFriendsEmailNotMine(){
        String expected = "you";
        Friend friend = new Friend("you", "me", ACCEPTED);
        String actual = friendModelFacade.returnFriendsEmailNotMine(friend, "me");
        assertEquals(expected, actual);

        friend = new Friend("me", "you", ACCEPTED);
        actual = friendModelFacade.returnFriendsEmailNotMine(friend, "me");
        assertEquals(expected, actual);
    }
}
