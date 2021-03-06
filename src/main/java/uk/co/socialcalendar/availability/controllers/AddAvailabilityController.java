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
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@org.springframework.web.bind.annotation.SessionAttributes("newAvailability")
public class AddAvailabilityController {
    AvailabilityFacade availabilityFacade;
    SessionAttributes sessionAttributes;
    UserFacade userFacade;
    FriendModelFacade friendModelFacade;
    AvailabilityCommonModel availabilityCommonModel;
    String me;

    public void setFriendModelFacade(FriendModelFacade friendModelFacade) {
        this.friendModelFacade = friendModelFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void setAvailabilityFacade(AvailabilityFacade availabilityFacade) {
        this.availabilityFacade = availabilityFacade;
    }

    public void setAvailabilityCommonModel(AvailabilityCommonModel availabilityCommonModel) {
        this.availabilityCommonModel = availabilityCommonModel;
    }

    @RequestMapping(value="addAvailability", method= RequestMethod.POST)
    public ModelAndView addAvailability(
            @RequestParam(value="title", required=false,defaultValue="") String title,
            @RequestParam(value="startDate", required=false,defaultValue="") String startDate,
            @RequestParam(value="endDate", required=false,defaultValue="") String endDate,
            @RequestParam(value="selectedFriends", required=false, defaultValue="")
//                    @ModelAttribute("newAvailability") AddAvailabilityDTO dto,
                List<String> selectedFriends,
//            @Valid Availability availabilities,
            Model m,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        me = sessionAttributes.getLoggedInUserId(request);
        String myName = userFacade.getUser(me).getName();

        LocalDateTime startDateFormatted = convertToLocalDateTime(startDate);
        LocalDateTime endDateFormatted = convertToLocalDateTime(endDate);

        Availability availability = new Availability(me, myName, title,
                startDateFormatted, endDateFormatted, "status");
//        System.out.println("selected friends="+selectedFriends.get(0));
        availabilityFacade.shareWithUsers(availability, selectedFriends);
        System.out.println("shared friends="+availability.getSharedList().size());
        availabilityFacade.create(availability);

        return buildModel();
    }

    public LocalDateTime convertToLocalDateTime(String date){
        String pattern = "yyyy-MM-dd HH:mm";
        return LocalDateTime.parse(date, DateTimeFormat.forPattern(pattern));
    }

    public ModelAndView buildModel(){
        ModelAndView mav = new ModelAndView("availabilityCreate");
        mav.addObject("message","You have just created a new availability");
        mav.addAllObjects(availabilityCommonModel.getAttributes(me));
//        mav.addObject("section","availability");
//        mav.addObject("newAvailability",new Availability());
//        mav.addObject("friendList", friendModelFacade.getFriendModelList(me));
        return mav;
    }
}
