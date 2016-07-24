package uk.co.socialcalendar.user.persistence;

import uk.co.socialcalendar.user.entities.User;

public class UserDAOWebServiceImpl implements UserDAO{
    @Override
    public User read(String userEmail) {
        return null;
    }

    @Override
    public boolean save(User user) {
        if (user.getEmail().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public UserHibernateModel getUserModel(String userEmail) {
        return null;
    }
}
