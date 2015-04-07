package uk.co.socialcalendar.stepDefs;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.TestDatabaseActions;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModel;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/test-servlet-context.xml"})
public class FriendStepDefs {
    public static final int JEREMY_FRIEND_ID = 3;
    public static final String MY_EMAIL = "me";
    @Autowired
    private SpringHolder springHolder;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    TestDatabaseActions databaseActions;
    @Autowired
    PopulateDatabase populateDatabase;

    HttpSession session;
    ResultActions results;
    private MockMvc mockMvc;
    User ronUser, lisaUser, jeremyUser;
    FriendModel ronFriendModel, lisaFriendModel;

    public static final String JEREMY_EMAIL = "jeremy_email";
    public static final String JEREMY_NAME = "jeremy";
    public static final String JERMEY_FACEBOOK_ID = "1234";
    public static final String RON_EMAIL = "ron_email";
    public static final String RON_NAME = "ron";
    public static final String RON_FACEBOOK_ID = "1234";
    public static final String LISA_FACEBOOK_ID = "567";
    public static final String LISA_NAME = "lisa";
    public static final String LISA_EMAIL = "lisa_email";
    public static final int RON_FRIEND_ID = 5;
    public static final int LISA_FRIEND_ID = 6;

    @Before
    public void setup() throws Throwable {
        mockMvc = webAppContextSetup(wac).build();
        clearDatabase();
    }

    @When("^I select the friend page$")
    public void i_select_the_friend_page() throws Throwable {
        MockMvc mockMvc = springHolder.getMockMVC();
        RequestBuilder getFriend = MockMvcRequestBuilders.get("/friend")
                .session((MockHttpSession) springHolder.getSession());
        results = mockMvc.perform(getFriend)
                .andDo(MockMvcResultHandlers.print());
        springHolder.setResultActions(results);
    }

    @Then("^the friend page is shown$")
    public void friends_are_shown() throws Throwable {
        results = springHolder.getResultActions();
        results.andExpect(status().isOk());
    }

    @Then("^the section should be friends$")
    public void the_section_should_be_friends() throws Throwable {
        results = springHolder.getResultActions();
        results.andExpect(model().attribute("section", "friends"));
    }

    @Given("^I have friends Ron and Lisa$")
    public void i_have_friends_Ron_and_Lisa() throws Throwable {
        populateUsers();
        populateMyFriends();
    }

    private void populateMyFriends() {
        Friend ronFriend = new Friend(MY_EMAIL, RON_EMAIL, FriendStatus.ACCEPTED);
        ronFriend.setFriendId(RON_FRIEND_ID);
        Friend lisaFriend = new Friend(MY_EMAIL, LISA_EMAIL, FriendStatus.ACCEPTED);
        lisaFriend.setFriendId(LISA_FRIEND_ID);
        Friend jeremyFriend = new Friend(JEREMY_EMAIL, MY_EMAIL, FriendStatus.PENDING);
        jeremyFriend.setFriendId(JEREMY_FRIEND_ID);
        populateDatabase.populateFriend(ronFriend);
        populateDatabase.populateFriend(lisaFriend);
        populateDatabase.populateFriend(jeremyFriend);
    }

    private void populateUsers() {
        ronUser = new User(RON_EMAIL, RON_NAME, RON_FACEBOOK_ID);
        lisaUser = new User(LISA_EMAIL, LISA_NAME, LISA_FACEBOOK_ID);
        jeremyUser = new User(JEREMY_EMAIL, JEREMY_NAME, JERMEY_FACEBOOK_ID);
        populateDatabase.populateUser(ronUser);
        populateDatabase.populateUser(lisaUser);
        populateDatabase.populateUser(jeremyUser);
    }

    @Then("^Ron and Lisa are shown in my friend list$")
    public void ron_and_Lisa_are_shown_in_my_friend_list() throws Throwable {
        setExpectedFriendModels();

        results = springHolder.getResultActions();
        List<FriendModel> actualFriendList =
                (List<FriendModel>) results.andReturn().getRequest().getAttribute("friendList");

        assertFriendModelIsInList(ronFriendModel, actualFriendList);
        assertFriendModelIsInList(lisaFriendModel, actualFriendList);
    }

    private void setExpectedFriendModels() {
        ronFriendModel = new FriendModel(ronUser);
        lisaFriendModel = new FriendModel(lisaUser);
        ronFriendModel.setFriendId(RON_FRIEND_ID);
        lisaFriendModel.setFriendId(LISA_FRIEND_ID);
    }

    public void clearDatabase() throws Throwable {
        databaseActions.clear();
    }

    @Given("^I have a friend request from Jeremy$")
    public void i_have_a_friend_request_from_Jeremy() throws Throwable {
        populateUsers();
        populateMyFriends();
    }

    @Then("^Jeremy is shown as a friend request$")
    public void jeremy_is_shown_as_a_friend_request() throws Throwable {
        results = springHolder.getResultActions();
        List<Friend> actualFriendList =
                (List<Friend>) results.andReturn().getRequest().getAttribute("friendRequests");

        Friend jeremyFriend = new Friend(JEREMY_EMAIL, MY_EMAIL, FriendStatus.PENDING);
        jeremyFriend.setFriendId(JEREMY_FRIEND_ID);

        //This isn't great! Cannot do a - assertThat(jeremyFriend isIn actualFriendList) - because I cannot
        //predict the friendId which hibernate will assign to the friend object
        assertFriendIsInList(jeremyFriend, actualFriendList);
    }

    public boolean assertFriendIsInList(Friend friendToFind, List<Friend> friendListToSearch) {
        for (Friend f : friendListToSearch) {
            if (f.getRequesterEmail().equals(friendToFind.getRequesterEmail()) &&
                    f.getBeFriendedEmail().equals(friendToFind.getBeFriendedEmail()) &&
                    f.getStatus() == friendToFind.getStatus()) {
                return true;
            }
        }
        return false;
    }

    public boolean assertFriendModelIsInList(FriendModel friendToFind, List<FriendModel> friendListToSearch) {
        for (FriendModel f : friendListToSearch) {
            if (f.getEmail().equals(friendToFind.getEmail()) &&
                    f.getFacebookId().equals((friendToFind.getFacebookId())) &&
                    f.getName().equals(friendToFind.getName())){
                return true;
            }
        }
        return false;
    }

}


