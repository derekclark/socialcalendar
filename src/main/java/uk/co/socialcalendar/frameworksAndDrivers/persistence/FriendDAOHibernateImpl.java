package uk.co.socialcalendar.frameworksAndDrivers.persistence;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendValidator;
import uk.co.socialcalendar.useCases.friend.FriendDAO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import static uk.co.socialcalendar.entities.FriendStatus.*;

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
        session.save(convertToFriendHibernateModel(friend));
        System.out.println("just saved friend id=" + friend.getFriendId());
        return true;
    }

    public boolean canUpdate(Friend friend) {
        if (friendValidator.validBefriendedEmail(friend) &&
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
        FriendHibernateModel friendHibernateModel =
                (FriendHibernateModel) sessionFactory.getCurrentSession().get(FriendHibernateModel.class, friendId);
        return convertToFriend(friendHibernateModel);
    }

    @Override
    @Transactional
    public boolean updateStatus(int friendId, FriendStatus status) {
        FriendHibernateModel friendHibernateModel =
                (FriendHibernateModel) sessionFactory.getCurrentSession().get(FriendHibernateModel.class, friendId);

        friendHibernateModel.setStatus(status);

        sessionFactory.getCurrentSession().update(friendHibernateModel);
        return true;
    }

    @Override
    @Transactional
    public List<Friend> getMyAcceptedFriends(String email) {
        @SuppressWarnings("unchecked")
        Query query = queryMyAcceptedFriendsWhereIAmOwner(email);
        List<FriendHibernateModel> returnSQLList = query.list();
        query = queryMyAcceptedFriendsWhereIAmNotOwner(email);
        returnSQLList.addAll(query.list());

        List<Friend> friendList = convertModelListToFriendList(returnSQLList);
        return friendList;
    }

    private List<Friend> convertModelListToFriendList(List<FriendHibernateModel> returnSQLList) {
        List<Friend> friendList = new ArrayList<Friend>();
        for (FriendHibernateModel friendHibernateModel:returnSQLList){
            friendList.add(convertToFriend(friendHibernateModel));
        }
        return friendList;
    }

    @Override
    @Transactional
    public List<Friend> getMyFriendInvites(String email) {
        Query query = queryStringForMyFriendInvites(email);
        List<FriendHibernateModel> returnSQLList = query.list();
        return convertModelListToFriendList(returnSQLList);
    }

    @Override
    @Transactional
    public boolean friendshipExists(String email1, String email2) {
        System.out.println("checking friendship");
        if (read(email1, email2)){
            return true;
        }
        if (read(email2, email1)){
            return true;
        }

        return false;
    }

    public boolean read(String email1, String email2){
        Query query = sessionFactory.getCurrentSession().createQuery
                ("from FriendHibernateModel where REQUESTER_EMAIL = :email1 and BEFRIENDED_EMAIL = :email2");
        query.setParameter("email1", email1);
        query.setParameter("email2", email2);

        List<FriendHibernateModel> returnSQLList = query.list();
        if (returnSQLList.size()>0){
            return true;
        }
        return false;

    }

    public Query queryMyAcceptedFriendsWhereIAmOwner(String email){
        Query query = sessionFactory.getCurrentSession().createQuery
                ("from FriendHibernateModel where REQUESTER_EMAIL = :email and STATUS = :status");
        query.setParameter("email", email);
        query.setParameter("status", ACCEPTED.toString());
        return query;
    }

    public Query queryMyAcceptedFriendsWhereIAmNotOwner(String email){
        Query query = sessionFactory.getCurrentSession().createQuery
                ("from FriendHibernateModel where BEFRIENDED_EMAIL = :email and STATUS = :status");
        query.setParameter("email", email);
        query.setParameter("status", ACCEPTED.toString());
        return query;
    }

    public Query queryStringForMyFriendInvites(String email){
        Query query = sessionFactory.getCurrentSession().createQuery
                ("from FriendHibernateModel where BEFRIENDED_EMAIL = :email and STATUS = :status");
        query.setParameter("email", email);
        query.setParameter("status", PENDING.toString());
        return query;
    }

    public FriendHibernateModel convertToFriendHibernateModel(Friend friend){
        return new FriendHibernateModel(friend);
    }

    public Friend convertToFriend(FriendHibernateModel friendHibernateModel){
        Friend friend = new Friend();
        friend.setBeFriendedEmail(friendHibernateModel.getBeFriendedEmail());
        friend.setStatus(friendHibernateModel.getStatus());
        friend.setRequesterEmail(friendHibernateModel.getRequesterEmail());
        friend.setFriendId(friendHibernateModel.getFriendId());
        return friend;
    }
}
