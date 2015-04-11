package uk.co.socialcalendar.useCases.friend;

import uk.co.socialcalendar.entities.Friend;

import java.util.List;

public interface FriendFacade {

    public int createFriendRequest(String requesterName, String requesteeName);
    public boolean acceptFriendRequest(int friendId);
    public boolean declineFriendRequest(int friendId);
    public List<Friend> getMyAcceptedFriends(String me);
    public boolean saveFriend(Friend friend);
    List<Friend> getFriendRequests(String me);
    List<Friend> getFriendRequestsMadeByMe(String me);
    Friend getFriend(int friendId);
}
