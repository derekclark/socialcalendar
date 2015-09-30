package uk.co.socialcalendar.availability.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.message.Message;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.useCases.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AmendAvailabilityController {
    SessionAttributes sessionAttributes;
    AvailabilityFacade availabilityFacade;
    UserFacade userFacade;
    AvailabilityCommonModel availabilityCommonModel;
    String me;

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
        me = sessionAttributes.getLoggedInUserId(request);
        ModelAndView mav = new ModelAndView("amendAvailability");
        Availability availability = availabilityFacade.get(id);
        mav.addObject("amendAvailability",availability);
        mav.addObject("newMessage",new Message());
        mav.addObject("isThisMyAvailability",isThisMyAvailability(availability));
        mav.addObject("friendsSharedAvailability",getSharedFriends(availability.getSharedList()));
        mav.addAllObjects(availabilityCommonModel.getAttributes(me));
        return mav;
    }

    private Set<FriendModel> getSharedFriends(Set<User> sharedList) {
        Set<FriendModel> list = new HashSet<FriendModel>();
        for (User user:sharedList){
            FriendModel friendModel = new FriendModel(user);
            list.add(friendModel);
        }

        return list;
    }

    private boolean isThisMyAvailability(Availability availability) {
        return availability.getOwnerEmail().equals(me);
    }
}
