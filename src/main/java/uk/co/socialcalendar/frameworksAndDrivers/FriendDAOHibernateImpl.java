package uk.co.socialcalendar.frameworksAndDrivers;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.useCases.FriendDAO;

import java.util.ArrayList;
import java.util.List;

public class FriendDAOHibernateImpl implements FriendDAO {

	public FriendDAOHibernateImpl(){

	}

	public Object setQueryStringOwnersAcceptedFriends(String ownerEmail) {
		return "select friendEmail from Friend "
				+ "where ownerEmail = " + ownerEmail + " and status = " + FriendStatus.ACCEPTED;
	}

	public Object setQueryStringOwneesAcceptedFriends(String friendEmail) {
		return "select ownerEmail from Friends "
				+ "where friendEmail = " + friendEmail + " and status = " + FriendStatus.ACCEPTED;
	}

	@Override
	public boolean save(Friend friend) {
		return true;
	}

	public boolean isNotSet(String value){
		return (value == null || value.isEmpty());
	}

	@Override
	public Friend read(Friend friendToFind) {
		return null;
	}

	@Override
	public boolean update(Friend friend) {
		return true;
	}

	@Override
	public List<Friend> getListOfConfirmedFriendsByRequesterName(String friendRequesterName) {

		List<Friend> friendList = new ArrayList<Friend>();
		return friendList;
	}

	@Override
	public List<Friend> getListOfConfirmedFriendsByRequesteeName(String friendRequesteeName) {

		List<Friend> friendList = new ArrayList<Friend>();
		return friendList;
	}

	@Override
	public List<Friend> getListOfPendingFriendsByRequesterName(String friendRequesterName) {
		List<Friend> friendList = new ArrayList<Friend>();
		return friendList;
	}

	@Override
	public boolean acceptFriend(Friend friendOne) {
		return true;
	}

	@Override
	public boolean declineFriend(Friend friendOne) {
		return true;
	}

	@Override
	public List<Friend> getFriendRequests(String user) {
		return null;
	}


}
