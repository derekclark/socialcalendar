package uk.co.socialcalendar.interfaceAdapters.controllers;

import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.utilities.UserNotification;
import uk.co.socialcalendar.useCases.FriendFacade;

public class FriendRequestController {
    FriendFacade friendFacade;
    UserNotification userNotification;

    public void setUserNotification(UserNotification userNotification) {
        this.userNotification = userNotification;
    }

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    public ModelAndView acceptFriendRequest(String userId, int friendId) {
        ModelAndView mav = new ModelAndView("friend");
        Friend friend = friendFacade.getFriend(friendId);

        String requester=friend.getRequesterEmail();
        mav.addObject("message", "You have just accepted a friend request from " + requester);
        mav.addObject("section", "friends");

        friendFacade.acceptFriendRequest(friendId);

        notifyUser(friend);
        return mav;
    }


    public ModelAndView declineFriendRequest(String userId, int friendId) {
        ModelAndView mav = new ModelAndView("friend");
        Friend friend = friendFacade.getFriend(friendId);
        String requester=friend.getRequesterEmail();
        mav.addObject("message", "You have just declined a friend request from " + requester);
        mav.addObject("section", "friends");

        friendFacade.declineFriendRequest(friendId);


        notifyUser(friend);
        return mav;
    }


    public boolean notifyUser(Friend friend) {
        String senderEmail = friend.getBeFriended();
        String recipientEmail = friend.getRequesterEmail();

        String message="", subject="";

        if (friend.getStatus() == FriendStatus.ACCEPTED) {
            message = friend.getBeFriended() + " has accepted your friend request";
            subject = "Accepted Friend Request";
        }

        if (friend.getStatus() == FriendStatus.DECLINED){
            message = friend.getBeFriended() + " has declined your friend request";
            subject = "Declined Friend Request";
        }

        userNotification.notify(recipientEmail, senderEmail, subject, message);
        return true;
    }
}