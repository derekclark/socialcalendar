package friendTests;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.interfaceAdapters.controllers.FriendCommonModel;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModel;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModelFacade;
import uk.co.socialcalendar.interfaceAdapters.utilities.AuthenticationFacade;
import uk.co.socialcalendar.useCases.FriendFacade;
import uk.co.socialcalendar.useCases.FriendFacadeImpl;
import uk.co.socialcalendar.useCases.UserFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FriendCommonModelTest {

    FriendCommonModel friendCommonModel;
    FriendFacade mockFriendFacade;
    AuthenticationFacade mockAuthenticationFacade;
    UserFacade mockUserFacade;
    FriendModelFacade mockFriendModelFacade;
    ModelAndView mav;
    User user;
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String FACEBOOK_ID = "facebookId";
    public static final String USER_ID = "userId";
    public static final String FRIENDS_PAGE_SECTION = "friends";
    public static final String USER_FACEBOOK_ID = "123";
    public static final String OAUTH_TOKEN = "456";

    @Before
    public void setup(){
        friendCommonModel = new FriendCommonModel();
        mockFriendFacade = mock(FriendFacadeImpl.class);
        mockFriendModelFacade = mock(FriendModelFacade.class);
        mockAuthenticationFacade = mock(AuthenticationFacade.class);
        mockUserFacade = mock(UserFacade.class);
        friendCommonModel.setUserFacade(mockUserFacade);
        friendCommonModel.setAuthenticationFacade(mockAuthenticationFacade);
        friendCommonModel.setFriendFacade(mockFriendFacade);
        friendCommonModel.setFriendModelFacade(mockFriendModelFacade);

        setupUserMock();
    }

    public void setupUserMock(){
        user = new User(EMAIL, NAME, FACEBOOK_ID);
        when(mockUserFacade.getUser(anyString())).thenReturn(user);
    }

    @Test
    public void canCreateInstance(){
        assertTrue(friendCommonModel instanceof FriendCommonModel);
    }

    @Test
    public void populatesFriendListAttribute(){
        List<FriendModel> expectedFriendModelList = create1Friend();
        when(mockFriendModelFacade.getFriendModelList(anyString())).thenReturn(expectedFriendModelList);

        Map<String, Object> actualModelMap = friendCommonModel.getFriendList(USER_ID);
        assertEquals(expectedFriendModelList, actualModelMap.get("friendList"));
    }

    @Test
    public void populateSectionAttribute(){
        Map<String, Object> actualModelMap = friendCommonModel.getSection();
        assertEquals(FRIENDS_PAGE_SECTION, actualModelMap.get("section"));

    }

    @Test
    public void populateNewFriendAttribute(){
        Map<String, Object> actualModelMap = friendCommonModel.getNewFriend();
        assertEquals(new Friend(), actualModelMap.get("newFriend"));
    }

    @Test
    public void populateAuthenticatedAttribute(){
        Map<String, Object> actualModelMap = createAuthenticationModel();
        when(mockAuthenticationFacade.getAuthenticationAttrbutes()).thenReturn(actualModelMap);
        assertEquals(true, actualModelMap.get("isAuthenticated"));
    }

    @Test
    public void populateFacebookIdModelAttribute(){
        Map<String, Object> actualModelMap = createAuthenticationModel();
		when(mockAuthenticationFacade.getAuthenticationAttrbutes()).thenReturn(actualModelMap);
        assertEquals(USER_FACEBOOK_ID,actualModelMap.get("userFacebookId"));
    }

    @Test
    public void populateOauthTokenAttribute(){
        Map<String, Object> actualModelMap = createAuthenticationModel();
		when(mockAuthenticationFacade.getAuthenticationAttrbutes()).thenReturn(actualModelMap);
        assertEquals(OAUTH_TOKEN,actualModelMap.get("oauthToken"));
    }




    private Map<String, Object> createAuthenticationModel() {
        Map<String, Object> commonModelMap = new HashMap<String, Object>();
        commonModelMap.put("isAuthenticated", true);
        commonModelMap.put("userFacebookId", USER_FACEBOOK_ID);
        commonModelMap.put("oauthToken", OAUTH_TOKEN);
        return commonModelMap;
    }

    @Test
    public void populateFriendRequestsAttribute(){
        List<Friend> expectedFriendRequests = setExpectedRequestList();

		when(mockFriendFacade.getFriendRequests(anyString())).thenReturn(expectedFriendRequests);
        Map<String, Object> actualModelMap = friendCommonModel.getFriendRequests(USER_ID);
        assertEquals(expectedFriendRequests, actualModelMap.get("friendRequests"));

    }

    private List<Friend> setExpectedRequestList() {
        List<Friend> expectedFriendRequests = new ArrayList<Friend>();
        expectedFriendRequests.add(new Friend("name2",USER_ID, FriendStatus.PENDING));
        expectedFriendRequests.add(new Friend("name3",USER_ID, FriendStatus.PENDING));
        return expectedFriendRequests;
    }



    private List<FriendModel> create1Friend() {
        List<FriendModel>expectedFriendModelList = new ArrayList<FriendModel>();
        FriendModel friendModel = new FriendModel();
        friendModel.setName(NAME);
        friendModel.setEmail(EMAIL);
        friendModel.setFriendId(1);
        friendModel.setFacebookId(USER_FACEBOOK_ID);
        friendModel.setHasFacebookId(true);
        expectedFriendModelList.add(friendModel);
        return expectedFriendModelList;
    }


    @Test
    public void populateUserNameInModel(){
        Map<String, Object> actualModelMap = friendCommonModel.getUserName(USER_ID);
        assertEquals("name",actualModelMap.get("userName"));
    }

    @Test
    public void returnsAllAttributes(){
        Map<String, Object> actualModelMap = createAuthenticationModel();
        when(mockAuthenticationFacade.getAuthenticationAttrbutes()).thenReturn(actualModelMap);

        Map<String, Object> actualMap = friendCommonModel.getCommonModelAttributes(USER_ID);
        assertNotNull(actualMap.get("userName"));
        assertNotNull(actualMap.get("friendList"));
        assertNotNull(actualMap.get("friendRequests"));
        assertNotNull(actualMap.get("isAuthenticated"));
        assertNotNull(actualMap.get("oauthToken"));
        assertNotNull(actualMap.get("userFacebookId"));
        assertNotNull(actualMap.get("newFriend"));
        assertNotNull(actualMap.get("section"));
    }
}
