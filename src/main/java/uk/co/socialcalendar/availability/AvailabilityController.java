package uk.co.socialcalendar.availability;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AvailabilityController {

    @RequestMapping(value = "addAvailability", method = RequestMethod.GET)
    public ModelAndView addAvailability(Model m, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("addAvailability");
        return mav;
    }
}
