package uk.co.socialcalendar.interfaceAdapters.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModel;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModelFacade;
import uk.co.socialcalendar.useCases.FriendFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller


public class FriendController{
	FriendFacade friendFacade;
	FriendModelFacade friendModelFacade;

	public void setFriendModelFacade(FriendModelFacade friendModelFacade) {
		this.friendModelFacade = friendModelFacade;
	}

	public void setFriendFacade(FriendFacade friendFacade) {
		this.friendFacade = friendFacade;
	}

	@RequestMapping(value = "friend", method = RequestMethod.GET)
	public ModelAndView friendPage(Model m,
								   HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String loggedInUser = (String) session.getAttribute("USER_NAME");

		ModelAndView mav = new ModelAndView("friend");
		mav.addObject("friendList",friendModelFacade.getFriendModelList(loggedInUser));
		mav.addObject("friendRequests",friendFacade.getFriendRequests(loggedInUser));
		mav.addObject("section","friends");
		mav.addObject("friend", new FriendModel());
		return mav;
	}


}
