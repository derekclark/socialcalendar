package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.Friend;

import java.util.List;

public interface FriendFacade {

    public Friend createFriendRequest(String requesterName, String requesteeName);
    public Friend acceptFriendRequest(String requesterName, String requesteeName);
    public Friend declineFriendRequest(String requesterName, String requesteeName);
    public List<Friend> getConfirmedFriends(String user);
    public boolean saveFriend(Friend friend);
    List<Friend> getFriendRequests(String user);
}
