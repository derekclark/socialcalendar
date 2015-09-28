package availability;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import testSupport.HttpMocks;
import uk.co.socialcalendar.availability.controllers.AddAvailabilityController;
import uk.co.socialcalendar.availability.controllers.AvailabilityCommonModel;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.persistence.InMemoryAvailability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacadeImpl;
import uk.co.socialcalendar.friend.controllers.FriendModel;
import uk.co.socialcalendar.friend.controllers.FriendModelFacade;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.InMemoryUserDAO;
import uk.co.socialcalendar.user.useCases.UserFacadeImpl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddAvailabilityControllerTest {
    private static final String END_DATE = "2012-01-27 14:15";
    private static final String START_DATE = "2012-01-25 11:12";
    private static final String TITLE = "title";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String ME = "me";
    private static final String MY_NAME = "myName";
    private static final String MY_FACEBOOK_ID = "facebookId";
    AddAvailabilityController controller;
    ModelAndView mav;
    AvailabilityFacadeImpl availabilityFacade = new AvailabilityFacadeImpl();
    InMemoryAvailability inMemoryAvailability = new InMemoryAvailability();
    InMemoryUserDAO inMemoryUserDAO = new InMemoryUserDAO();
    Availability savedAvailability;
    UserFacadeImpl userFacade = new UserFacadeImpl();
    User user1=new User("USER1","NAME1", "FACEBOOK1");
    User user2=new User("USER2","NAME2", "FACEBOOK2");

    FriendModelFacade mockFriendModelFacade = mock(FriendModelFacade.class);
    HttpMocks httpMocks;
    AvailabilityCommonModel mockAvailabilityCommonModel;

    @Before
    public void setup(){
        controller = new AddAvailabilityController();
        setupMocks();
        injectDependencies();
        saveMyUser();
    }

    @Test
    public void addAvailabilityRendersAvailabilityView() throws IOException, ServletException {
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertEquals("availabilityCreate",mav.getViewName());
    }

    public ModelAndView callAddAvailability(String title, String startDate, String endDate, List<String> selectedFriends)
            throws IOException, ServletException {
        return controller.addAvailability(title, startDate, endDate, selectedFriends, httpMocks.getModel(),
                httpMocks.getMockHttpServletRequest(), httpMocks.getMockHttpServletResponse());
    }

    @Test
    public void addAvailabilityReturnsExpectedMessage() throws IOException, ServletException {
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertEquals("You have just created a new availability",mav.getModelMap().get("message"));
    }

    @Test
    public void setsAvailabilityWithOwnerEmail() throws IOException, ServletException {
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertEquals(ME, inMemoryAvailability.read(1).getOwnerEmail());
    }

    @Test
    public void setsAvailabilityWithOwnerName() throws IOException, ServletException {
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertEquals(MY_NAME, inMemoryAvailability.read(1).getOwnerName());
    }

    @Test
    public void setsAvailabilityWithStartDate() throws IOException, ServletException {
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        LocalDateTime expectedDate = LocalDateTime.parse(START_DATE, DateTimeFormat.forPattern(DATE_PATTERN));
        assertEquals(expectedDate, inMemoryAvailability.read(1).getStartDate());
    }

    @Test
    public void setsAvailabilityWithEndDate() throws IOException, ServletException {
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        LocalDateTime expectedDate = LocalDateTime.parse(END_DATE, DateTimeFormat.forPattern(DATE_PATTERN));
        assertEquals(expectedDate, inMemoryAvailability.read(1).getEndDate());
    }

    @Test
    public void setsAvailabilityWithStatus() throws IOException, ServletException {
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertEquals("status", inMemoryAvailability.read(1).getStatus());
    }

    @Test
    public void setsAvailabilityWithTitle() throws IOException, ServletException {
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertEquals(TITLE, inMemoryAvailability.read(1).getTitle());
    }

    @Test
    public void modelReturnsSection() throws IOException, ServletException {
        mockExpectedModel();
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertEquals("availability",mav.getModel().get("section"));
    }

    @Test
    public void modelReturnsNewAvailability() throws IOException, ServletException {
        mockExpectedModel();
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertNotNull(mav.getModelMap().get("newAvailability"));
    }

    @Test
    public void modelReturnsFriendList() throws IOException, ServletException {
        mockExpectedModel();
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);
        assertNotNull(mav.getModelMap().get("friendList"));
    }

    @Test
    public void sharesAvailabilityWithSelectedUsers() throws IOException, ServletException {
        saveUsers();
        List<String> selectedFriends = setSelectedFriendsTo2Users();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);

        savedAvailability = inMemoryAvailability.read(1);
        assertEquals(2, savedAvailability.getSharedList().size());
        assertTrue(findElementInSet(savedAvailability.getSharedList(), user1));
        assertTrue(findElementInSet(savedAvailability.getSharedList(), user2));
    }

    @Test
    public void sharesAvailabilityWhenNoSelectedUsers() throws IOException, ServletException {
        saveUsers();
        List<String> selectedFriends = setSelectedFriendsToNoUsers();
        mav = callAddAvailability(TITLE, START_DATE, END_DATE, selectedFriends);

        savedAvailability = inMemoryAvailability.read(1);
        assertEquals(0, savedAvailability.getSharedList().size());
    }

    public List<String> setSelectedFriendsTo2Users(){
        List<String> selectedFriends = new ArrayList<String>();
        selectedFriends.add(user1.getEmail());
        selectedFriends.add(user2.getEmail());

        return selectedFriends;
    }

    public List<String> setSelectedFriendsToNoUsers(){
        List<String> selectedFriends = new ArrayList<String>();
        return selectedFriends;
    }

    public void saveUsers(){
        inMemoryUserDAO.save(user1);
        inMemoryUserDAO.save(user2);

    }
    public boolean findElementInSet(Set<User> set, User user){
        Iterator<User> iterator = set.iterator();
        while(iterator.hasNext()) {
            User setElement = iterator.next();
            if(setElement.equals(user)) {
                return true;
            }
        }
        return false;
    }
    private Map<String, Object> mockExpectedModel() {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        expectedMap.put("section", "availability");
        expectedMap.put("newAvailability", new Availability());

        List<FriendModel> expectedFriendList = setExpectedFriendList();
        expectedMap.put("friendList", expectedFriendList);

        when(mockFriendModelFacade.getFriendModelList(ME)).thenReturn(expectedFriendList);
        when(mockAvailabilityCommonModel.getAttributes(anyString())).thenReturn(expectedMap);
        return expectedMap;
    }

    public List<FriendModel> setExpectedFriendList(){
        FriendModel friendModel1 = new FriendModel(new User("friendEmail1","friendName1","facebookId1"));
        FriendModel friendModel2 = new FriendModel(new User("friendEmail2","friendName2","facebookId2"));

        List<FriendModel> expectedFriendList = new ArrayList<FriendModel>();
        expectedFriendList.add(friendModel1);
        expectedFriendList.add(friendModel2);

        return expectedFriendList;
    }

    public void injectDependencies(){
        controller.setAvailabilityFacade(availabilityFacade);
        availabilityFacade.setUserDAO(inMemoryUserDAO);
        availabilityFacade.setAvailabilityDAO(inMemoryAvailability);
        controller.setUserFacade(userFacade);
        userFacade.setUserDAO(inMemoryUserDAO);
    }

    public void setupMocks(){
        setupHttpMocks();
        setMockAvailabilityCommonModel();
        controller.setFriendModelFacade(mockFriendModelFacade);
    }

    public void setMockAvailabilityCommonModel(){
        mockAvailabilityCommonModel = mock(AvailabilityCommonModel.class);
        controller.setAvailabilityCommonModel(mockAvailabilityCommonModel);
    }

    public void setupHttpMocks(){
        httpMocks = new HttpMocks();
        controller.setSessionAttributes(httpMocks.getMockSessionAttributes());
    }

    public void saveMyUser(){
        User user=new User(ME, MY_NAME, MY_FACEBOOK_ID);
        inMemoryUserDAO.save(user);
    }



}
