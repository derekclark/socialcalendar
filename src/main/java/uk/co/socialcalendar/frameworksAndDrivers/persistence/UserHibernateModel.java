package uk.co.socialcalendar.frameworksAndDrivers.persistence;

import uk.co.socialcalendar.entities.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserHibernateModel {

    @Id
    @Column(name="EMAIL")
    private String email;

    @Column(name="NAME")
    private String name;

    @Column(name="FACEBOOK_ID")
    private String facebookId;

    public UserHibernateModel(){
    }

    public UserHibernateModel(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.facebookId = user.getFacebookId();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

}