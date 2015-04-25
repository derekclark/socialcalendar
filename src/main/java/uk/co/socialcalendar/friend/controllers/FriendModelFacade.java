package uk.co.socialcalendar.friend.controllers;

import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.friend.useCases.FriendFacade;
import uk.co.socialcalendar.user.useCases.UserFacade;

import java.util.ArrayList;
import java.util.List;

public class FriendModelFacade {

    FriendFacade friendFacade;
    UserFacade userFacade;

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public List<FriendModel> getFriendModelList(String myId){
        List<Friend> friendList = friendFacade.getMyAcceptedFriends(myId);
        List<FriendModel> friendModelList = new ArrayList<FriendModel>();
        for (Friend f : friendList){
            friendModelList.add(populateModel(myId, f));
        }
        return friendModelList;
    }

    private FriendModel populateModel(String myId, Friend f) {
        String u = returnFriendsEmailNotMine(f, myId);
        User user = userFacade.getUser(u);
        FriendModel friendModel = new FriendModel();
        friendModel.setFacebookId(user.getFacebookId());
        setHasFacebookId(friendModel);
        friendModel.setEmail(user.getEmail());
        friendModel.setFriendId(f.getFriendId());
        friendModel.setName(user.getName());
        return friendModel;
    }

    private void setHasFacebookId(FriendModel friendModel) {
        if (friendModel.getFacebookId() == null || friendModel.getFacebookId().isEmpty()){
            friendModel.setHasFacebookId(false);
        }else {
            friendModel.setHasFacebookId(true);
        }
    }

    public String returnFriendsEmailNotMine(Friend friend, String myEmail) {
        if (!friend.getRequesterEmail().equals(myEmail)){
            return friend.getRequesterEmail();
        } else {
            return friend.getBeFriendedEmail();
        }
    }
}
