package uk.co.socialcalendar.availability.persistence;

import uk.co.socialcalendar.availability.entities.Availability;

import java.util.List;

public interface AvailabilityDAO {
    public int save(Availability availability);
    public Availability read(int id);
    public boolean update(Availability  availability);
    public List<Availability> readAllOwnersOpenAvailabilities(String owner);
}
