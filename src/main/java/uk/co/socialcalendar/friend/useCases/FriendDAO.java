package uk.co.socialcalendar.friend.useCases;

import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.friend.entities.FriendStatus;

import java.util.List;

public interface FriendDAO {
	public int save(Friend friend);
	public Friend read(int friendId);
	public boolean updateStatus(int friendId, FriendStatus status);
	public List<Friend> getMyAcceptedFriends(String email);
	public List<Friend> getFriendRequestsMadeOnMe(String name);
	public List<Friend> getFriendRequestsMadeByMe(String me);
	public boolean friendshipExists(String email1, String email2);
}
