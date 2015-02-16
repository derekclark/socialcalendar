package uk.co.socialcalendar.entities;


public class Friend {
	
	int friendId;
	String requesterName;
	String requesteeName;
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
	
	public Friend(String requesterName, String requestedName, FriendStatus status){
		this.requesterName = requesterName;
		this.requesteeName = requestedName;
		this.status = status;
	}

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public String getRequesteeName() {
		return requesteeName;
	}

	public void setRequesteeName(String requestedName) {
		this.requesteeName = requestedName;
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
