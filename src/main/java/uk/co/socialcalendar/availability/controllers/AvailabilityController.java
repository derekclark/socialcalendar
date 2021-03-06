package uk.co.socialcalendar.availability.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AvailabilityController {
    FriendModelFacade friendModelFacade;
    SessionAttributes sessionAttributes;
    UserFacade userFacade;
    AvailabilityCommonModel availabilityCommonModel;

    public void setAvailabilityCommonModel(AvailabilityCommonModel availabilityCommonModel) {
        this.availabilityCommonModel = availabilityCommonModel;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void setFriendModelFacade(FriendModelFacade friendModelFacade) {
        this.friendModelFacade = friendModelFacade;
    }

    @RequestMapping(value = "availability", method = RequestMethod.GET)
    public ModelAndView addAvailability(Model m, HttpServletRequest request, HttpServletResponse response) {
        String me = sessionAttributes.getLoggedInUserId(request);
        ModelAndView mav = new ModelAndView("availabilityCreate");
        mav.addAllObjects(availabilityCommonModel.getAttributes(me));
        return mav;
    }
}
