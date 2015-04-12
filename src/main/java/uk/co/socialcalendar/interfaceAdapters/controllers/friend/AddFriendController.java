package uk.co.socialcalendar.interfaceAdapters.controllers.friend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.interfaceAdapters.utilities.SessionAttributes;
import uk.co.socialcalendar.useCases.friend.FriendFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AddFriendController {
    SessionAttributes sessionAttributes;
    FriendCommonModel friendCommonModel;
    FriendFacade friendFacade;

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void setFriendCommonModel(FriendCommonModel friendCommonModel) {
        this.friendCommonModel = friendCommonModel;
    }

    @RequestMapping(value = "addFriend", method = RequestMethod.POST)
    public ModelAndView addFriend(
            @RequestParam(value="requesteeEmail", required=true,defaultValue="") String requesteeEmail,
            Model m, HttpServletRequest request, HttpServletResponse response) {
        String loggedInUser = sessionAttributes.getLoggedInUserId(request);

        System.out.println("addfriend; create request;" + loggedInUser + " " + requesteeEmail);
        friendFacade.createFriendRequest(loggedInUser, requesteeEmail);
        ModelAndView mav = new ModelAndView("friend");
        mav.addAllObjects(friendCommonModel.getCommonModelAttributes(loggedInUser));
        mav.addObject("message", "You have sent a friend request to " + requesteeEmail);
        return mav;
    }
}
