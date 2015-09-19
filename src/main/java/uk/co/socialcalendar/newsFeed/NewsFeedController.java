package uk.co.socialcalendar.newsFeed;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsFeedController {
    SessionAttributes sessionAttributes;
    NewsFeedFacade newsFeedFacade;
    UserFacade userFacade;
    String me;

    public NewsFeedFacade getNewsFeedFacade() {
        return newsFeedFacade;
    }

    public void setNewsFeedFacade(NewsFeedFacade newsFeedFacade) {
        this.newsFeedFacade = newsFeedFacade;
    }

    public SessionAttributes getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @RequestMapping(value = "newsFeed", method = RequestMethod.GET)
    public ModelAndView showNewsFeed(
            Model m,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        me = sessionAttributes.getLoggedInUserId(request);

        List<NewsFeedLine> newsFeedLines = new ArrayList<NewsFeedLine>();
        ModelAndView mav = new ModelAndView("newsFeed");
        mav.addObject("newsFeedLines",newsFeedFacade.getNewsFeed("me"));
        return mav;
    }

}