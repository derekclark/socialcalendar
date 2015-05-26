package uk.co.socialcalendar.availability.controllers;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AddAvailabilityController {

    AvailabilityFacade availabilityFacade;
    SessionAttributes sessionAttributes;

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void setAvailabilityFacade(AvailabilityFacade availabilityFacade) {
        this.availabilityFacade = availabilityFacade;
    }

    @RequestMapping(value="addAvailability", method= RequestMethod.POST)
    public ModelAndView addAvailability(
            @RequestParam(value="title", required=false,defaultValue="") String title,
            @RequestParam(value="startDate", required=false,defaultValue="") String startDate,
            @RequestParam(value="endDate", required=false,defaultValue="") String endDate,
            @RequestParam(value="selectedFriends", required=false, defaultValue="")
                List<String> friendNotifyList,
//            @Valid Availability availabilities,
            Model m,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        String loggedInUser = sessionAttributes.getLoggedInUserId(request);

        System.out.println(loggedInUser);
        String pattern = "yyyy-MM-dd HH:mm";
        LocalDateTime startDateFormatted = LocalDateTime.parse(startDate, DateTimeFormat.forPattern(pattern));
        LocalDateTime endDateFormatted = LocalDateTime.parse(endDate, DateTimeFormat.forPattern(pattern));
        Availability availability = new Availability(loggedInUser,"ownerName",title,
                startDateFormatted, endDateFormatted, "status");

        availabilityFacade.create(availability);
        ModelAndView mav = new ModelAndView("availability");
        mav.addObject("message","You have just created a new availability");
        mav.addObject("isAuthenticated","true");
        return mav;

    }
}
