package uk.co.socialcalendar.availability;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.friend.useCases.FriendFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AvailabilityController {

    FriendFacade friendFacade;

    SessionAttributes sessionAttributes;

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    @RequestMapping(value = "addAvailability", method = RequestMethod.GET)
    public ModelAndView addAvailability(Model m, HttpServletRequest request, HttpServletResponse response) {
        String me = sessionAttributes.getLoggedInUserId(request);
        ModelAndView mav = new ModelAndView("addAvailability");
        mav.addObject("section","availability");
        mav.addObject("newAvailability",new Availability());
        mav.addObject("friendList",friendFacade.getMyAcceptedFriends(me));
        return mav;
    }
}
