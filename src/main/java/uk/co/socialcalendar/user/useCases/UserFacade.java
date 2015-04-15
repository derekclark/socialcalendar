package uk.co.socialcalendar.user.useCases;

import uk.co.socialcalendar.user.entities.User;

public interface UserFacade {
    public User getUser(String userEmail);
}
