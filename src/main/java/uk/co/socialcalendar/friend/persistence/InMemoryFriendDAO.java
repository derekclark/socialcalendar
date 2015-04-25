package uk.co.socialcalendar.friend.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.socialcalendar.friend.entities.FriendStatus;
import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.friend.useCases.FriendDAO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFriendDAO implements FriendDAO{
	private static final Logger LOG = LoggerFactory.getLogger(InMemoryFriendDAO.class);
	private static final int NOT_SAVED = -1;

	List<Friend> listOfSavedFriends = new ArrayList<Friend>();
	public InMemoryFriendDAO(){
	}
	
	@Override
	public int save(Friend friend) {

		friend.setFriendId(getNextFriendId());
		if (isNotSet(friend.getBeFriendedEmail()) || isNotSet(friend.getRequesterEmail()) ){
			return NOT_SAVED;
		}
		
		if (friend.getStatus() == FriendStatus.UNKNOWN){
			return NOT_SAVED;
		}
		listOfSavedFriends.add(friend);
		LOG.info("friend added");
		return friend.getFriendId();
	}

	private int getNextFriendId(){
		if (listOfSavedFriends.size() == 0){
			return 1;
		}
		Friend friend = listOfSavedFriends.get(listOfSavedFriends.size()-1);
		return friend.getFriendId() + 1;
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
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
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
	public List<Friend> getFriendRequestsMadeOnMe(String me) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getBeFriendedEmail().equals(me) && friend.getStatus() == FriendStatus.PENDING){
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public List<Friend> getFriendRequestsMadeByMe(String me) {
		List<Friend> friendList = new ArrayList<Friend>();
		for (Friend friend: listOfSavedFriends){
			if (friend.getRequesterEmail().equals(me) && friend.getStatus() == FriendStatus.PENDING){
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public boolean friendshipExists(String email1, String email2) {
		return true;
	}
}
