package newsFeed;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import testSupport.HttpMocks;
import uk.co.socialcalendar.newsFeed.NewsFeedController;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class NewsFeedControllerTest {
    private static final String END_DATE = "2012-01-27 14:15";
    private static final String START_DATE = "2012-01-25 11:12";
    private static final String TITLE = "title";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String ME = "me";
    private static final String MY_NAME = "myName";

    NewsFeedController controller = new NewsFeedController();
    HttpMocks httpMocks;
    ModelAndView mav;

    @Before
    public void setup(){
        controller = new NewsFeedController();
        setupHttpMocks();
    }

    public void setupHttpMocks(){
        httpMocks = new HttpMocks();
        controller.setSessionAttributes(httpMocks.getMockSessionAttributes());
    }

    @Test
    public void rendersNewsFeedView() throws IOException, ServletException {
        mav = controller.showNewsFeed(httpMocks.getModel(),
                httpMocks.getMockHttpServletRequest(), httpMocks.getMockHttpServletResponse());
        assertEquals("newsFeed",mav.getViewName());
    }
}
