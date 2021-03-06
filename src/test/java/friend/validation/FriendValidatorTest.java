package friend.validation;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.friend.entities.Friend;
import uk.co.socialcalendar.friend.entities.FriendValidator;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.co.socialcalendar.friend.entities.FriendStatus.*;

public class FriendValidatorTest {
    FriendValidator friendValidator;
    Friend friend;
    private final static String REQUESTER_EMAIL = "requesterEmail";
    private final static String BEFRIENDED_EMAIL = "befriendedEmail";

    @Before
    public void setup(){
        friendValidator = new FriendValidator();
    }

    @Test
    public void canCreateInstance(){
        assertTrue(friendValidator instanceof FriendValidator);
    }

    @Test
    public void nullRequesterEmailIsInvalid(){
        friend = new Friend(null, BEFRIENDED_EMAIL, ACCEPTED);
        assertFalse(friendValidator.validRequesterEmail(friend));
    }

    @Test
    public void emptyRequesterEmailIsInvalid(){
        friend = new Friend("", BEFRIENDED_EMAIL, ACCEPTED);
        assertFalse(friendValidator.validRequesterEmail(friend));
    }

    @Test
    public void populatedRequesterEmailIsValid(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
        assertTrue(friendValidator.validRequesterEmail(friend));
    }

    @Test
    public void nullBefriendedEmailIsInvalid(){
        friend = new Friend(REQUESTER_EMAIL, null, ACCEPTED);
        assertFalse(friendValidator.validBefriendedEmail(friend));
    }

    @Test
    public void emptyBefriendedEmailIsInvalid(){
        friend = new Friend(REQUESTER_EMAIL, "", ACCEPTED);
        assertFalse(friendValidator.validBefriendedEmail(friend));
    }

    @Test
    public void populatedBefriendedEmailIsValid(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
        assertTrue(friendValidator.validBefriendedEmail(friend));
    }

    @Test
    public void unknownStatusIsInvalid(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, UNKNOWN);
        assertFalse(friendValidator.validStatus(friend));
    }

    @Test
    public void acceptedStatusIsValid(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, ACCEPTED);
        assertTrue(friendValidator.validStatus(friend));
    }

    @Test
    public void declinedStatusIsValid(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, DECLINED);
        assertTrue(friendValidator.validStatus(friend));
    }

    @Test
    public void pendingStatusIsValid(){
        friend = new Friend(REQUESTER_EMAIL, BEFRIENDED_EMAIL, PENDING);
        assertTrue(friendValidator.validStatus(friend));
    }

}
