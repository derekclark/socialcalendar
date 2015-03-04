package uk.co.socialcalendar.frameworksAndDrivers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.models.FriendValidator;
import uk.co.socialcalendar.useCases.FriendDAO;

import java.util.List;

import static uk.co.socialcalendar.entities.FriendStatus.ACCEPTED;
import static uk.co.socialcalendar.entities.FriendStatus.DECLINED;

public class FriendDAOHibernateImpl implements FriendDAO {

    SessionFactory sessionFactory;


    FriendValidator friendValidator;

    public void setFriendValidator(FriendValidator friendValidator) {
        this.friendValidator = friendValidator;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

    @Override
    public Friend read(int friendId) {
        return (Friend) sessionFactory.getCurrentSession().get(Friend.class, friendId);
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
        Friend friend = read(friendId);
        friend.setStatus(ACCEPTED);
        sessionFactory.getCurrentSession().update(friend);

        return true;
    }

    @Override
    public boolean declineFriend(int friendId) {
        Friend friend = read(friendId);
        friend.setStatus(DECLINED);
        sessionFactory.getCurrentSession().update(friend);

        return true;
    }

    @Override
    public List<Friend> getFriendRequests(String user) {
        return null;
    }
}
