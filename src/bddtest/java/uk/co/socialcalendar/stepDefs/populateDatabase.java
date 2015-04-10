package uk.co.socialcalendar.stepDefs;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.friend.FriendDAO;
import uk.co.socialcalendar.useCases.user.UserDAO;

public class PopulateDatabase {
    public static final String MY_FACEBOOK_ID = "100008173740345";
    public static final String MY_EMAIL = "me";
    public static final String MY_NAME = "my name";
    public static final int NOT_SAVED = -1;

    UserDAO userDAO;
    FriendDAO friendDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setFriendDAO(FriendDAO friendDAO) {
        this.friendDAO = friendDAO;
    }

    public void writeUser(User user){
        if (userDAO.read(user.getEmail()) == null){
            System.out.println("creating user=" + user.getEmail());
            userDAO.save(user);
        }
    }

    public Friend createFriend(String requester, String beFriended, FriendStatus status, int id){
        Friend friend = new Friend(requester, beFriended, status);
        friend.setFriendId(id);
        return friend;
    }
    public int writeFriends(Friend friend){
        if (!friendDAO.friendshipExists(friend.getRequesterEmail(), friend.getBeFriendedEmail())){
            int id = friendDAO.save(friend);
            System.out.println("just saved friend id=" + id);
            return id;
        }else{
            System.out.println("existing friend relationship - don't write");
        }
        return NOT_SAVED;
    }

    public void populateMyUser(){
        writeUser(new User(MY_EMAIL, MY_NAME, MY_FACEBOOK_ID));
    }
    public void populateUser(User user){
        writeUser(user);
    }

    public int populateFriend(Friend friend){
        return writeFriends(friend);
    }
}
