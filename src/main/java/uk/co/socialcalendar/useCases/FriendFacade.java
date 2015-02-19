package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.Friend;

import java.util.List;

public interface FriendFacade {

    public Friend createFriendRequest(String requesterName, String requesteeName);
    public Friend acceptFriendRequest(int friendId);
    public Friend declineFriendRequest(int friendId);
    public List<Friend> getConfirmedFriends(String user);
    public boolean saveFriend(Friend friend);
    List<Friend> getFriendRequests(String user);
    Friend getFriend(int friendId);
}
