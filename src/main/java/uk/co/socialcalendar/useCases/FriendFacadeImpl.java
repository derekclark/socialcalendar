package uk.co.socialcalendar.useCases;

import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.frameworksAndDrivers.FriendDAOHibernateImpl;

import java.util.ArrayList;
import java.util.List;

public class FriendFacadeImpl implements FriendFacade {

	FriendDAO friendDAO;

	public void setFriendDAO(FriendDAO friendDAO) {
		this.friendDAO = friendDAO;
	}

	public Friend createFriendRequest(String requesterName,
			String requesteeName) {
		return new Friend(requesterName, requesteeName, FriendStatus.PENDING);
	}

	public Friend acceptFriendRequest(String requesterName,
			String requesteeName) {
		return new Friend(requesterName, requesteeName, FriendStatus.ACCEPTED);
	}

	public Friend declineFriendRequest(String requesterName,
			String requesteeName) {
		return new Friend(requesterName, requesteeName, FriendStatus.DECLINED);
	}

	public List<Friend> getConfirmedFriends(String user) {
		List<Friend> returnList = friendDAO.getListOfConfirmedFriendsByRequesterName(user);
		returnList.addAll(friendDAO.getListOfConfirmedFriendsByRequesteeName(user));
		return returnList;

//		for (Friend friend:friendsList){
//			if (friend.getStatus().equals(FriendStatus.ACCEPTED)
//					&& (friend.getRequesteeName().equals(user) || friend.getRequesterName().equals(user))){
//				returnList.add(friend);
//			}
//		}
	}

	public boolean saveFriend(Friend friend){
		friendDAO.save(friend);
		return true;
	}

	@Override
	public List<Friend> getFriendRequests(String user) {
		return friendDAO.getFriendRequests(user);
	}

}
