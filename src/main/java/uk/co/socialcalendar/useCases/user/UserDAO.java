package uk.co.socialcalendar.useCases.user;

import uk.co.socialcalendar.entities.User;

public interface UserDAO {

    public User read(String userEmail);
    public boolean save(User user);
}