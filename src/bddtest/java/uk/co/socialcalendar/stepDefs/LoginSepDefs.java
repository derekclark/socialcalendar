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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/test-servlet-context.xml"})
public class LoginSepDefs {
    public static final String VALID_USER_ID = "me";
    public static final String VALID_USER_PASSWORD = "pass1";
    public static final String MY_FACEBOOK_ID = "100008173740345";
    public static final String TOKEN = "token";
    private MockMvc mockMvc;
    MockHttpServletRequestBuilder getRequest;
    HttpSession session;

    @Autowired SpringHolder springHolder;

    @Autowired private WebApplicationContext wac;
    private static final String JSP_VIEW = "/WEB-INF/jsp/view/";
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";

    ModelAndView mav;
    ResultActions results;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(this.wac).build();
    }

    @Given("^I have friends setup$")
    public void i_have_friends_setup() throws Throwable {
        session = mockMvc.perform(get("/fakelogin"))
//                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andReturn()
                .getRequest()
                .getSession();

        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        PopulateDatabase pop = (PopulateDatabase)ctx.getBean("populateDatabase");
        pop.populateUsers();
        pop.populateFriends();
    }

    @Given("^I have logged in with valid credentials$")
    public void i_have_logged_in_with_valid_credentials() throws Throwable {
        ResultActions auth =this.mockMvc.perform(
                MockMvcRequestBuilders.post("/fakelogin").param("userId", VALID_USER_ID).param("password", VALID_USER_PASSWORD));
        MvcResult result = auth.andReturn();
        MockHttpSession mockHttpSession = (MockHttpSession)result.getRequest().getSession();
        springHolder.setSession(mockHttpSession);
    }

    @Then("^I should be authenticated$")
    public void i_should_be_authenticated() throws Throwable {
        MockHttpSession session = (MockHttpSession)springHolder.getSession();
        assertEquals(true, session.getAttribute("IS_AUTHENTICATED"));
        assertEquals(VALID_USER_ID,session.getAttribute("USER_ID"));
        assertEquals(TOKEN,session.getAttribute("OAUTH_TOKEN"));
        assertEquals(MY_FACEBOOK_ID,session.getAttribute("MY_FACEBOOK_ID"));
    }

    @Given("^I have logged in with invalid credentials$")
    public void i_have_logged_in_with_invalid_credentials() throws Throwable {
        session = mockMvc.perform(post("/fakelogin").param("userId", VALID_USER_ID).param("password", "wrongpassword"))
                .andReturn()
                .getRequest()
                .getSession();

        assertNotNull(session);
    }

    @Then("^I should not be authenticated$")
    public void i_should_not_be_authenticated() throws Throwable {

        assertEquals(null, session.getAttribute("IS_AUTHENTICATED"));
    }

    @Given("^I have not authenticated$")
    public void i_have_not_authenticated() throws Throwable {
    }

    @Then("^I should be redirected to the login page$")
    public void i_should_be_redirected_to_the_login_page() throws Throwable {
        results.andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Then("^friend view is returned$")
    public void friend_view_is_returned() throws Throwable {
        results = springHolder.getResultActions();
        results.andExpect(view().name(FRIEND_VIEW));
    }

    @When("^I select the friend page without going authenticating$")
    public void i_select_the_friend_page_without_going_authenticating() throws Throwable {
        RequestBuilder getFriend = MockMvcRequestBuilders.get("/friend");

        results = mockMvc.perform(getFriend)
                .andDo(MockMvcResultHandlers.print());

        springHolder.setResultActions(results);

    }
}
