package uk.co.socialcalendar.testPersistence;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import uk.co.socialcalendar.availability.persistence.AvailabilityHibernateModel;

import javax.transaction.Transactional;
import java.util.List;

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

        query = sessionFactory.getCurrentSession().createQuery
                ("DELETE FROM AvailabilityHibernateModel");

        query.executeUpdate();

    }

    @Transactional
    public List<AvailabilityHibernateModel> readAvailabilityHibernateModel(String ownerEmail) {

        Query query = sessionFactory.getCurrentSession().createQuery
                ("from AvailabilityHibernateModel where OWNER_EMAIL = :ownerEmail");
        query.setParameter("ownerEmail", ownerEmail);

        List<AvailabilityHibernateModel> returnSQLList = query.list();
        return returnSQLList;
    }
}
