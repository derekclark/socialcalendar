package uk.co.socialcalendar.entities;


public class Friend {
	
	int friendId;
	String requesterEmail;
	String beFriendedEmail;
	FriendStatus status;
	
	public FriendStatus getStatus() {
		return status;
	}

	public void setStatus(FriendStatus status) {
		this.status = status;
	}

	public Friend(){
		this.status = FriendStatus.UNKNOWN;
	}
	
	public Friend(String requesterEmail, String requestedName, FriendStatus status){
		this.requesterEmail = requesterEmail;
		this.beFriendedEmail = requestedName;
		this.status = status;
	}

	public String getRequesterEmail() {
		return requesterEmail;
	}

	public void setRequesterEmail(String requesterEmail) {
		this.requesterEmail = requesterEmail;
	}

	public String getBeFriendedEmail() {
		return beFriendedEmail;
	}

	public void setBeFriendedEmail(String requestedName) {
		this.beFriendedEmail = requestedName;
	}

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public Object getStatusString() {
		return status.toString();
	}

	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())){
			return false;
		}
		Friend friend = (Friend) obj;
		return friendId == friend.getFriendId();
	}

	public int hashcode(){
		int hash = 7;
		hash = 31 * hash + friendId;
		return hash;
	}
	
}
