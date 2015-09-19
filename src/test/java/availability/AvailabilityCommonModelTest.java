package availability;


import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.controllers.AvailabilityCommonModel;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AvailabilityCommonModelTest {

    AvailabilityCommonModel availabilityCommonModel;

    @Before
    public void setup(){
        availabilityCommonModel = new AvailabilityCommonModel();
    }

    @Test
    public void returnsSection(){
        Map<String, Object> actualModelMap = availabilityCommonModel.getSection();
        assertEquals("availability", actualModelMap.get("section"));

    }
}
