package uk.co.socialcalendar.user.persistence;

import uk.co.socialcalendar.user.entities.User;

public interface UserDAO {

    public User read(String userEmail);
    public boolean save(User user);

    //DOES THIS BELONG HERE????? SHOULDN'T RETURN HIBERNATE ANYTHING!!!!!!! CHANGE!!!!!!!!!!!
    public UserHibernateModel getUserModel(String userEmail);
}
