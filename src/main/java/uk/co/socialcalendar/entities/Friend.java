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

	public Friend(int friendId, String requesterEmail, String beFriendedEmail, FriendStatus status) {
		this.friendId = friendId;
		this.requesterEmail = requesterEmail;
		this.beFriendedEmail = beFriendedEmail;
		this.status = status;
	}
	
	public Friend(String requesterEmail, String beFriendedEmail, FriendStatus status){
		this.requesterEmail = requesterEmail;
		this.beFriendedEmail = beFriendedEmail;
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

		if (! this.requesterEmail.equals(friend.getRequesterEmail())) return false;
		if (! this.beFriendedEmail.equals(friend.getBeFriendedEmail())) return false;
		if (! this.status.equals(friend.getStatus())) return false;

		return friendId == friend.getFriendId();
	}

	public int hashcode(){
		int hash = 7;
		hash = 31 * hash + friendId + requesterEmail.hashCode() + beFriendedEmail.hashCode() + status.hashCode();
		return hash;
	}

	@Override
	public String toString(){
		return "requester email=" + this.requesterEmail + " befriended email=" + this.beFriendedEmail
				+ " status=" + this.status.toString() + " friendId=" + this.friendId;
	}
	
}
