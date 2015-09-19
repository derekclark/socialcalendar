package newsFeed;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import testSupport.HttpMocks;
import uk.co.socialcalendar.newsFeed.NewsFeedController;
import uk.co.socialcalendar.newsFeed.NewsFeedFacade;
import uk.co.socialcalendar.newsFeed.NewsFeedLine;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    NewsFeedLine newsFeedLine;
    NewsFeedFacade mockNewsFeedFacade;
    UserFacade mockUserFacade;

    @Before
    public void setup(){
        controller = new NewsFeedController();
        setupHttpMocks();
        mockNewsFeedFacade = mock(NewsFeedFacade.class);
        controller.setNewsFeedFacade(mockNewsFeedFacade);
    }

    public void setupHttpMocks(){
        httpMocks = new HttpMocks();
        controller.setSessionAttributes(httpMocks.getMockSessionAttributes());
        controller.setUserFacade(httpMocks.getMockUserFacade());
    }

    @Test
    public void rendersNewsFeedView() throws IOException, ServletException {
        mav = callNewsFeed();
        assertEquals("newsFeed",mav.getViewName());
    }

    @Test
    public void returnsEmptyNewsFeedIfNoAvailabilities() throws IOException, ServletException {
        List<NewsFeedLine> emptyNewsFeed = new ArrayList<NewsFeedLine>();
        mav = callNewsFeed();
        assertEquals(emptyNewsFeed,mav.getModelMap().get("newsFeedLines"));
    }

    @Test
    public void returnsNewsFeedLine() throws IOException, ServletException {
        List<NewsFeedLine> newsFeedLineList = new ArrayList<NewsFeedLine>();
        NewsFeedLine newsFeedLine = new NewsFeedLine();
        newsFeedLineList.add(newsFeedLine);
        when(mockNewsFeedFacade.getNewsFeed(anyString())).thenReturn(newsFeedLineList);

        mav = callNewsFeed();
        assertEquals(newsFeedLineList, mav.getModelMap().get("newsFeedLines"));
    }

    public ModelAndView callNewsFeed() throws IOException, ServletException {
        return controller.showNewsFeed(httpMocks.getModel(),
                httpMocks.getMockHttpServletRequest(), httpMocks.getMockHttpServletResponse());

    }
}
