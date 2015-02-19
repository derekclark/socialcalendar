package friendTests;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.controllers.FriendRequestController;
import uk.co.socialcalendar.interfaceAdapters.utilities.StubMailNotification;
import uk.co.socialcalendar.useCases.FriendFacadeImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class FriendRequestControllerTest {
    FriendRequestController controller;
    FriendFacadeImpl mockFriendFacade;
    ModelAndView mav;
    Friend friend;
    public static final String FRIEND_VIEW = "friend";
    public static final String USER_ID = "userId";
    public static final String FRIENDS_PAGE_SECTION = "friends";
    StubMailNotification stubMailNotification = new StubMailNotification();

    @Before
    public void setup(){
        controller = new FriendRequestController();
        mockFriendFacade = mock(FriendFacadeImpl.class);
        controller.setFriendFacade(mockFriendFacade);
        friend = new Friend("NAME1","NAME2", FriendStatus.PENDING);
        friend.setFriendId(1);

        setupTestDoubles();
    }

    public void setupTestDoubles(){
        when(mockFriendFacade.getFriend(anyInt())).thenReturn(friend);
        controller.setUserNotification(stubMailNotification);

    }

    @Test
    public void acceptingFriendRequestShouldShowAcceptedConfirmationMessage(){
        mav = controller.acceptFriendRequest(USER_ID, friend.getFriendId());
        assertEquals(mav.getModelMap().get("message"), "You have just accepted a friend request from " + friend.getRequesterEmail());
    }

    @Test
    public void acceptingFriendRequestShouldSetSectionAsFriends(){
        mav = controller.acceptFriendRequest(USER_ID, friend.getFriendId());
        assertEquals(mav.getModelMap().get("section"), FRIENDS_PAGE_SECTION);
    }

    @Test
    public void acceptingFriendRequestShouldRenderFriendsPage(){
        mav = controller.acceptFriendRequest(USER_ID, friend.getFriendId());
        assertEquals(mav.getViewName(),FRIEND_VIEW);
    }

    @Test
    public void decliningFriendRequestShouldShowDeclinedConfirmationMessage(){
        mav = controller.declineFriendRequest(USER_ID, friend.getFriendId());
        assertEquals(mav.getModelMap().get("message"), "You have just declined a friend request from " + friend.getRequesterEmail());
    }

    @Test
    public void decliningFriendRequestShouldSetSectionAsFriends(){
        mav = controller.declineFriendRequest(USER_ID, friend.getFriendId());
        assertEquals(mav.getModelMap().get("section"), FRIENDS_PAGE_SECTION);
    }

    @Test
    public void decliningFriendRequestShouldRenderFriendsPage(){
        mav = controller.declineFriendRequest(USER_ID, friend.getFriendId());
        assertEquals(mav.getViewName(),FRIEND_VIEW);
    }


    @Test
    public void canNotifyUserOfAcceptedRequest(){
        friend.setStatus(FriendStatus.ACCEPTED);
        boolean result = controller.notifyUser(friend);

        assertTrue(result);
        assertEquals(1, stubMailNotification.getMessagesSent());
        assertEquals(friend.getRequesterEmail(),stubMailNotification.getRecipient());
        assertEquals(friend.getBeFriended(),stubMailNotification.getSender());
        assertEquals("Accepted Friend Request", stubMailNotification.getSubject());
        assertEquals(friend.getBeFriended() + " has accepted your friend request", stubMailNotification.getMessage());
    }

    @Test
    public void canNotifyUserOfDeclinedRequest(){
        friend.setStatus(FriendStatus.DECLINED);
        boolean result = controller.notifyUser(friend);

        assertTrue(result);
        assertEquals(1, stubMailNotification.getMessagesSent());
        assertEquals(friend.getRequesterEmail(),stubMailNotification.getRecipient());
        assertEquals(friend.getBeFriended(),stubMailNotification.getSender());
        assertEquals("Declined Friend Request", stubMailNotification.getSubject());
        assertEquals(friend.getBeFriended() + " has declined your friend request", stubMailNotification.getMessage());
    }

    @Test
    public void decliningFriendRequestSendsNotification(){
        mav = controller.declineFriendRequest(USER_ID, friend.getFriendId());
        assertEquals(1, stubMailNotification.getMessagesSent());
    }

    @Test
    public void acceptingFriendRequestSendsNotification(){
        mav = controller.acceptFriendRequest(USER_ID, friend.getFriendId());
        assertEquals(1, stubMailNotification.getMessagesSent());
    }

    @Test
    public void acceptingFriendRequestSetsFriendStatusToAccepted(){
        mav = controller.acceptFriendRequest(USER_ID, friend.getFriendId());
        verify(mockFriendFacade, times(1)).acceptFriendRequest(anyInt());
    }

    @Test
    public void decliningFriendRequestSetsFriendStatusToAccepted(){
        mav = controller.declineFriendRequest(USER_ID, friend.getFriendId());
        verify(mockFriendFacade, times(1)).declineFriendRequest(anyInt());
    }

}
