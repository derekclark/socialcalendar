package uk.co.socialcalendar.stepDefs;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.co.socialcalendar.interfaceAdapters.controllers.friend.FriendController;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


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


    }

    @When("^I select the friend page$")
    public void i_select_the_friend_page() throws Throwable {

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

}


