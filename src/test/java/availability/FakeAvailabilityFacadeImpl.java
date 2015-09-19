package availability;

import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;

public class FakeAvailabilityFacadeImpl implements AvailabilityFacade{
    Availability availability;
    boolean createMethodCalled;

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
        createMethodCalled = true;
        return 0;
    }

    @Override
    public Availability get(int id) {
        return null;
    }

    @Override
    public boolean update(Availability availability) {
        return false;
    }
}
