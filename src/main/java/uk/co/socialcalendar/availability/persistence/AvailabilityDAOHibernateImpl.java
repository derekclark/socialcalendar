package uk.co.socialcalendar.availability.persistence;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.co.socialcalendar.availability.entities.Availability;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
        AvailabilityHibernateModel availabilityHibernateModel =
                (AvailabilityHibernateModel) sessionFactory.getCurrentSession().get(AvailabilityHibernateModel.class, id);

        if (availabilityHibernateModel == null){
            return null;
        }
        return convertToAvailability(availabilityHibernateModel);
    }

    @Override
    @Transactional
    public boolean update(Availability availability) {
        AvailabilityHibernateModel availabilityHibernateModel =
                (AvailabilityHibernateModel) sessionFactory.getCurrentSession().get(AvailabilityHibernateModel.class, availability.getId());

        if (availabilityHibernateModel != null) {
            availabilityHibernateModel.setStatus(availability.getStatus());
            availabilityHibernateModel.setOwnerName(availability.getOwnerName());
            availabilityHibernateModel.setEndDate(availability.getEndDate());
            availabilityHibernateModel.setStartDate(availability.getEndDate());
            availabilityHibernateModel.setOwnerEmail(availability.getOwnerEmail());
            availabilityHibernateModel.setTitle(availability.getTitle());
        } else{
            return false;
        }
        sessionFactory.getCurrentSession().update(availabilityHibernateModel);
        return true;
    }

    @Override
    public List<Availability> readAllOwnersOpenAvailabilities(String owner) {

        Query query = sessionFactory.getCurrentSession().createQuery
                ("from AvailabilityHibernateModel where OWNER_EMAIL = :owner");
        query.setParameter("owner", owner);

        List<AvailabilityHibernateModel> returnSQLList = query.list();

        List<Availability> availabilityList = new ArrayList<Availability>();

        for (AvailabilityHibernateModel avail:returnSQLList){
            availabilityList.add(convertToAvailability(avail));
        }

        return availabilityList;
    }

    public boolean canUpdate(Availability availability){
        if (availability.getOwnerEmail().isEmpty() ||
                availability.getOwnerName().isEmpty() ||
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
        availabilityHibernateModel.setId(availability.getId());
        availabilityHibernateModel.setOwnerEmail(availability.getOwnerEmail());
        availabilityHibernateModel.setTitle(availability.getTitle());
        availabilityHibernateModel.setEndDate(availability.getEndDate());
        availabilityHibernateModel.setStartDate(availability.getStartDate());
        availabilityHibernateModel.setOwnerName(availability.getOwnerName());
        availabilityHibernateModel.setStatus(availability.getStatus());
        return availabilityHibernateModel;
    }

    public Availability convertToAvailability(AvailabilityHibernateModel model){
        Availability availability = new Availability(model.getOwnerEmail(), model.getOwnerName(),
                model.getTitle(), model.getStartDate(), model.getEndDate(), model.getStatus());
        availability.setId(model.getId());
        return availability;
    }

}
