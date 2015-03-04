package uk.co.socialcalendar.frameworksAndDrivers;

import org.hibernate.*;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.models.FriendValidator;
import uk.co.socialcalendar.useCases.FriendDAO;

import java.util.List;

public class FriendDAOHibernateImpl implements FriendDAO {

    SessionFactory sessionFactory;

    FriendValidator friendValidator;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        friendValidator = new FriendValidator();
    }

    public FriendDAOHibernateImpl(){

	}

    @Override
    public boolean save(Friend friend) {
        if (!canUpdate(friend)){
            return false;
        }

        Session session = sessionFactory.getCurrentSession();
        session.save(friend);
        return true;
    }

    public boolean canUpdate(Friend friend) {
        if (friendValidator.validBefriendedEmail(friend) &&
                friendValidator.validId(friend) &&
                friendValidator.validRequesterEmail(friend) &&
                friendValidator.validStatus(friend)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validFriendId(Friend friend){
        if (friend.getFriendId() == 0){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Friend read(int friendId) {
        return null;
    }

    @Override
    public boolean updateStatus(int friendId, FriendStatus status) {
        return false;
    }

    @Override
    public List<Friend> getListOfConfirmedFriendsByRequester(String friendRequesterEmail) {
        return null;
    }

    @Override
    public List<Friend> getListOfConfirmedFriendsByBeFriended(String friendBeFriendedEmail) {
        return null;
    }

    @Override
    public List<Friend> getListOfPendingFriendsByRequester(String friendRequesterName) {
        return null;
    }

    @Override
    public boolean acceptFriend(int friendId) {
        return false;
    }

    @Override
    public boolean declineFriend(int friendId) {
        return false;
    }

    @Override
    public List<Friend> getFriendRequests(String user) {
        return null;
    }
}
