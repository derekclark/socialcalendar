package uk.co.socialcalendar.newsFeed;

import java.util.List;

public interface NewsFeedFacade {
    public List<NewsFeedLine> getNewsFeed(String me);
}
