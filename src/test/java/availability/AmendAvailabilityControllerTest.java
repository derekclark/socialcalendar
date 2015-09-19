package availability;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import testSupport.HttpMocks;
import uk.co.socialcalendar.availability.controllers.AmendAvailabilityController;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AmendAvailabilityControllerTest {
    AmendAvailabilityController controller;
    HttpMocks httpMocks;
    UserFacade mockUserFacade;
    ModelAndView mav;
    String me;
    public static final String ME = "me";
    public static final String MY_NAME = "my name";
    public static final String MY_FACEBOOK_ID = "facebookid";
    private static final LocalDateTime START_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 0);
    private static final LocalDateTime END_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 30);

    @Before
    public void setup(){
        controller = new AmendAvailabilityController();
        setupMocks();
    }

    @Test
    public void returnsSectionInModel() throws IOException, ServletException {
        mav=callAmendAvailability(1);
        assertEquals("availability",mav.getModelMap().get("section"));
    }

    public void setupMocks(){
        setupUserMock();
        setupHttpMocks();
    }

    public void setupHttpMocks(){
        httpMocks = new HttpMocks();
        controller.setSessionAttributes(httpMocks.getMockSessionAttributes());
    }

    public void setupUserMock(){
        mockUserFacade = mock(UserFacade.class);
        controller.setUserFacade(mockUserFacade);
        User user = new User(ME, MY_NAME, MY_FACEBOOK_ID);
        when(mockUserFacade.getUser(ME)).thenReturn(user);
    }

    public ModelAndView callAmendAvailability(int id) throws IOException, ServletException {
        return controller.AmendAvailability(id, httpMocks.getModel(),
                httpMocks.getMockHttpServletRequest(), httpMocks.getMockHttpServletResponse());
    }

}
