package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.Friend;

import java.util.List;

public interface FriendDAO {

	public boolean save(Friend friend);
	public Friend read(Friend friend);
	public boolean update(Friend friend);
	public List<Friend> getListOfConfirmedFriendsByRequesterName(String friendRequesterName);
	public List<Friend> getListOfConfirmedFriendsByRequesteeName(String friendRequesterName);
	public List<Friend> getListOfPendingFriendsByRequesterName(String friendRequesterName);
	public boolean acceptFriend(Friend friend);
	public boolean declineFriend(Friend friend);
	List<Friend> getFriendRequests(String user);
}
