package uk.co.socialcalendar.stepDefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.co.socialcalendar.interfaceAdapters.controllers.friend.FriendController;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModel;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/test-servlet-context.xml"})
public class FriendStepDefs {


    private MockMvc mockMvc;
    MockHttpServletRequestBuilder getRequest;
    HttpSession session;

    @Autowired private WebApplicationContext wac;

    private static final String JSP_VIEW = "/WEB-INF/jsp/view/";
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";


    @Autowired FriendController friendController;
    ModelAndView mav;
    ResultActions results;



    private void setupMockMvc(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(JSP_VIEW);
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(this.wac)
                .setViewResolvers(viewResolver)
                .build();

    }

    @Test
    public void testFriendPage() throws Exception {
        givenIHaveLoggedIn();
        thenIShouldBeAuthenticated();
        whenISelectFriendPage();
        thenFriendPageShouldBeShown();
        thenLoggedInUserShouldBeMe();
        thenMyFriendListShouldBeShown();
        thenSectionShouldBeFriends();
    }

    private void thenIShouldBeAuthenticated() {
        assertEquals(true, session.getAttribute("IS_AUTHENTICATED"));
        assertEquals("userEmail1",session.getAttribute("USER_ID"));
        assertEquals("token",session.getAttribute("OAUTH_TOKEN"));
        assertEquals("100008173740345",session.getAttribute("MY_FACEBOOK_ID"));
    }

    private void thenSectionShouldBeFriends() throws Exception {
        results.andExpect(model().attribute("section", "friends"));
    }

    private void thenMyFriendListShouldBeShown() throws Exception {
        List<FriendModel> expectedFriendList = new ArrayList<FriendModel>();
        FriendModel friendModel = new FriendModel();
        friendModel.setName("name3");
        friendModel.setFacebookId("100040345");
        friendModel.setEmail("userEmail3");
        expectedFriendList.add(friendModel);

        FriendModel friendModel2 = new FriendModel();
        friendModel2.setName("name4");
        friendModel2.setFacebookId("1008173740345");
        friendModel2.setEmail("userEmail4");
        expectedFriendList.add(friendModel2);

        assertPropertyIsTrue("friendList", "name", "name3");
        assertPropertyIsTrue("friendList", "facebookId", "100040345");
        assertPropertyIsTrue("friendList", "email", "userEmail3");
    }

    private void assertPropertyIsTrue(String attribute, String propertyName, String expectedValue) throws Exception {
        results.andExpect(model().attribute(attribute,
                hasItem(
                        allOf(
                                hasProperty(propertyName,is(expectedValue)
                                )
                        )
                )
        )
        );
    }

    private void thenLoggedInUserShouldBeMe() throws Exception {
        results.andExpect(model().attribute("userName", "derek clark"));
    }

    private void thenFriendPageShouldBeShown() throws Exception {
        results.andExpect(status().isOk())
                .andExpect(view().name("friend"));
    }

    private void whenISelectFriendPage() throws Exception {
        results = mockMvc.perform(get("/friend").session((MockHttpSession)session).locale(Locale.ENGLISH))
                .andDo(print());
    }

    private void givenIHaveLoggedIn() throws Exception {
        mockMvc = webAppContextSetup(this.wac).build();

        session = mockMvc.perform(get("/").param("USER_ID", "userEmail1").param("j_password", "user1"))
//                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andReturn()
                .getRequest()
                .getSession();

        assertNotNull(session);

        results = mockMvc.perform(get("/").session((MockHttpSession)session).locale(Locale.ENGLISH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

    }

    @Given("^The friend webpage is available$")
    public void the_friend_webpage_is_available() throws Throwable {
        testFriendPage();

//        setupMockMvc();
    }

    @When("^a request is made$")
    public void a_request_is_made() throws Throwable {
        MockHttpServletRequestBuilder getRequest = get("/friend").accept(MediaType.ALL);
        results = mockMvc.perform(getRequest).andDo(print());
    }

    @Then("^page is shown$")
    public void friends_are_shown() throws Throwable {
        results.andExpect(status().isOk());
    }

    @Then("^friend view is returned$")
    public void friend_view_is_returned() throws Throwable {
        results.andExpect(view().name(FRIEND_VIEW));
    }

    @Given("^The I have friends Ron and Lisa$")
    public void the_I_have_friends_Ron_and_Lisa() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^ron and lisa are shown in my friend list$")
    public void ron_and_lisa_are_shown_in_my_friend_list() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}


