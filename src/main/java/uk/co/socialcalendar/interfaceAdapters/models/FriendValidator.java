package uk.co.socialcalendar.interfaceAdapters.models;

import uk.co.socialcalendar.entities.Friend;
import static uk.co.socialcalendar.entities.FriendStatus.*;

public class FriendValidator {

    public boolean validRequesterEmail(Friend friend){
        if (friend.getRequesterEmail() == null || friend.getRequesterEmail().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean validBefriendedEmail(Friend friend){
        if (friend.getBeFriendedEmail() == null || friend.getBeFriendedEmail().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean validStatus(Friend friend){
        if (friend.getStatus() == UNKNOWN){
            return false;
        }
        return true;
    }

    public boolean validId(Friend friend){
        if (friend.getFriendId() == 0){
            return false;
        }
        return true;
    }
}
