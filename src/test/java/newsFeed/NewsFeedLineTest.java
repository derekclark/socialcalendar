package newsFeed;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import uk.co.socialcalendar.newsFeed.NewsFeedLine;

import static org.junit.Assert.assertEquals;

public class NewsFeedLineTest {
    static final LocalDateTime START_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 0);
    static final LocalDateTime END_DATE = new LocalDateTime(2015, 1, 2, 0, 0, 30);
    NewsFeedLine newsFeedLine = new NewsFeedLine();

    @Test
    public void canSetAvailabilityId(){
        newsFeedLine.setAvailabilityId(1);
        assertEquals(1, newsFeedLine.getAvailabilityId());
    }

    @Test
    public void canSetStartDate(){
        newsFeedLine.setStartDateTime(START_DATE);
        assertEquals(START_DATE, newsFeedLine.getStartDateTime());
    }

    @Test
    public void testEquality(){
        NewsFeedLine nl1 = new NewsFeedLine(1, "title", START_DATE, END_DATE, "date line", "N", "name", "email",
                "facebookid", "text line", "status","available",null, null, null, null, "url", "key", false);
        NewsFeedLine nl2 = new NewsFeedLine(1, "title", START_DATE, END_DATE, "date line", "N", "name", "email",
                "facebookid", "text line", "status","available",null, null, null, null, "url", "key", false);
        assertEquals(nl1, nl2);
    }


}
