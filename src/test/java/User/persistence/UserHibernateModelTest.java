package user.persistence;

import org.junit.Test;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserHibernateModel;

import static org.junit.Assert.assertEquals;

public class UserHibernateModelTest {
    @Test
    public void convertsUserModelToUser(){
        User user = new User("USER1", "NAME1", "FACEBOOK1");
        UserHibernateModel model = new UserHibernateModel(user);

        assertEquals(user, model.convertUserModelToUser());

    }
}
