package uk.co.socialcalendar.availability.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.availability.useCases.AvailabilityFacade;

public class AvailabilityDAOHibernateImpl implements AvailabilityFacade {
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int create(Availability availability) {
        Session session = sessionFactory.getCurrentSession();
        int id = (int) session.save(new AvailabilityHibernateModel());
        return id;
    }

    @Override
    public Availability get(int id) {
        return null;
    }

    @Override
    public boolean update(Availability availability) {
        return false;
    }
}
