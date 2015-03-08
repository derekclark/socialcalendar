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

	public boolean acceptFriendRequest(int friendId) {
		friendDAO.updateStatus(friendId, ACCEPTED);
		return true;
	}

	public boolean declineFriendRequest(int friendId) {
		friendDAO.updateStatus(friendId, DECLINED);
		return true;
	}

	public List<Friend> getMyAcceptedFriends(String user) {
		return friendDAO.getMyAcceptedFriends(user);
	}

	public boolean saveFriend(Friend friend){
		friendDAO.save(friend);
		return true;
	}

	@Override
	public List<Friend> getFriendRequests(String user) {
		return friendDAO.getMyFriendInvites(user);
	}

	@Override
	public Friend getFriend(int friendId) {
		return friendDAO.read(friendId);
	}


}
