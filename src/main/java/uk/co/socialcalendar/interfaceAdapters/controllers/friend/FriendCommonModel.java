package uk.co.socialcalendar.interfaceAdapters.controllers.friend;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModelFacade;
import uk.co.socialcalendar.interfaceAdapters.utilities.AuthenticationFacade;
import uk.co.socialcalendar.useCases.friend.FriendFacade;
import uk.co.socialcalendar.useCases.user.UserFacade;

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

    public Map<String, Object> getCommonModelAttributes(String myId) {
        Map<String, Object> mav = new HashMap<String, Object>();
        mav.putAll(getFriendList(myId));
        mav.putAll(getFriendRequests(myId));
        mav.putAll(getSection());
        mav.putAll(getUserName(myId));
        mav.putAll(authenticationFacade.getAuthenticationAttributes());
        mav.putAll(getNewFriend());
        return mav;
    }

    public Map<String,Object> getFriendList(String myId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("friendList", friendModelFacade.getFriendModelList(myId));
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

    public Map<String,Object> getFriendRequests(String myId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("friendRequests", friendFacade.getFriendRequests(myId));
        return modelMap;
    }


    public Map<String,Object> getUserName(String myId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("userName", userFacade.getUser(myId).getName());
        return modelMap;
    }

}
