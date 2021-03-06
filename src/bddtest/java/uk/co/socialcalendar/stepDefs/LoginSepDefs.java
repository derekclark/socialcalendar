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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import uk.co.socialcalendar.user.entities.User;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/resources/servlet-context.xml"})
public class LoginSepDefs {
    @Autowired SpringHolder springHolder;
    @Autowired PopulateDatabase populateDatabase;
    @Autowired private WebApplicationContext wac;

    private MockMvc mockMvc;
    HttpSession session;
    ResultActions results;

    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";
    public static final String MY_USER_ID = "me";
    public static final String MY_USER_PASSWORD = "pass1";
    public static final String MY_FACEBOOK_ID = "100008173740345";
    public static final String TOKEN = "token";
    public static final String MY_NAME = "my name";

    User myUser;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Given("^I am a registered user$")
    public void i_am_a_registered_user() throws Throwable {
        myUser = new User(MY_USER_ID, MY_NAME, MY_FACEBOOK_ID);
        populateDatabase.populateUser(myUser);
    }

    @Given("^I have logged in with valid credentials$")
    public void i_have_logged_in_with_valid_credentials() throws Throwable {
        ResultActions auth =this.mockMvc.perform(
                MockMvcRequestBuilders.post("/fakelogin").param("userId", MY_USER_ID).param("password", MY_USER_PASSWORD));
        MvcResult result = auth.andReturn();
        MockHttpSession mockHttpSession = (MockHttpSession)result.getRequest().getSession();
        springHolder.setSession(mockHttpSession);
        springHolder.setMockMVC(mockMvc);
        springHolder.setResultActions(auth);
    }

    @Then("^I should be authenticated$")
    public void i_should_be_authenticated() throws Throwable {
        MockHttpSession session = (MockHttpSession)springHolder.getSession();
        assertEquals(true, session.getAttribute("IS_AUTHENTICATED"));
        assertEquals(MY_USER_ID,session.getAttribute("USER_ID"));
        assertEquals(TOKEN,session.getAttribute("OAUTH_TOKEN"));
        assertEquals(MY_FACEBOOK_ID,session.getAttribute("MY_FACEBOOK_ID"));
    }

    @Given("^I have logged in with invalid credentials$")
    public void i_have_logged_in_with_invalid_credentials() throws Throwable {
        ResultActions auth = mockMvc.perform(
                MockMvcRequestBuilders.post("/fakelogin").param("userId", MY_USER_ID).param("password", "wrong password"))
                .andDo(MockMvcResultHandlers.print());
        MvcResult result = auth.andReturn();
        MockHttpSession mockHttpSession = (MockHttpSession)result.getRequest().getSession();
        springHolder.setSession(mockHttpSession);
        springHolder.setMockMVC(mockMvc);
        springHolder.setResultActions(auth);
    }

    @Then("^I should not be authenticated$")
    public void i_should_not_be_authenticated() throws Throwable {
        MockHttpSession session = (MockHttpSession)springHolder.getSession();
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

    @Then("^the \"(.*?)\" view is rendered$")
    public void the_view_is_returned(String expectedView) throws Throwable {
        results = springHolder.getResultActions();
        results.andExpect(view().name(expectedView));
    }

    @When("^I select the friend page without authenticating$")
    public void i_select_the_friend_page_without_going_authenticating() throws Throwable {
        RequestBuilder getFriend = MockMvcRequestBuilders.get("/friend");
        results = mockMvc.perform(getFriend)
                .andDo(MockMvcResultHandlers.print());
        springHolder.setResultActions(results);

    }
}
