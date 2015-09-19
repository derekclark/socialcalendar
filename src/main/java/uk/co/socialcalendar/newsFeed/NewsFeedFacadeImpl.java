package uk.co.socialcalendar.newsFeed;

import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.AvailabilityDAO;
import java.util.ArrayList;
import java.util.List;

public class NewsFeedFacadeImpl implements NewsFeedFacade{
    public static final String PRETTY_DATE_FORMAT = "YYYY-MM-dd HH:mm";
    AvailabilityDAO availabilityDAO;

    public void setAvailabilityDAO(AvailabilityDAO availabilityDAO) {
        this.availabilityDAO = availabilityDAO;
    }

    @Override
    public List<NewsFeedLine> getNewsFeed(String me) {
        List <Availability> availabilitiesIOwn = getMyAvailabilities(me);
        List<NewsFeedLine> newsFeedLines = new ArrayList<NewsFeedLine>();
        for (Availability avail:availabilitiesIOwn){
            newsFeedLines.add(convertAvailabilityToNewsFeedLine(avail));
        }
        return newsFeedLines;
    }

    public List<Availability> getMyAvailabilities(String me) {
        return availabilityDAO.readAllOwnersOpenAvailabilities(me);
    }

    public NewsFeedLine convertAvailabilityToNewsFeedLine(Availability availability){
        NewsFeedLine newsFeedLine = new NewsFeedLine();
        newsFeedLine.setAvailabilityId(availability.getId());
        newsFeedLine.setTitle(availability.getTitle());
        newsFeedLine.setStartDateTime(availability.getStartDate().toString(PRETTY_DATE_FORMAT));
        newsFeedLine.setEndDateTime(availability.getEndDate().toString(PRETTY_DATE_FORMAT));
        newsFeedLine.setOwnerEmail(availability.getOwnerEmail());
        newsFeedLine.setOwnerName(availability.getOwnerName());
        newsFeedLine.setUrl("amendAvailability?id=" + availability.getId());
        return newsFeedLine;
    }
}
