package uk.co.socialcalendar.friend.controllers;

import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.friend.useCases.FriendFacade;
import uk.co.socialcalendar.user.useCases.UserFacade;

import java.util.HashMap;
import java.util.Map;

public class FriendCommonModel {
    FriendFacade friendFacade;
    FriendModelFacade friendModelFacade;
    UserFacade userFacade;

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    public void setFriendModelFacade(FriendModelFacade friendModelFacade) {
        this.friendModelFacade = friendModelFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public Map<String, Object> getCommonModelAttributes(String myId) {
        Map<String, Object> mav = new HashMap<String, Object>();
        mav.putAll(getFriendList(myId));
        mav.putAll(getFriendRequestsMadeOnMe(myId));
        mav.putAll(getFriendRequestsMadeByMe(myId));
        mav.putAll(getSection());
        mav.putAll(getUserName(myId));
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

    public Map<String,Object> getFriendRequestsMadeOnMe(String myId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("friendRequestsMadeOnMe", friendFacade.getFriendRequests(myId));
        return modelMap;
    }

    public Map<String,Object> getFriendRequestsMadeByMe(String myId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("friendRequestsMadeByMe", friendFacade.getFriendRequestsMadeByMe(myId));
        return modelMap;
    }

    public Map<String,Object> getUserName(String myId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("userName", userFacade.getUser(myId).getName());
        return modelMap;
    }
}
