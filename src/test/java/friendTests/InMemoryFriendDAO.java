package friendTests;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.useCases.FriendDAO;

import java.util.ArrayList;
import java.util.List;

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
		if (isNotSet(friend.getBeFriended()) || isNotSet(friend.getRequesterEmail()) ){
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
	public Friend read(int friendId) {
		for (Friend friend: listOfSavedFriends){
			if (friend.getFriendId() == friendId){
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
			if (friend.getRequesterEmail().equals(friendRequesterName) && friend.getStatus() == FriendStatus.ACCEPTED){
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public List<Friend> getListOfConfirmedFriendsByRequesteeName(String friendRequesteeName) {

		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getBeFriended().equals(friendRequesteeName) && friend.getStatus() == FriendStatus.ACCEPTED){
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public List<Friend> getListOfPendingFriendsByRequesterName(String friendRequesterName) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesterEmail().equals(friendRequesterName) && friend.getStatus() == FriendStatus.PENDING){
				friendList.add(friend);
			}
		}

		return friendList;
	}


	@Override
	public boolean acceptFriend(int friendId) {
		Friend friend=read(friendId);
		friend.setStatus(FriendStatus.ACCEPTED);
		return true;
	}

	@Override
	public boolean declineFriend(int friendId) {
		Friend friend=read(friendId);
		friend.setStatus(FriendStatus.DECLINED);
		return true;
	}

	@Override
	public List<Friend> getFriendRequests(String user) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getBeFriended().equals(user) && friend.getStatus() == FriendStatus.PENDING){
				friendList.add(friend);
			}
		}
		return friendList;
	};

	public List<Friend> getListOfSavedFriends() {
		return listOfSavedFriends;
	}


}
