package newsFeed;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import uk.co.socialcalendar.newsFeed.NewsFeedLine;

import static org.junit.Assert.assertEquals;

public class NewsFeedLineTest {
    static final LocalDateTime START_DATE = new LocalDateTime(2015, 1, 2, 14, 27, 0);
    static final LocalDateTime END_DATE = new LocalDateTime(2015, 1, 2, 15, 45, 30);
    static final String PRETTY_DATE_PATTERN = "YYYY-MM-dd HH:mm";
    NewsFeedLine newsFeedLine = new NewsFeedLine();

    @Test
    public void canSetAvailabilityId(){
        newsFeedLine.setAvailabilityId(1);
        assertEquals(1, newsFeedLine.getAvailabilityId());
    }

    @Test
    public void canSetStartDate(){
        newsFeedLine.setStartDateTime(START_DATE.toString(PRETTY_DATE_PATTERN));
        assertEquals("2015-01-02 14:27", newsFeedLine.getStartDateTime());
    }

    @Test
    public void testEquality(){
        NewsFeedLine nl1 = new NewsFeedLine(1, "title",
                START_DATE.toString(PRETTY_DATE_PATTERN), END_DATE.toString(PRETTY_DATE_PATTERN), "date line", "N",
                "name", "email",
                "facebookid", "text line", "status","available",null, null, null, null, "url", "key", false);
        NewsFeedLine nl2 = new NewsFeedLine(1, "title",
                START_DATE.toString(PRETTY_DATE_PATTERN), END_DATE.toString(PRETTY_DATE_PATTERN), "date line", "N",
                "name", "email",
                "facebookid", "text line", "status","available",null, null, null, null, "url", "key", false);
        assertEquals(nl1, nl2);
    }
}
