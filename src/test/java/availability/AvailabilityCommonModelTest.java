package availability;


import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.availability.controllers.AvailabilityCommonModel;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityCommonModelTest {

    AvailabilityCommonModel availabilityCommonModel;
    FriendModelFacade mockFriendModelFacade;

    @Before
    public void setup(){
        availabilityCommonModel = new AvailabilityCommonModel();
        mockFriendModelFacade = mock(FriendModelFacade.class);
        availabilityCommonModel.setFriendModelFacade(mockFriendModelFacade);
    }

    @Test
    public void returnsSection(){
        Map<String, Object> actualModelMap = availabilityCommonModel.getSection();
        assertEquals("availability", actualModelMap.get("section"));
    }

    @Test
    public void returnsNewAvailabilityObject(){
        Map<String, Object> actualModelMap = availabilityCommonModel.getNewAvailability();
        assertEquals(new Availability(), actualModelMap.get("newAvailability"));
    }

    @Test
    public void returnsFriendList(){
        List<FriendModel> expectedFriendList = setExpectedFriendList();
        when(mockFriendModelFacade.getFriendModelList(anyString())).thenReturn(expectedFriendList);
        Map<String, Object> actualModelMap = availabilityCommonModel.getFriendList("me");
        assertEquals(expectedFriendList, actualModelMap.get("friendList"));
    }

    public List<FriendModel> setExpectedFriendList(){
        FriendModel friendModel1 = new FriendModel(new User("friendEmail1","friendName1","facebookId1"));
        FriendModel friendModel2 = new FriendModel(new User("friendEmail2","friendName2","facebookId2"));

        List<FriendModel> expectedFriendList = new ArrayList<FriendModel>();
        expectedFriendList.add(friendModel1);
        expectedFriendList.add(friendModel2);

        return expectedFriendList;
    }

}
