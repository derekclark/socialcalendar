package uk.co.socialcalendar.availability.controllers;

import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;

import java.util.HashMap;
import java.util.Map;

public class AvailabilityCommonModel {
    FriendModelFacade friendModelFacade;

    public void setFriendModelFacade(FriendModelFacade friendModelFacade) {
        this.friendModelFacade = friendModelFacade;
    }

    public Map<String, Object> getSection() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("section","availability");
        return modelMap;
    }

    public Map<String, Object> getNewAvailability() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("newAvailability",new Availability());
        return modelMap;
    }

    public Map<String, Object> getFriendList(String me) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("friendList",friendModelFacade.getFriendModelList(me));
        return modelMap;
    }

    public Map<String, Object> getAttributes(String me) {
        Map<String, Object> mav = new HashMap<String, Object>();
        mav.putAll(getSection());
        mav.putAll(getNewAvailability());
        mav.putAll(getFriendList(me));

        return mav;
    }
}
