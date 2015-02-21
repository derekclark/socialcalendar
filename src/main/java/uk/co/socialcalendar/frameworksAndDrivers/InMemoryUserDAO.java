package uk.co.socialcalendar.frameworksAndDrivers;

import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDAO implements UserDAO {
    private static final String USER_EMAIL1 = "userEmail1";
    private static final String USER_EMAIL2 = "userEmail2";
    private static final String FACEBOOK_ID1 = "facebookId1";
    private static final String FACEBOOK_ID2 = "facebookId2";
    private static final String USER_NAME1 = "userName1";
    private static final String USER_NAME2 = "userName2";
    List<User> listOfUsers = new ArrayList<User>();

    @Override
    public User getUser(String userEmail) {
        return null;
    }

    public void populate(){
        User user = new User();
        user.setEmail(USER_EMAIL1);
        user.setName(USER_NAME1);
        user.setFacebookId(FACEBOOK_ID1);
        listOfUsers.add(user);
        user = new User();
        user.setEmail(USER_EMAIL2);
        user.setName(USER_NAME2);
        user.setFacebookId(FACEBOOK_ID2);
        listOfUsers.add(user);

    }
}
