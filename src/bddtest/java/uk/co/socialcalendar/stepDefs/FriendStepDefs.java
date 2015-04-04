package uk.co.socialcalendar.stepDefs;

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
import org.springframework.web.context.support.WebApplicationContextUtils;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.interfaceAdapters.controllers.friend.FriendController;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModel;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/test-servlet-context.xml"})
public class FriendStepDefs {
    User ronUser, lisaUser;
    FriendModel ronFriendModel, lisaFriendModel;
    public static final String RON_EMAIL = "ron_email";
    public static final String RON_NAME = "ron";
    public static final String RON_FACEBOOK_ID = "1234";
    public static final String LISA_FACEBOOK_ID = "567";
    public static final String LISA_NAME = "lisa";
    public static final String LISA_EMAIL = "lisa_email";
    HttpSession session;
    @Autowired private SpringHolder springHolder;
    private static final String JSP_VIEW = "/WEB-INF/jsp/view/";
    public static final String USER_ID = "userId";
    @Autowired FriendController friendController;
    ResultActions results;

    @When("^I select the friend page$")
    public void i_select_the_friend_page() throws Throwable {

        MockMvc mockMvc = springHolder.getMockMVC();
        RequestBuilder getFriend = MockMvcRequestBuilders.get("/friend")
                .session((MockHttpSession)springHolder.getSession());

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
        MockMvc mockMvc = springHolder.getMockMVC();

        session = mockMvc.perform(get("/fakelogin"))
                .andReturn()
                .getRequest()
                .getSession();

        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        PopulateDatabase pop = (PopulateDatabase)ctx.getBean("populateDatabase");
        populateUsers(pop);
        populateMyFriends(pop);
    }

    private void populateMyFriends(PopulateDatabase pop) {
        Friend ronFriend = new Friend("me", RON_EMAIL, FriendStatus.ACCEPTED);
        ronFriend.setFriendId(5);
        Friend lisaFriend = new Friend("me", LISA_EMAIL, FriendStatus.ACCEPTED);
        lisaFriend.setFriendId(6);
        pop.populateFriend(ronFriend);
        pop.populateFriend(lisaFriend);
    }

    private void populateUsers(PopulateDatabase pop) {
        ronUser = new User(RON_EMAIL, RON_NAME, RON_FACEBOOK_ID);
        lisaUser = new User(LISA_EMAIL, LISA_NAME, LISA_FACEBOOK_ID);
        pop.populateUser(ronUser);
        pop.populateUser(lisaUser);
    }

    @Then("^Ron and Lisa are shown in my friend list$")
    public void ron_and_Lisa_are_shown_in_my_friend_list() throws Throwable {
        setExpectedFriendModels();

        results = springHolder.getResultActions();
        List<FriendModel> actualFriendList =
                (List<FriendModel>) results.andReturn().getRequest().getAttribute("friendList");

        assertThat(ronFriendModel,isIn(actualFriendList));
        assertThat(lisaFriendModel,isIn(actualFriendList));
    }

    private void setExpectedFriendModels() {
        ronFriendModel = new FriendModel(ronUser);
        lisaFriendModel = new FriendModel(lisaUser);
        ronFriendModel.setFriendId(5);
        lisaFriendModel.setFriendId(6);
    }

}


