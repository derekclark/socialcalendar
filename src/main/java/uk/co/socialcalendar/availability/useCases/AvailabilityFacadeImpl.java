package uk.co.socialcalendar.availability.useCases;

import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAO;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AvailabilityFacadeImpl implements AvailabilityFacade{
    AvailabilityDAO availabilityDAO;
    UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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

    public Set<User> getUserList(List<String> selectedList) {
        Set<User> returnList = new HashSet<User>();
        for (String userId:selectedList){
            returnList.add(userDAO.read(userId));
        }
        return returnList;
    }

    public void shareWithUsers(Availability availability, List<String> selectedList) {
        Set<User> userList = getUserList(selectedList);
        availability.setSharedList(userList);
//        return availabilityDAO.update(availability);
    }
}
