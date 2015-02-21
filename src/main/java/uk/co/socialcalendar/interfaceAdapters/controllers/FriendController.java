package uk.co.socialcalendar.interfaceAdapters.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModel;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModelFacade;
import uk.co.socialcalendar.interfaceAdapters.utilities.AuthenticationFacade;
import uk.co.socialcalendar.useCases.FriendFacade;
import uk.co.socialcalendar.useCases.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FriendController{
	FriendFacade friendFacade;
	FriendModelFacade friendModelFacade;
	AuthenticationFacade authenticationFacade;
	UserFacade userFacade;

	public void setUserFacade(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	public void setAuthenticationFacade(AuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}

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
		String loggedInUser = (String) session.getAttribute("USER_ID");

		System.out.println("session attr isAuthenticated=" + session.getAttribute("IS_AUTHENTICATED"));

		System.out.println("logged in user=" + loggedInUser);
		ModelAndView mav = new ModelAndView("friend");
		mav.addObject("friendList",friendModelFacade.getFriendModelList(loggedInUser));

//		mav.addObject("alertMessage","Friend request sent to ");


		System.out.println("retrieved friends=" + friendModelFacade.getFriendModelList(loggedInUser).size());
		System.out.println("names...");

		List<FriendModel> fm = friendModelFacade.getFriendModelList(loggedInUser);
		for (FriendModel f: fm){
			System.out.println("name is=" + f.getName());
		}


		mav.addObject("friendRequests",friendFacade.getFriendRequests(loggedInUser));
		mav.addObject("section","friends");
		mav.addObject("userName", userFacade.getUser(loggedInUser).getName());
		mav.addAllObjects(authenticationFacade.getAuthenticationAttrbutes());

		mav.addObject("newFriend", new Friend());
		return mav;
	}


}
