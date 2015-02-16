package friendTests;

import java.util.ArrayList;
import java.util.List;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.useCases.FriendDAO;
import uk.co.socialcalendar.entities.FriendStatus;

public class InMemoryFriendDAO implements FriendDAO{
	private final static int FRIEND_ID = 1;
	private final static String FRIEND_REQUESTEE = "john.smith@mail.com";
	private final static String FRIEND_REQUESTER = "jill.sullivan@mail.com";
	private final static String FRIEND_PENDING_STATUS = "PENDING";

	List<Friend> listOfSavedFriends = new ArrayList<Friend>();
	Friend existentFriend;
	
	public InMemoryFriendDAO(){
		existentFriend = new Friend(FRIEND_REQUESTER, FRIEND_REQUESTEE,FriendStatus.PENDING);
		existentFriend.setFriendId(FRIEND_ID);
		listOfSavedFriends.add(existentFriend);
	}
	
	@Override
	public boolean save(Friend friend) {
		if (isNotSet(friend.getRequesteeName()) || isNotSet(friend.getRequesterName()) ){
			return false;
		}
		
		if (friend.getStatus() == FriendStatus.UNKNOWN){
			return false;
		}
		this.listOfSavedFriends.add(friend);
		return true;
	}
		
	public boolean isNotSet(String value){
		return (value == null || value.isEmpty());
	}

	@Override
	public Friend read(Friend friendToFind) {
		for (Friend friend: listOfSavedFriends){
			if (friend.getFriendId() == friendToFind.getFriendId()){
				return friend;
			}
		}
		return null;
	}

	@Override
	public boolean update(Friend friend) {
		return true;
	}

	@Override
	public List<Friend> getListOfConfirmedFriendsByRequesterName(String friendRequesterName) {

		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesterName().equals(friendRequesterName) && friend.getStatus() == FriendStatus.ACCEPTED){
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public List<Friend> getListOfConfirmedFriendsByRequesteeName(String friendRequesteeName) {

		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesteeName().equals(friendRequesteeName) && friend.getStatus() == FriendStatus.ACCEPTED){
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public List<Friend> getListOfPendingFriendsByRequesterName(String friendRequesterName) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesterName().equals(friendRequesterName) && friend.getStatus() == FriendStatus.PENDING){
				friendList.add(friend);
			}
		}

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
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesteeName().equals(user) && friend.getStatus() == FriendStatus.PENDING){
				friendList.add(friend);
			}
		}
		return friendList;
	};

}
