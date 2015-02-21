package uk.co.socialcalendar.frameworksAndDrivers;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.useCases.FriendDAO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFriendDAO implements FriendDAO{
	private static final String BEFRIENDED1 = "befriendedEmail1";
	private static final String BEFRIENDED2 = "befriendedEmail2";
	private static final String REQUESTER1 = "userEmail1";
	private static final String REQUESTER2 = "userEmail2";

	List<Friend> listOfSavedFriends = new ArrayList<Friend>();
	public InMemoryFriendDAO(){
	}
	
	@Override
	public boolean save(Friend friend) {
		if (isNotSet(friend.getBeFriendedEmail()) || isNotSet(friend.getRequesterEmail()) ){
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
	public List<Friend> getListOfConfirmedFriendsByRequester(String requesterEmail) {
		System.out.println("in inmemoryFriendDAO getconfirmedfriendsbyrequester for " + requesterEmail);
		for (Friend f:listOfSavedFriends){
			System.out.println("saved friend=" + f.getRequesterEmail());
		}
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesterEmail().equals(requesterEmail) && friend.getStatus() == FriendStatus.ACCEPTED){
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public List<Friend> getListOfConfirmedFriendsByBeFriended(String beFriendedEmail) {

		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getBeFriendedEmail().equals(beFriendedEmail) && friend.getStatus() == FriendStatus.ACCEPTED){
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public List<Friend> getListOfPendingFriendsByRequester(String requesterEmail) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesterEmail().equals(requesterEmail) && friend.getStatus() == FriendStatus.PENDING){
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
			if (friend.getBeFriendedEmail().equals(user) && friend.getStatus() == FriendStatus.PENDING){
				friendList.add(friend);
			}
		}
		return friendList;
	};

	public List<Friend> getListOfSavedFriends() {
		return listOfSavedFriends;
	}

	public void populate(){
		save(createFriend(new Friend(REQUESTER1, BEFRIENDED1, FriendStatus.ACCEPTED), 1));
		save(createFriend(new Friend(REQUESTER2, BEFRIENDED2, FriendStatus.ACCEPTED), 2));
		save(createFriend(new Friend(REQUESTER2, REQUESTER1, FriendStatus.ACCEPTED), 3));
		System.out.println("populating friend inmemory database with " + listOfSavedFriends.size() + " friends");
		for (Friend f: listOfSavedFriends){
			System.out.println(f.getFriendId());
		}
	}

	private Friend createFriend(Friend friend, int friendId){
		friend.setFriendId(friendId);
		return friend;
	}

}
