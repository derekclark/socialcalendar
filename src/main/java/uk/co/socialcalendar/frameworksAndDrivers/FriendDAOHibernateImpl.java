package uk.co.socialcalendar.frameworksAndDrivers;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.models.FriendValidator;
import uk.co.socialcalendar.useCases.FriendDAO;

import javax.transaction.Transactional;
import java.util.List;

public class FriendDAOHibernateImpl implements FriendDAO {
    SessionFactory sessionFactory;
    FriendValidator friendValidator;

    public FriendDAOHibernateImpl(){
    }

    public void setFriendValidator(FriendValidator friendValidator) {
        this.friendValidator = friendValidator;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
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
    @Transactional
    public Friend read(int friendId) {
        return (Friend) sessionFactory.getCurrentSession().get(Friend.class, friendId);
    }

    @Override
    @Transactional
    public boolean updateStatus(int friendId, FriendStatus status) {
        Friend friend = read(friendId);
        friend.setStatus(status);
        sessionFactory.getCurrentSession().update(friend);
        return true;
    }

    @Override
    @Transactional
    public List<Friend> getMyAcceptedFriends(String email) {
        @SuppressWarnings("unchecked")
        Query query = sessionFactory.getCurrentSession().createQuery(queryStringForMyAcceptedFriendsWhereIAmOwner(email));
        List<Friend> returnSQLList = query.list();
        query = sessionFactory.getCurrentSession().createQuery(queryStringForMyAcceptedFriendsWhereIAmNotOwner(email));
        returnSQLList.addAll(query.list());
        return returnSQLList;
    }

    @Override
    @Transactional
    public List<Friend> getListOfPendingFriendsByRequester(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery(queryStringForMyPendingFriends(email));
        List<Friend> returnSQLList = query.list();
        return returnSQLList;
    }

    @Override
    @Transactional
    public List<Friend> getFriendRequests(String user) {
        return null;
    }

    public String queryStringForMyAcceptedFriendsWhereIAmOwner(String email){
        return "select BEFRIENDED_EMAIL from FriendHibernateModel "
                + "where REQUESTER_EMAIL = " + email + " and STATUS = ACCEPTED";
    }

    public String queryStringForMyAcceptedFriendsWhereIAmNotOwner(String email){
        return "select REQUESTER_EMAIL from FriendHibernateModel "
                + "where BEFRIENDED_EMAIL = " + email + " and STATUS = ACCEPTED";
    }

    public String queryStringForMyPendingFriends(String email){
        return "from FriendHibernateModel "
                + "where REQUESTER_EMAIL = " + email + " and status = PENDING";
    }
}
