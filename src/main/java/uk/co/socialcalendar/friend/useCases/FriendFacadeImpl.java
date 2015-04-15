package uk.co.socialcalendar.friend.useCases;

import uk.co.socialcalendar.friend.entities.Friend;
import java.util.List;
import static uk.co.socialcalendar.friend.entities.FriendStatus.*;

public class FriendFacadeImpl implements FriendFacade {

	FriendDAO friendDAO;

	public void setFriendDAO(FriendDAO friendDAO) {
		this.friendDAO = friendDAO;
	}

	public int createFriendRequest(String requesterName,
			String requesteeName) {
		System.out.println("creating friend request for " + requesterName + " " + requesteeName);
		int id = friendDAO.save(new Friend(requesterName, requesteeName, PENDING));
		return id;
	}

	public boolean acceptFriendRequest(int friendId) {
		friendDAO.updateStatus(friendId, ACCEPTED);
		return true;
	}

	public boolean declineFriendRequest(int friendId) {
		friendDAO.updateStatus(friendId, DECLINED);
		return true;
	}

	public List<Friend> getMyAcceptedFriends(String me) {
		return friendDAO.getMyAcceptedFriends(me);
	}

	public boolean saveFriend(Friend friend){
		friendDAO.save(friend);
		return true;
	}

	@Override
	public List<Friend> getFriendRequests(String me) {
		return friendDAO.getFriendRequestsMadeOnMe(me);
	}

	@Override
	public List<Friend> getFriendRequestsMadeByMe(String me) {
		return friendDAO.getFriendRequestsMadeByMe(me);
	}

	@Override
	public Friend getFriend(int friendId) {
		return friendDAO.read(friendId);
	}


}
