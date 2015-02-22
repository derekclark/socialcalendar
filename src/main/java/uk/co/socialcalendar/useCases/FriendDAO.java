package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;

import java.util.List;

public interface FriendDAO {

	public boolean save(Friend friend);
	public Friend read(int friendId);
	public boolean updateStatus(int friendId, FriendStatus status);
	public List<Friend> getListOfConfirmedFriendsByRequester(String friendRequesterEmail);
	public List<Friend> getListOfConfirmedFriendsByBeFriended(String friendBeFriendedEmail);
	public List<Friend> getListOfPendingFriendsByRequester(String friendRequesterName);
	public boolean acceptFriend(int friendId);
	public boolean declineFriend(int friendId);
	List<Friend> getFriendRequests(String user);
}
