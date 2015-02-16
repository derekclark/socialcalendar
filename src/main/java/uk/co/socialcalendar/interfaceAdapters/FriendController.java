package uk.co.socialcalendar.interfaceAdapters;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.useCases.FriendDAO;
import uk.co.socialcalendar.useCases.FriendFacade;
import uk.co.socialcalendar.useCases.FriendFacadeImpl;
import org.springframework.web.servlet.ModelAndView;

public class FriendController {
	
	FriendFacade friendFacade;

	public void setFriendFacade(FriendFacade friendFacade) {
		this.friendFacade = friendFacade;
	}

	public ModelAndView friendPage(String loggedInUser) {
		ModelAndView mav = new ModelAndView("friend");
		mav.addObject("friendList",friendFacade.getConfirmedFriends(loggedInUser));
		mav.addObject("friendRequests",friendFacade.getFriendRequests(loggedInUser));
		mav.addObject("section","friends");
		return mav;
	}

	public ModelAndView addFriend(String loggedInUser) {
		ModelAndView mav = new ModelAndView("friend");
		mav.addObject("section","friends");
		mav.addObject("friend",new Friend());
		return mav;
	}
}
