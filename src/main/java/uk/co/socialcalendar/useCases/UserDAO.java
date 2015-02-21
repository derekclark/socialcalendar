package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.User;

public interface UserDAO {

    public User getUser(String userEmail);
    public boolean save(User user);
}
