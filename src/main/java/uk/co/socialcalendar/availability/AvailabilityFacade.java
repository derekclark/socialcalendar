package uk.co.socialcalendar.availability;

import uk.co.socialcalendar.friend.entities.Friend;

import java.util.List;

public interface AvailabilityFacade {
    public int create(Availability availability);
    public Availability get(int id);
    public boolean update(Availability availability);
    public List<Friend> getFriendList(String me);
}
