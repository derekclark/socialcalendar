package uk.co.socialcalendar.stepDefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.interfaceAdapters.controllers.friend.FriendCommonModel;
import uk.co.socialcalendar.interfaceAdapters.controllers.friend.FriendController;
import uk.co.socialcalendar.interfaceAdapters.utilities.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class Friend {

//    @Autowired
//    protected WebApplicationContext wac;
    MockMvc mockMvc;
    private static final String JSP_VIEW = "/WEB-INF/jsp/view/";
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";


    FriendController controller;
    ModelAndView mav;

    SessionAttributes sessionAttributes;
    FriendCommonModel mockFriendCommonModel;
    Model model;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;
    User user;

    ResultActions results;


    public void setup(){
        controller = new FriendController();
        sessionAttributes = new SessionAttributes();
        mockFriendCommonModel = mock(FriendCommonModel.class);
        controller.setSessionAttributes(sessionAttributes);
        controller.setFriendCommonModel(mockFriendCommonModel);
        setupHttpSessions();
    }

    public void setupHttpSessions(){
        model = new ExtendedModelMap();
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("USER_ID")).thenReturn(USER_ID);
    }

    private void setupMockMvc(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(JSP_VIEW);
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }


    @Given("^The friend webpage is available$")
    public void the_friend_webpage_is_available() throws Throwable {
        setup();
        setupMockMvc();
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


