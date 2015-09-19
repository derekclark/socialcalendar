package uk.co.socialcalendar.newsFeed;

import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;

import java.util.List;

public class NewsFeedFacadeImpl implements NewsFeedFacade{
    AvailabilityFacade availabilityFacade;

    public void setAvailabilityFacade(AvailabilityFacade availabilityFacade) {
        this.availabilityFacade = availabilityFacade;
    }

    @Override
    public List<NewsFeedLine> getNewsFeed(String me) {
        return null;
    }

    public List<Availability> getMyAvailabilities(String me) {
        return availabilityFacade.getOwnersOpenAvailabilities(me);
    }
}
