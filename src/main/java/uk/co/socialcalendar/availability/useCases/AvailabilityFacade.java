package uk.co.socialcalendar.availability.useCases;

import uk.co.socialcalendar.availability.entities.Availability;

public interface AvailabilityFacade {
    public int create(Availability availability);
    public Availability get(int id);
    public boolean update(Availability availability);
}
