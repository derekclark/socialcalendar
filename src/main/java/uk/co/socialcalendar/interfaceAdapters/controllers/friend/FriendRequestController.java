package uk.co.socialcalendar.interfaceAdapters.controllers.friend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.utilities.SessionAttributes;
import uk.co.socialcalendar.interfaceAdapters.utilities.UserNotification;
import uk.co.socialcalendar.useCases.friend.FriendFacade;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class FriendRequestController {
    FriendFacade friendFacade;
    UserNotification userNotification;
    SessionAttributes sessionAttributes;
    FriendCommonModel friendCommonModel;

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void setFriendCommonModel(FriendCommonModel friendCommonModel) {
        this.friendCommonModel = friendCommonModel;
    }

    public void setUserNotification(UserNotification userNotification) {
        this.userNotification = userNotification;
    }

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    @RequestMapping(value = "acceptFriendRequest", method = RequestMethod.GET)
    public ModelAndView acceptFriendRequest
            (@RequestParam(value="id", required=false,defaultValue="") int friendId,
             Model m,
             HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
        String loggedInUser = sessionAttributes.getLoggedInUserId(request);
        ModelAndView mav = getModelAndView(loggedInUser);

        Friend friend = friendFacade.getFriend(friendId);
        notifyUser(friend);
        mav.addObject("message", "You have just accepted a friend request from " + friend.getRequesterEmail());

        friendFacade.acceptFriendRequest(friendId);
        return mav;
    }

    @RequestMapping(value = "declineFriendRequest", method = RequestMethod.GET)
    public ModelAndView declineFriendRequest
            (@RequestParam(value="id", required=false,defaultValue="") int friendId,
             Model m,
             HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
        String loggedInUser = sessionAttributes.getLoggedInUserId(request);
        ModelAndView mav = getModelAndView(loggedInUser);

        Friend friend = friendFacade.getFriend(friendId);
        notifyUser(friend);
        mav.addObject("message", "You have just declined a friend request from " + friend.getRequesterEmail());

        friendFacade.declineFriendRequest(friendId);
        return mav;
    }

    private ModelAndView getModelAndView(String loggedInUser) {
        ModelAndView mav = new ModelAndView("friend");
        mav.addAllObjects(friendCommonModel.getCommonModelAttributes(loggedInUser));
        return mav;
    }


    public boolean notifyUser(Friend friend) {
        String senderEmail = friend.getBeFriendedEmail();
        String recipientEmail = friend.getRequesterEmail();
        String message="", subject="";

        if (friend.getStatus() == FriendStatus.ACCEPTED) {
            message = friend.getBeFriendedEmail() + " has accepted your friend request";
            subject = "Accepted Friend Request";
        }

        if (friend.getStatus() == FriendStatus.DECLINED){
            message = friend.getBeFriendedEmail() + " has declined your friend request";
            subject = "Declined Friend Request";
        }

        userNotification.notify(recipientEmail, senderEmail, subject, message);
        return true;
    }
}