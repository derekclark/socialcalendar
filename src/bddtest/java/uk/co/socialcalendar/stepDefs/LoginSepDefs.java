package uk.co.socialcalendar.stepDefs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
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

    @Autowired private WebApplicationContext wac;

    private static final String JSP_VIEW = "/WEB-INF/jsp/view/";
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";
    
    ModelAndView mav;
    ResultActions results;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(this.wac).build();

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
    @Test
    public void testLogin() throws Exception {
        givenIHaveLoggedInWithValidCredentials();
        thenIShouldBeAuthenticated();
    }

    private void givenIHaveLoggedInWithValidCredentials() throws Exception {
        session = mockMvc.perform(post("/fakelogin").param("userId", VALID_USER_ID).param("password", VALID_USER_PASSWORD))
//                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andReturn()
                .getRequest()
                .getSession();

        assertNotNull(session);


    }

    private void thenIShouldBeAuthenticated() {
        assertEquals(true, session.getAttribute("IS_AUTHENTICATED"));
        assertEquals(VALID_USER_ID,session.getAttribute("USER_ID"));
        assertEquals(TOKEN,session.getAttribute("OAUTH_TOKEN"));
        assertEquals(MY_FACEBOOK_ID,session.getAttribute("MY_FACEBOOK_ID"));
    }



}
