package uk.co.socialcalendar.user.persistence;

import uk.co.socialcalendar.user.entities.User;

public interface UserDAO {

    public User read(String userEmail);
    public boolean save(User user);
    public UserHibernateModel getUserModel(String userEmail);
}
