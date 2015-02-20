package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.User;

public class UserFacadeImpl implements UserFacade {


    UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(String userEmail){
        return userDAO.getUser(userEmail);
    }
}
