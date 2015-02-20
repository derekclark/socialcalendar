package uk.co.socialcalendar.frameworksAndDrivers;

import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDAO implements UserDAO {
    List<User> listOfUsers = new ArrayList<User>();

    @Override
    public User getUser(String userEmail) {
        return null;
    }

}
