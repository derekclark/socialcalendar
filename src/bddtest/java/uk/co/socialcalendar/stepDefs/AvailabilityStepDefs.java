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
import uk.co.socialcalendar.availability.controllers.AddAvailabilityDTO;
import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.friend.entities.FriendStatus;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.testPersistence.TestDatabaseActions;
import uk.co.socialcalendar.friend.controllers.FriendModel;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/resources/servlet-context.xml"})
public class AvailabilityStepDefs {
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

    @Before
    public void setup() throws Throwable {
        mockMvc = webAppContextSetup(wac).build();
        clearDatabase();
    }

    @When("^I select the availability page$")
    public void i_select_the_availability_page() throws Throwable {
        MockMvc mockMvc = springHolder.getMockMVC();
        RequestBuilder getFriend = MockMvcRequestBuilders.get("/availability")
                .session((MockHttpSession) springHolder.getSession());
        results = mockMvc.perform(getFriend)
                .andDo(MockMvcResultHandlers.print());
        springHolder.setResultActions(results);
    }

    @When("^I make a create an availability for \"(.*?)\" and \"(.*?)\"$")
    public void i_make_a_create_an_availability_for_and(String arg1, String arg2) throws Throwable {
        MockMvc mockMvc = springHolder.getMockMVC();

        RequestBuilder postAvailability = MockMvcRequestBuilders.post("/addAvailability")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "title")
                .param("startDate","2015-01-01 11:30")
                .param("endDate","2015-01-01 17:45")
                .sessionAttr("newAvailability",new AddAvailabilityDTO())
                .session((MockHttpSession) springHolder.getSession());
        results = mockMvc.perform(postAvailability)
                .andDo(MockMvcResultHandlers.print());
        springHolder.setResultActions(results);
    }

    public void clearDatabase() throws Throwable {
        databaseActions.clear();
    }

}