package uk.co.socialcalendar.interfaceAdapters.models;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.FriendFacade;
import uk.co.socialcalendar.useCases.UserFacade;

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

    public List<FriendModel> getFriendModelList(String userEmail){
        List<Friend> friendList = friendFacade.getConfirmedFriends(userEmail);
        List<FriendModel> friendModelList = new ArrayList<FriendModel>();

        for (Friend f : friendList){
            FriendModel friendModel = new FriendModel();

            User user = userFacade.getUser(f.getBeFriendedEmail());
            friendModel.setFacebookId(user.getFacebookId());
            friendModel.setEmail(user.getEmail());
            friendModel.setFriendId(f.getFriendId());
            friendModel.setName(user.getName());

            friendModelList.add(friendModel);
        }

        return friendModelList;
    }
}
