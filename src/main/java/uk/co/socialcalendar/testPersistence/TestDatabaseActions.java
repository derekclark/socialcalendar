package uk.co.socialcalendar.testPersistence;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;

public class TestDatabaseActions {
    SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TestDatabaseActions(){

    }

    @Transactional
    public void clear() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("DELETE FROM FriendHibernateModel");

        query.executeUpdate();

        query = sessionFactory.getCurrentSession().createQuery
                ("DELETE FROM UserHibernateModel");

        query.executeUpdate();

    }

}
