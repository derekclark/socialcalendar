package uk.co.socialcalendar.availability.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AmendAvailabilityController {
    SessionAttributes sessionAttributes;
    AvailabilityFacade availabilityFacade;
    UserFacade userFacade;
    AvailabilityCommonModel availabilityCommonModel;

    public void setAvailabilityCommonModel(AvailabilityCommonModel availabilityCommonModel) {
        this.availabilityCommonModel = availabilityCommonModel;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public void setAvailabilityFacade(AvailabilityFacade availabilityFacade) {
        this.availabilityFacade = availabilityFacade;
    }

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    @RequestMapping(value="amendAvailability", method= RequestMethod.GET)
    public ModelAndView amendAvailability(@RequestParam(value="id", required=false,defaultValue="") int id,
                        Model model, HttpServletRequest request,
                        HttpServletResponse response) {
        String me = sessionAttributes.getLoggedInUserId(request);
        ModelAndView mav = new ModelAndView("amendAvailability");
        mav.addObject("amendAvailability",availabilityFacade.get(id));
        mav.addAllObjects(availabilityCommonModel.getAttributes(me));
        return mav;
    }
}
