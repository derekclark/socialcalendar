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

import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/test-servlet-context.xml"})
public class FriendStepDefs {
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
    FriendModel ronFriendModel, lisaFriendModel, jeremyFriendModel;
    Friend ronFriend, lisaFriend, jeremyFriend;

    public static final String JEREMY_EMAIL = "jeremy_email";
    public static final String JEREMY_NAME = "Jeremy";
    public static final String JERMEY_FACEBOOK_ID = "1234";
    public static final String RON_EMAIL = "ron_email";
    public static final String RON_NAME = "Ron";
    public static final String RON_FACEBOOK_ID = "1234";
    public static final String LISA_FACEBOOK_ID = "567";
    public static final String LISA_NAME = "Lisa";
    public static final String LISA_EMAIL = "lisa_email";

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

    @Then("^the page is shown OK$")
    public void friends_are_shown() throws Throwable {
        results = springHolder.getResultActions();
        results.andExpect(status().isOk());
    }

    @Then("^the section should be \"(.*?)\"$")
    public void the_section_should_be(String section) throws Throwable {
        results = springHolder.getResultActions();
        results.andExpect(model().attribute("section", section));
    }

    @Given("^I have Ron as a \"(.*?)\" friend$")
    public void i_have_Ron_as_a_friend(FriendStatus status) throws Throwable {
        ronFriend = createFriend(MY_EMAIL, RON_EMAIL, status);
    }

    @Given("^I have Lisa as a \"(.*?)\" friend$")
    public void i_have_Lisa_as_a_friend(FriendStatus status) throws Throwable {
        lisaFriend = createFriend(MY_EMAIL, LISA_EMAIL, status);
    }

    @Given("^Jeremy has made a friend request on me$")
    public void jeremy_has_made_a_friend_request_on_me() throws Throwable {
        jeremyFriend = createFriend(JEREMY_EMAIL, MY_EMAIL, FriendStatus.PENDING);
    }

    @Given("^Lisa is a user$")
    public void lisa_is_a_user() throws Throwable {
        lisaUser = createUser(LISA_EMAIL, LISA_NAME, LISA_FACEBOOK_ID);
    }

    @Given("^Ron is a user$")
    public void ron_is_a_user() throws Throwable {
        ronUser = createUser(RON_EMAIL, RON_NAME, RON_FACEBOOK_ID);
    }

    @Given("^Jeremy is a user$")
    public void jeremy_is_a_user() throws Throwable {
        jeremyUser = createUser(JEREMY_EMAIL, JEREMY_NAME, JERMEY_FACEBOOK_ID);
    }

    private User createUser(String email, String name, String facebookId) {
        User user = new User(email, name, facebookId);
        populateDatabase.populateUser(user);
        return user;
    }

    private Friend createFriend(String requester, String beFriended, FriendStatus status) {
        Friend friend = new Friend(requester, beFriended, status);
        friend.setFriendId(populateDatabase.populateFriend(friend));
        return friend;
    }

    @Then("^Ron and Lisa are shown in my friend list$")
    public void ron_and_Lisa_are_shown_in_my_friend_list() throws Throwable {
        ronFriendModel = createFriendModel(ronUser, ronFriend);
        lisaFriendModel = createFriendModel(lisaUser, lisaFriend);

        results = springHolder.getResultActions();
        List<FriendModel> actualFriendList =
                (List<FriendModel>) results.andReturn().getRequest().getAttribute("friendList");

        assertThat(ronFriendModel, isIn(actualFriendList));
        assertThat(lisaFriendModel, isIn(actualFriendList));
    }


    private FriendModel createFriendModel(User user, Friend friend){
        FriendModel friendModel = new FriendModel(user);
        friendModel.setFriendId(friend.getFriendId());
        return friendModel;
    }

    public void clearDatabase() throws Throwable {
        databaseActions.clear();
    }

    @Given("^I have a friend request from Jeremy$")
    public void i_have_a_friend_request_from_Jeremy() throws Throwable {
        jeremyFriend = createFriend(JEREMY_EMAIL, MY_EMAIL, FriendStatus.PENDING);
    }

