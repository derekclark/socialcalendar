package uk.co.socialcalendar.stepDefs;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpSession;

@Component
public class SpringHolder {
    private HttpSession session;

    private ResultActions resultActions;

    private MockMvc mockMVC;

    public MockMvc getMockMVC() {
        return mockMVC;
    }

    public void setMockMVC(MockMvc mockMVC) {
        this.mockMVC = mockMVC;
    }

    public ResultActions getResultActions() {
        return resultActions;
    }

    public void setResultActions(ResultActions resultActions) {
        this.resultActions = resultActions;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}
