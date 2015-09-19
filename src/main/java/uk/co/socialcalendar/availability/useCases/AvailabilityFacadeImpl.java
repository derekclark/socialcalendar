package uk.co.socialcalendar.availability.useCases;

import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAO;

import java.util.List;

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

    @Override
    public List<Availability> getOwnersOpenAvailabilities(String owner) {
        return null;
    }
}
