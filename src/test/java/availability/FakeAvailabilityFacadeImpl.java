package availability;

import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakeAvailabilityFacadeImpl implements AvailabilityFacade{
    Availability availability;
    boolean createMethodCalled;
    UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Availability getAvailability() {
        return availability;
    }

    public boolean isCreateMethodCalled() {
        return createMethodCalled;
    }

    public FakeAvailabilityFacadeImpl(){
        createMethodCalled = false;
    }

    @Override
    public int create(Availability availability) {
        this.availability = availability;
        this.availability.setId(0);

        createMethodCalled = true;
        return 0;
    }

    @Override
    public Availability get(int id) {
        return availability;
    }

    @Override
    public boolean update(Availability availability) {
        return false;
    }

    public Set<User> getUserList(List<String> selectedList) {
        Set<User> returnList = new HashSet<User>();
        for (String userId:selectedList){
            returnList.add(userDAO.read(userId));
        }
        return returnList;
    }

    @Override
    public void shareWithUsers(Availability availability, List<String> selectedList) {
        Set<User> userList = getUserList(selectedList);
        availability.setSharedList(userList);
    }

}
