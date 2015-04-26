package uk.co.socialcalendar.availability;

public interface AvailabilityFacade {
    public int create(Availability availability);
    public Availability get(int id);
    public boolean update(Availability availability);
}
