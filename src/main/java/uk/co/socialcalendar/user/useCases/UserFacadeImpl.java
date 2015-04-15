package uk.co.socialcalendar.user.useCases;

import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAO;

public class UserFacadeImpl implements UserFacade {


    UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(String userEmail){
        return userDAO.read(userEmail);
    }
}
