package uk.co.socialcalendar.interfaceAdapters.models.friend;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.friend.FriendFacade;
import uk.co.socialcalendar.useCases.user.UserFacade;

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

    public List<FriendModel> getFriendModelList(String myEmail){
        List<Friend> friendList = friendFacade.getMyAcceptedFriends(myEmail);
        List<FriendModel> friendModelList = new ArrayList<FriendModel>();
        for (Friend f : friendList){
            System.out.println("requester="+f.getRequesterEmail() + " befriended=" + f.getBeFriendedEmail() + " me=" + myEmail);
            friendModelList.add(populateModel(myEmail, f));
        }
        return friendModelList;
    }

    private FriendModel populateModel(String myEmail, Friend f) {
        String u = returnFriendsEmailNotMine(f, myEmail);
        System.out.println("your email not mine = " + u);
        User user = userFacade.getUser(u);
        System.out.println("user = " + user.getName());
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
