package uk.co.socialcalendar.frameworksAndDrivers.persistence;

import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.useCases.friend.FriendDAO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFriendDAO implements FriendDAO{
	private static final String EMAIL1 = "userEmail1";
	private static final String EMAIL2 = "userEmail2";
	private static final String EMAIL3 = "userEmail3";
	private static final String EMAIL4 = "userEmail4";
	private static final String EMAIL5 = "userEmail5";
	private static final int NOT_SAVED = -1;

	List<Friend> listOfSavedFriends = new ArrayList<Friend>();
	public InMemoryFriendDAO(){
	}
	
	@Override
	public int save(Friend friend) {

		friend.setFriendId(getNextFriendId());
//		if (isAlreadyPresent(friend)) return NOT_SAVED;
		if (isNotSet(friend.getBeFriendedEmail()) || isNotSet(friend.getRequesterEmail()) ){
			return NOT_SAVED;
		}
		
		if (friend.getStatus() == FriendStatus.UNKNOWN){
			return NOT_SAVED;
		}
		listOfSavedFriends.add(friend);
		System.out.println("friend added");
		return friend.getFriendId();
	}

	private int getNextFriendId(){
		if (listOfSavedFriends.size() == 0){
			return 1;
		}
		Friend friend = listOfSavedFriends.get(listOfSavedFriends.size()-1);
		return friend.getFriendId() + 1;
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
	public List<Friend> getMyAcceptedFriends(String me) {
		System.out.println("in inMemoryFriendDAO getMyAcceptedFriends for " + me);
		for (Friend f:listOfSavedFriends){
			System.out.println("saved friend=" + f.getRequesterEmail());
		}
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			System.out.println("checking friend=" + friend.getRequesterEmail() + " " + friend.getBeFriendedEmail());
			if (youAreMyAcceptedFriend(me, friend) || iAmYourAcceptedFriend(me, friend)){
				friendList.add(friend);
			}
		}

		return friendList;
	}

	private boolean iAmYourAcceptedFriend(String me, Friend friend) {
		return friend.getBeFriendedEmail().equals(me) && friend.getStatus() == FriendStatus.ACCEPTED;
	}

	private boolean youAreMyAcceptedFriend(String me, Friend friend) {
		return friend.getRequesterEmail().equals(me) && friend.getStatus() == FriendStatus.ACCEPTED;
	}

	@Override
	public List<Friend> getMyFriendInvites(String me) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			System.out.println("friendrequests looking at=" + friend.getRequesterEmail());
			if (friend.getBeFriendedEmail().equals(me) && friend.getStatus() == FriendStatus.PENDING){
				System.out.println("yes");
				friendList.add(friend);
			}
		}

		return friendList;
	}

	@Override
	public List<Friend> getFriendRequestsMadeByMe(String me) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			System.out.println("friendRequestsMadeByMe looking at=" + friend.getRequesterEmail());
			if (friend.getRequesterEmail().equals(me) && friend.getStatus() == FriendStatus.PENDING){
				System.out.println("yes");
				friendList.add(friend);
			}
		}

		return friendList;
	}

	@Override
	public boolean friendshipExists(String email1, String email2) {
		return true;
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
