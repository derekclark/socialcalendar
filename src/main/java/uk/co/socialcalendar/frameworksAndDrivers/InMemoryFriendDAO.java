package uk.co.socialcalendar.frameworksAndDrivers;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.useCases.FriendDAO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFriendDAO implements FriendDAO{
	private static final String EMAIL1 = "userEmail1";
	private static final String EMAIL2 = "userEmail2";
	private static final String EMAIL3 = "userEmail3";
	private static final String EMAIL4 = "userEmail4";
	private static final String EMAIL5 = "userEmail5";

	List<Friend> listOfSavedFriends = new ArrayList<Friend>();
	public InMemoryFriendDAO(){
	}
	
	@Override
	public boolean save(Friend friend) {

		if (isAlreadyPresent(friend)) return false;
		if (isNotSet(friend.getBeFriendedEmail()) || isNotSet(friend.getRequesterEmail()) ){
			return false;
		}
		
		if (friend.getStatus() == FriendStatus.UNKNOWN){
			return false;
		}
		this.listOfSavedFriends.add(friend);
		return true;
	}

	private boolean isAlreadyPresent(Friend friend) {
		for (Friend f:listOfSavedFriends){
			if (f.getFriendId() == friend.getFriendId()){
				return true;
			}
		}
		return false;
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
	public boolean updateStatus(int friendId, FriendStatus status) {
		for (Friend f: listOfSavedFriends){
			if (f.getFriendId() == friendId){
				f.setStatus(status);
				return true;
			}

		}
		return false;
	}

	@Override
	public List<Friend> getMyAcceptedFriends(String email) {
		System.out.println("in inMemoryFriendDAO getMyAcceptedFriends for " + email);
		for (Friend f:listOfSavedFriends){
			System.out.println("saved friend=" + f.getRequesterEmail());
		}
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesterEmail().equals(email) && friend.getStatus() == FriendStatus.ACCEPTED){
				friendList.add(friend);
			}
		}

		for (Friend friend: listOfSavedFriends){
			if (friend.getBeFriendedEmail().equals(email) && friend.getStatus() == FriendStatus.ACCEPTED){
				friendList.add(friend);
			}
		}

		return friendList;
	}

	@Override
	public List<Friend> getMyFriendInvites(String requesterEmail) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getBeFriendedEmail().equals(requesterEmail) && friend.getStatus() == FriendStatus.PENDING){
				friendList.add(friend);
			}
		}

		return friendList;
	}

	@Override
	public boolean doesFriendshipExist(String email1, String email2) {
		return false;
	}

	;

	public List<Friend> getListOfSavedFriends() {
		return listOfSavedFriends;
	}

	public void populate(){
		save(createFriend(new Friend(EMAIL1, EMAIL3, FriendStatus.ACCEPTED), 1));
		save(createFriend(new Friend(EMAIL4, EMAIL2, FriendStatus.ACCEPTED), 2));
		save(createFriend(new Friend(EMAIL4, EMAIL1, FriendStatus.ACCEPTED), 3));
		save(createFriend(new Friend(EMAIL5, EMAIL1, FriendStatus.PENDING), 4));
		System.out.println("populating friend in memory database with " + listOfSavedFriends.size() + " friends");
		for (Friend f: listOfSavedFriends){
			System.out.println(f.getFriendId());
		}
	}

	private Friend createFriend(Friend friend, int friendId){
		friend.setFriendId(friendId);
		return friend;
	}

}
