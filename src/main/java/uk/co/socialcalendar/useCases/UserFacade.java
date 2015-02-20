package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.User;

public interface UserFacade {
    public User getUser(String userEmail);
}
