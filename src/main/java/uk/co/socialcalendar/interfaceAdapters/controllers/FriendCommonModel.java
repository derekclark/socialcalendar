package uk.co.socialcalendar.interfaceAdapters.controllers;

import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.interfaceAdapters.models.FriendModelFacade;
import uk.co.socialcalendar.interfaceAdapters.utilities.AuthenticationFacade;
import uk.co.socialcalendar.useCases.FriendFacade;
import uk.co.socialcalendar.useCases.UserFacade;

import java.util.HashMap;
import java.util.Map;

public class FriendCommonModel {
    FriendFacade friendFacade;
    FriendModelFacade friendModelFacade;
    AuthenticationFacade authenticationFacade;
    UserFacade userFacade;

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    public void setFriendModelFacade(FriendModelFacade friendModelFacade) {
        this.friendModelFacade = friendModelFacade;
    }

    public void setAuthenticationFacade(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public ModelAndView getCommonModelAttributes(String loggedInUser) {
        ModelAndView mav = new ModelAndView("friend");
        mav.addAllObjects(getFriendList(loggedInUser));
        mav.addAllObjects(getFriendRequests(loggedInUser));
        mav.addAllObjects(getSection());
        mav.addAllObjects(getUserName(loggedInUser));
        mav.addAllObjects(authenticationFacade.getAuthenticationAttrbutes());
        mav.addAllObjects(getNewFriend());
        return mav;
    }

    public Map<String,Object> getFriendList(String loggedInUser) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("friendList", friendModelFacade.getFriendModelList(loggedInUser));
        return modelMap;
    }


    public Map<String,Object> getSection() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("section","friends");
        return modelMap;
    }

    public Map<String,Object> getNewFriend() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("newFriend",new Friend());
        return modelMap;
    }

    public Map<String,Object> getFriendRequests(String loggedInUser) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("friendRequests", friendFacade.getFriendRequests(loggedInUser));
        return modelMap;
    }


    public Map<String,Object> getUserName(String loggedInUser) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("userName", userFacade.getUser(loggedInUser).getName());
        return modelMap;
    }

}
