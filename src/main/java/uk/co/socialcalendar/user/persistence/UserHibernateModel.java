package uk.co.socialcalendar.user.persistence;

import uk.co.socialcalendar.user.entities.User;

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

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        UserHibernateModel user = (UserHibernateModel) obj;

        if (! this.email.equals(user.getEmail())) return false;
        if (! this.name.equals(user.getName())) return false;
        if (! this.facebookId.equals(user.getFacebookId())) return false;

        return true;
    }

    public int hashcode(){
        int hash = 7;
        hash = 31 * hash + email.hashCode() + name.hashCode() + facebookId.hashCode();
        return hash;
    }

    public String toString(){
        return this.email + " " + this.name + " " + this.facebookId;
    }

}