    @Then("^Jeremy is shown as a friend request$")
    public void jeremy_is_shown_as_a_friend_request() throws Throwable {
        results = springHolder.getResultActions();
        List<Friend> actualFriendList =
                (List<Friend>) results.andReturn().getRequest().getAttribute("friendRequestsMadeOnMe");

        assertThat(jeremyFriend, isIn(actualFriendList));
    }

    @When("^I accept a friend request from Jeremy$")
    public void i_accept_a_friend_request_from_Jeremy() throws Throwable {
        MockMvc mockMvc = springHolder.getMockMVC();
        RequestBuilder acceptFriendRequest = MockMvcRequestBuilders.get("/acceptFriendRequest?id=" + jeremyFriend.getFriendId())
                .session((MockHttpSession) springHolder.getSession());
        results = mockMvc.perform(acceptFriendRequest)
                .andDo(MockMvcResultHandlers.print());
        springHolder.setResultActions(results);
    }

    @Then("^a message is shown \"(.*?)\"$")
    public void a_message_is_shown(String expectedMessage) throws Throwable {
        results = springHolder.getResultActions();
        String actualMessage =
                (String) results.andReturn().getRequest().getAttribute("message");
        assertEquals(expectedMessage,actualMessage);
    }


    @Then("^Ron and Lisa and Jeremy are shown in my friend list$")
    public void ron_and_Lisa_and_Jeremy_are_shown_in_my_friend_list() throws Throwable {
        ronFriendModel = createFriendModel(ronUser, ronFriend);
        lisaFriendModel = createFriendModel(lisaUser, lisaFriend);
        jeremyFriendModel = createFriendModel(jeremyUser, jeremyFriend);
        results = springHolder.getResultActions();
        List<FriendModel> actualFriendList =
                (List<FriendModel>) results.andReturn().getRequest().getAttribute("friendList");

        assertThat(ronFriendModel, isIn(actualFriendList));
        assertThat(lisaFriendModel, isIn(actualFriendList));
        assertThat(jeremyFriendModel, isIn(actualFriendList));
    }

    @When("^I decline a friend request from Jeremy$")
    public void i_decline_a_friend_request_from_Jeremy() throws Throwable {
        MockMvc mockMvc = springHolder.getMockMVC();
        RequestBuilder acceptFriendRequest = MockMvcRequestBuilders.get("/declineFriendRequest?id=" + jeremyFriend.getFriendId())
                .session((MockHttpSession) springHolder.getSession());
        results = mockMvc.perform(acceptFriendRequest)
                .andDo(MockMvcResultHandlers.print());
        springHolder.setResultActions(results);
    }

    @Then("^a message is shown that you have declined jeremy as a friend$")
    public void a_message_is_shown_that_you_have_declined_jeremy_as_a_friend() throws Throwable {
        results = springHolder.getResultActions();
        String actualMessage =
                (String) results.andReturn().getRequest().getAttribute("message");
        assertEquals("You have just declined a friend request from jeremy_email",actualMessage);
    }

    @Then("^Jeremy is not shown as a pending friend request$")
    public void jeremy_is_not_shown_as_a_pending_friend_request() throws Throwable {
        results = springHolder.getResultActions();
        List<FriendModel> actualFriendRequest =
                (List<FriendModel>) results.andReturn().getRequest().getAttribute("friendRequest");
        assertNull(actualFriendRequest);
    }

    @When("^I make a friend request on Jeremy $")
    public void i_make_a_friend_request_on_Jeremy() throws Throwable {
        MockMvc mockMvc = springHolder.getMockMVC();
        RequestBuilder acceptFriendRequest = MockMvcRequestBuilders.post("/addFriend?beFriendedEmail="
                + jeremyUser.getEmail())
                .session((MockHttpSession) springHolder.getSession());
        results = mockMvc.perform(acceptFriendRequest).andDo(MockMvcResultHandlers.print());
        springHolder.setResultActions(results);
    }

    @Then("^Jeremy is shown as a pending friend request$")
    public void jeremy_is_shown_as_a_pending_friend_request() throws Throwable {
        results = springHolder.getResultActions();
        List<Friend> actualFriendRequest =
                (List<Friend>) results.andReturn().getRequest().getAttribute("friendRequestsMadeByMe");

        assertEquals(1,actualFriendRequest.size());
        assertEquals(JEREMY_EMAIL, actualFriendRequest.get(0).getBeFriendedEmail());
    }
}


