package uk.co.socialcalendar.stepDefs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/test-servlet-context.xml"})

public class LoginSepDefs {
    private MockMvc mockMvc;
    MockHttpServletRequestBuilder getRequest;
    HttpSession session;

    @Autowired private WebApplicationContext wac;

    private static final String JSP_VIEW = "/WEB-INF/jsp/view/";
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";


    ModelAndView mav;
    ResultActions results;

    @Test
    public void testLogin() throws Exception {
        givenIHaveLoggedIn();
        thenIShouldBeAuthenticated();
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

    private void thenIShouldBeAuthenticated() {
        assertEquals(true, session.getAttribute("IS_AUTHENTICATED"));
        assertEquals("userEmail1",session.getAttribute("USER_ID"));
        assertEquals("token",session.getAttribute("OAUTH_TOKEN"));
        assertEquals("100008173740345",session.getAttribute("MY_FACEBOOK_ID"));
    }


}
