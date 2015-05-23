package uk.co.socialcalendar.availability.persistence;

import uk.co.socialcalendar.availability.entities.Availability;

public interface AvailabilityDAO {
    public int save(Availability availability);
    public Availability read(int id);
    public boolean update(Availability  availability);
}
