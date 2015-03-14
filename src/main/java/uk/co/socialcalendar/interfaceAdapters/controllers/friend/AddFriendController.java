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
public class AddFriendController {
    SessionAttributes sessionAttributes;
    FriendCommonModel friendCommonModel;

    public void setSessionAttributes(SessionAttributes sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void setFriendCommonModel(FriendCommonModel friendCommonModel) {
        this.friendCommonModel = friendCommonModel;
    }

    @RequestMapping(value = "addFriend", method = RequestMethod.POST)
    public ModelAndView addFriend(Model m,
                                   HttpServletRequest request, HttpServletResponse response) {
        String loggedInUser = sessionAttributes.getLoggedInUserId(request);
        ModelAndView mav = new ModelAndView("friend");
        mav.addAllObjects(friendCommonModel.getCommonModelAttributes(loggedInUser));
        return mav;
    }
}
