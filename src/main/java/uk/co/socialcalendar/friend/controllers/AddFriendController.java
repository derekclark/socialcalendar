package uk.co.socialcalendar.friend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.authentication.SessionAttributes;
import uk.co.socialcalendar.friend.useCases.FriendFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AddFriendController {
    private static final Logger LOG = LoggerFactory.getLogger(AddFriendController.class);
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
            @RequestParam(value="beFriendedEmail", required=false) String befriendedEmail,
            Model m, HttpServletRequest request, HttpServletResponse response) {
        String loggedInUser = sessionAttributes.getLoggedInUserId(request);

        LOG.info("addfriend; create request;" + loggedInUser + " " + befriendedEmail);
        friendFacade.createFriendRequest(loggedInUser, befriendedEmail);
        ModelAndView mav = new ModelAndView("friend");
        mav.addAllObjects(friendCommonModel.getCommonModelAttributes(loggedInUser));
        mav.addObject("message", "You have sent a friend request to " + befriendedEmail);
        return mav;
    }
}
