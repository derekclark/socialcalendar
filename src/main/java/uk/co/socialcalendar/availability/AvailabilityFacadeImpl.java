package uk.co.socialcalendar.availability;

import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.friend.useCases.FriendDAO;

import java.util.List;

public class AvailabilityFacadeImpl implements AvailabilityFacade{
    AvailabilityDAO availabilityDAO;
    FriendDAO friendDAO;

    public void setAvailabilityDAO(AvailabilityDAO availabilityDAO) {
        this.availabilityDAO = availabilityDAO;
    }

    @Override
    public int create(Availability availability) {
        return availabilityDAO.save(availability);
    }

    @Override
    public Availability get(int id) {
        return availabilityDAO.read(id);
    }

    @Override
    public boolean update(Availability availability) {
        return availabilityDAO.update(availability);
    }

    @Override
    public List<Friend> getFriendList(String me) {
        return friendDAO.getMyAcceptedFriends(me);
    }
}
