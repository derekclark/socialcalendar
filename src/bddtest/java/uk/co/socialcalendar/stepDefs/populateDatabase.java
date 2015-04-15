package uk.co.socialcalendar.stepDefs;

import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.friend.useCases.FriendDAO;
import uk.co.socialcalendar.user.persistence.UserDAO;

public class PopulateDatabase {
    UserDAO userDAO;
    FriendDAO friendDAO;
    public static final int NOT_SAVED = -1;

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

    public void populateUser(User user){
        writeUser(user);
    }

    public int populateFriend(Friend friend){
        return writeFriends(friend);
    }
}
