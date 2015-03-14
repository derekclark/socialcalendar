package uk.co.socialcalendar.useCases.friend;

import uk.co.socialcalendar.entities.Friend;

import java.util.List;

public interface FriendFacade {

    public Friend createFriendRequest(String requesterName, String requesteeName);
    public boolean acceptFriendRequest(int friendId);
    public boolean declineFriendRequest(int friendId);
    public List<Friend> getMyAcceptedFriends(String user);
    public boolean saveFriend(Friend friend);
    List<Friend> getFriendRequests(String user);
    Friend getFriend(int friendId);
}
