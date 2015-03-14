package uk.co.socialcalendar.frameworksAndDrivers.persistence;

import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.user.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDAO implements UserDAO {
    private static final String MAIL1 = "userEmail1";
    private static final String MAIL2 = "userEmail2";
    private static final String MAIL3 = "userEmail3";
    private static final String MAIL4 = "userEmail4";
    private static final String FACEBOOK_ID1 = "100004697869160";
    private static final String FACEBOOK_ID2 = "100007212617286";
    private static final String FACEBOOK_ID3 = "100007212617286";
    private static final String FACEBOOK_ID4 = "facebookId4";
    private static final String NAME1 = "userName1";
    private static final String NAME2 = "userName2";
    private static final String NAME3 = "userName3";
    private static final String NAME4 = "userName4";

    List<User> listOfUsers = new ArrayList<User>();

    @Override
    public User read(String userEmail) {
        for (User u:listOfUsers){
            if (u.getEmail().equals(userEmail)){
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        if (read(user.getEmail()) == null) {
            listOfUsers.add(user);
        }else {
            return false;
        }
        return true;
    }

    public void populate(){
        save(new User(MAIL1, NAME1, FACEBOOK_ID1));
        save(new User(MAIL2, NAME2, FACEBOOK_ID2));
        save(new User(MAIL3, NAME3, FACEBOOK_ID3));
        save(new User(MAIL4, NAME4, FACEBOOK_ID4));

        System.out.println("populating user in memory database with " + listOfUsers.size() + " users");
        for (User u:listOfUsers){
            System.out.println(u.getEmail());
        }

    }

}
