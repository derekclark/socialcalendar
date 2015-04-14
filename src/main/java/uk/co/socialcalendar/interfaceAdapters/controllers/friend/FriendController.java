package uk.co.socialcalendar.interfaceAdapters.controllers.friend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.interfaceAdapters.utilities.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FriendController{
	SessionAttributes sessionAttributes;
	FriendCommonModel friendCommonModel;

	public void setFriendCommonModel(FriendCommonModel friendCommonModel) {
		this.friendCommonModel = friendCommonModel;
	}

	public void setSessionAttributes(SessionAttributes sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	@RequestMapping(value = {"/","friend"}, method = RequestMethod.GET)
	public ModelAndView friendPage(Model m,
								   HttpServletRequest request, HttpServletResponse response) {
		String myUserId = getMyUserId(request);
		if (!iAmAuthenticated(myUserId)){
			ModelAndView mav = new ModelAndView("login");
			System.out.println("not authenticated, redirected!");
			return mav;
		}
		System.out.println("in friendPage; userid=" + sessionAttributes.getLoggedInUserId(request));
		System.out.println("facebookId=" + request.getSession().getAttribute("FACEBOOK_ID"));
		System.out.println("IS_AUTHENTICATED=" + sessionAttributes.isAuthenticated(request));

		ModelAndView mav = new ModelAndView("friend");

		mav.addAllObjects(friendCommonModel.getCommonModelAttributes(myUserId));
		return mav;
	}

	private boolean iAmAuthenticated(String myUserId) {
		if (myUserId == null || myUserId.isEmpty()){
			return false;
		};
		return true;
	}

	public String getMyUserId(HttpServletRequest request){
		return sessionAttributes.getLoggedInUserId(request);
	}

}
