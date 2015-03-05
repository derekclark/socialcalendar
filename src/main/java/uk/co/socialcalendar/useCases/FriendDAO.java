package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;

import java.util.List;

public interface FriendDAO {
	public boolean save(Friend friend);
	public Friend read(int friendId);
	public boolean updateStatus(int friendId, FriendStatus status);
	public List<Friend> getMyAcceptedFriends(String email);
	public List<Friend> getListOfPendingFriendsByRequester(String name);
	List<Friend> getFriendRequests(String user);
}
