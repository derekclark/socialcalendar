package uk.co.socialcalendar.availability;

public interface AvailabilityDAO {
    public int save(Availability availability);
    public Availability read(int id);
}
