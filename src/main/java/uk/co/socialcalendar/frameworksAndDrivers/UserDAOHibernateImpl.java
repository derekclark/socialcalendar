package uk.co.socialcalendar.frameworksAndDrivers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.UserDAO;
import javax.transaction.Transactional;

public class UserDAOHibernateImpl implements UserDAO {
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public User read(String userEmail) {
        UserHibernateModel userHibernateModel =
                (UserHibernateModel) sessionFactory.getCurrentSession().get(UserHibernateModel.class, userEmail);
        if (userHibernateModel != null) {
            return convertToUser(userHibernateModel);
        }else {
            return null;
        }

    }

    public User convertToUser(UserHibernateModel userHibernateModel){
        User user = new User();
        user.setEmail(userHibernateModel.getEmail());
        user.setFacebookId(userHibernateModel.getFacebookId());
        user.setName(userHibernateModel.getName());
        return user;
    }

    public UserHibernateModel convertToUserHibernateModel(User user){
        UserHibernateModel userHibernateModel = new UserHibernateModel(user);
        userHibernateModel.setEmail(user.getEmail());
        userHibernateModel.setFacebookId(user.getFacebookId());
        userHibernateModel.setName(user.getName());
        return userHibernateModel;
    }

    @Override
    @Transactional
    public boolean save(User user) {
        Session session = sessionFactory.getCurrentSession();
        UserHibernateModel userHibernateModel = convertToUserHibernateModel(user);
        session.save(userHibernateModel);
        return true;
    }
}
