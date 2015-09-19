package uk.co.socialcalendar.availability.controllers;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AmendAvailabilityController {

    SessionAttributes sessionAttributes;

    UserFacade userFacade;

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public ModelAndView AmendAvailability(int id, Model model, HttpServletRequest mockHttpServletRequest,
                                          HttpServletResponse mockHttpServletResponse) {
        ModelAndView mav = new ModelAndView("amendAvailability");
        mav.addObject("section","availability");
        return mav;
    }
}
