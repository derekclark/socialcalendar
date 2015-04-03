package uk.co.socialcalendar.stepDefs;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.co.socialcalendar.interfaceAdapters.controllers.friend.FriendController;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static uk.co.socialcalendar.stepDefs.HelperAsserts.assertPropertyIsTrue;


@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/test-servlet-context.xml"})
public class FriendStepDefs {


    private MockMvc mockMvc;
    MockHttpServletRequestBuilder getRequest;
    HttpSession session;

    @Autowired private WebApplicationContext wac;

    @Autowired private SpringHolder springHolder;

    private static final String JSP_VIEW = "/WEB-INF/jsp/view/";
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";
    public static final String VALID_USER_ID = "me";
    public static final String VALID_USER_PASSWORD = "pass1";
    public static final String MY_FACEBOOK_ID = "100008173740345";
    public static final String TOKEN = "token";


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

    @Before
    public void setup() throws Exception {

        mockMvc = webAppContextSetup(this.wac).build();

//        session = mockMvc.perform(get("/fakelogin"))
////                .andExpect(status().is(HttpStatus.FOUND.value()))
//                .andReturn()
//                .getRequest()
//                .getSession();
//
//        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
//        PopulateDatabase pop = (PopulateDatabase)ctx.getBean("populateDatabase");
//        pop.populateUsers();
//        pop.populateFriends();

    }

//    @Test
//    public void testFriendPageWhenIAmAuthenticated() throws Exception {
//        givenIHaveLoggedInWithValidCredentials();
//        thenIShouldBeAuthenticated();
//        whenISelectFriendPage();
////        thenSectionShouldBeFriends();
////        thenFriendPageShouldBeShown();
////        thenLoggedInUserShouldBeMe();
////        thenMyFriendListShouldBeShown();
////        thenMyFriendRequestsShouldBeShown();
//    }

//    private void thenIShouldBeAuthenticated() {
//        assertEquals(true, session.getAttribute("IS_AUTHENTICATED"));
//        assertEquals(VALID_USER_ID,session.getAttribute("USER_ID"));
//        assertEquals(TOKEN,session.getAttribute("OAUTH_TOKEN"));
//        assertEquals(MY_FACEBOOK_ID,session.getAttribute("MY_FACEBOOK_ID"));
//    }


    private void thenMyFriendListShouldBeShown() throws Exception {
        assertPropertyIsTrue(results, "friendList", "name", "name3");
        assertPropertyIsTrue(results, "friendList", "facebookId", "100040345");
        assertPropertyIsTrue(results, "friendList", "email", "userEmail3");
        assertPropertyIsTrue(results, "friendList", "name", "name4");
        assertPropertyIsTrue(results, "friendList", "facebookId", "1008173740345");
        assertPropertyIsTrue(results, "friendList", "email", "userEmail4");
    }

    private void thenMyFriendRequestsShouldBeShown() throws Exception {
        assertPropertyIsTrue(results, "friendRequests", "requesterEmail", "userEmail5");
    }

    private void thenLoggedInUserShouldBeMe() throws Exception {
        results.andExpect(model().attribute("userName", "derek clark"));
    }

    private void whenISelectFriendPage() throws Exception {
    }

//    private void givenIHaveLoggedInWithValidCredentials() throws Exception {
//        session = mockMvc.perform(post("/fakelogin").param("userId", VALID_USER_ID).param("password", VALID_USER_PASSWORD))
////                .andExpect(status().is(HttpStatus.FOUND.value()))
//                .andReturn()
//                .getRequest()
//                .getSession();
//
//        assertNotNull(session);
//    }



//    @Given("^The friend webpage is available$")
//    public void the_friend_webpage_is_available() throws Throwable {
//
//        session = mockMvc.perform(get("/fakelogin"))
////                .andExpect(status().is(HttpStatus.FOUND.value()))
//                .andReturn()
//                .getRequest()
//                .getSession();
//    }

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

}


