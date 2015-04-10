package uk.co.socialcalendar.useCases.friend;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;

import java.util.List;

public interface FriendDAO {
	public int save(Friend friend);
	public Friend read(int friendId);
	public boolean updateStatus(int friendId, FriendStatus status);
	public List<Friend> getMyAcceptedFriends(String email);
	public List<Friend> getMyFriendInvites(String name);
	public boolean friendshipExists(String email1, String email2);
}
