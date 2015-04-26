package uk.co.socialcalendar.availability;

public class AvailabilityFacadeImpl implements AvailabilityFacade{
    AvailabilityDAO availabilityDAO;

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
}
