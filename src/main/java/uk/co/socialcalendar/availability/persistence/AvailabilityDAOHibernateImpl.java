package uk.co.socialcalendar.availability.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.co.socialcalendar.availability.entities.Availability;

import javax.transaction.Transactional;

public class AvailabilityDAOHibernateImpl implements AvailabilityDAO {
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public int save(Availability availability) {
        if (!canUpdate(availability)) {
            return -1;
        }
        Session session = sessionFactory.getCurrentSession();
        int id = (int) session.save(convertToHibernateModel(availability));
        return id;
    }

    @Override
    @Transactional
    public Availability read(int id) {
        return null;
    }

    @Override
    @Transactional
    public boolean update(Availability availability) {
        return false;
    }

    public boolean canUpdate(Availability availability){
        if (availability.getOwnerEmail().isEmpty() ||
                availability.getOwnerName().isEmpty() ||
                availability.getTitle().isEmpty() ||
                availability.getStatus().isEmpty() ||
                availability.getStartDate() == null ||
                availability.getEndDate() == null
                ) {
            return false;
        }
        return true;
    }

    public AvailabilityHibernateModel convertToHibernateModel(Availability availability){
        AvailabilityHibernateModel availabilityHibernateModel = new AvailabilityHibernateModel();
        availabilityHibernateModel.setOwnerEmail(availability.getOwnerEmail());
        availabilityHibernateModel.setTitle(availability.getTitle());
        availabilityHibernateModel.setEndDate(availability.getEndDate());
        availabilityHibernateModel.setStartDate(availability.getStartDate());
        availabilityHibernateModel.setOwnerName(availability.getOwnerName());
        availabilityHibernateModel.setStatus(availability.getStatus());
        return availabilityHibernateModel;
    }

}
