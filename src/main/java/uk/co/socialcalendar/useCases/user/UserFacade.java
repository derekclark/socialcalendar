package uk.co.socialcalendar.useCases.user;

import uk.co.socialcalendar.entities.User;

public interface UserFacade {
    public User getUser(String userEmail);
}
