package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.Friend;

import java.util.List;

import static uk.co.socialcalendar.entities.FriendStatus.*;

public class FriendFacadeImpl implements FriendFacade {

	FriendDAO friendDAO;

	public void setFriendDAO(FriendDAO friendDAO) {
		this.friendDAO = friendDAO;
	}

	public Friend createFriendRequest(String requesterName,
			String requesteeName) {
		return new Friend(requesterName, requesteeName, PENDING);
	}

	public Friend acceptFriendRequest(int friendId) {
		Friend friend = friendDAO.read(friendId);
		friend.setStatus(ACCEPTED);
		friendDAO.save(friend);
		return friend;
	}

	public Friend declineFriendRequest(int friendId) {
		Friend friend = friendDAO.read(friendId);
		friend.setStatus(DECLINED);
		friendDAO.save(friend);
		return friend;
	}

	public List<Friend> getConfirmedFriends(String user) {
		List<Friend> returnList = friendDAO.getListOfConfirmedFriendsByRequesterName(user);
		returnList.addAll(friendDAO.getListOfConfirmedFriendsByRequesteeName(user));
		return returnList;
	}

	public boolean saveFriend(Friend friend){
		friendDAO.save(friend);
		return true;
	}

	@Override
	public List<Friend> getFriendRequests(String user) {
		return friendDAO.getFriendRequests(user);
	}

	@Override
	public Friend getFriend(int friendId) {
		return friendDAO.read(friendId);
	}


}
