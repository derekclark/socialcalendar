package uk.co.socialcalendar.availability.useCases;

import uk.co.socialcalendar.availability.entities.Availability;

import java.util.List;

public interface AvailabilityFacade {
    public int create(Availability availability);
    public Availability get(int id);
    public boolean update(Availability availability);
    public List<Availability> getOwnersOpenAvailabilities(String owner);
}
