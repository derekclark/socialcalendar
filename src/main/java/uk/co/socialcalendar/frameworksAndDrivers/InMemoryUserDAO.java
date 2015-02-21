package uk.co.socialcalendar.frameworksAndDrivers;

import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDAO implements UserDAO {
    private static final String USER_EMAIL1 = "userEmail1";
    private static final String USER_EMAIL2 = "userEmail2";
    private static final String FACEBOOK_ID1 = "100004697869160";
    private static final String FACEBOOK_ID2 = "100007212617286";
    private static final String FACEBOOK_ID3 = "100007212617286";
    private static final String FACEBOOK_ID4 = "facebookId4";
    private static final String USER_NAME1 = "userName1";
    private static final String USER_NAME2 = "userName2";
    private static final String USER_NAME3 = "userName3";
    private static final String USER_NAME4 = "userName4";
    private static final String BEFRIENDED1 = "befriendedEmail1";
    private static final String BEFRIENDED2 = "befriendedEmail2";

    List<User> listOfUsers = new ArrayList<User>();

    @Override
    public User getUser(String userEmail) {
        for (User u:listOfUsers){
            if (u.getEmail().equals(userEmail)){
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        listOfUsers.add(user);
        return true;
    }

    public void populate(){
        listOfUsers.add(new User(USER_EMAIL1, USER_NAME1, FACEBOOK_ID1));
        listOfUsers.add(new User(USER_EMAIL2, USER_NAME2, FACEBOOK_ID2));
        listOfUsers.add(new User(BEFRIENDED1, USER_NAME3, FACEBOOK_ID3));
        listOfUsers.add(new User(BEFRIENDED2, USER_NAME4, FACEBOOK_ID4));

        System.out.println("populating user in memory database with " + listOfUsers.size() + " users");
        for (User u:listOfUsers){
            System.out.println(u.getEmail());
        }

    }

}
