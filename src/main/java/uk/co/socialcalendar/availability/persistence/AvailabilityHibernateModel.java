package uk.co.socialcalendar.availability.persistence;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import uk.co.socialcalendar.availability.entities.Availability;
import uk.co.socialcalendar.user.entities.User;
import uk.co.socialcalendar.user.persistence.UserHibernateModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
public class AvailabilityHibernateModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private int id;

    @Column(name="TITLE")
    private String title;

    @Column(name="START_DATE")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime startDate;

    @Column(name="END_DATE")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime endDate;

    @Column(name="OWNER_EMAIL")
    private String ownerEmail;

    @Column(name="OWNER_NAME")
    private String ownerName;

    @Column(name="STATUS")
    private String status;

    @ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name="AVAILABILITY_SHARED",
            joinColumns={@JoinColumn(name="ID")},
            inverseJoinColumns={@JoinColumn(name="EMAIL")})
    private Set<UserHibernateModel> sharedList = new HashSet<UserHibernateModel>();


    public AvailabilityHibernateModel(){

    }

    public AvailabilityHibernateModel(Availability availability){
        this.id = availability.getId();
        this.ownerEmail = availability.getOwnerEmail();
        this.ownerName = availability.getOwnerName();
        this.startDate = availability.getStartDate();
        this.endDate = availability.getEndDate();
        this.status = availability.getStatus();
        this.title = availability.getTitle();
        Set<UserHibernateModel> userModel = new HashSet<UserHibernateModel>();
        for (User user:availability.getSharedList()){
            userModel.add(new UserHibernateModel(user));
        }
        this.sharedList = userModel;
    }

    public AvailabilityHibernateModel(String ownerEmail, String ownerName, String title,
                                      LocalDateTime startDate, LocalDateTime endDate,
                                      String status, Set<User> sharedList) {
        this.ownerEmail = ownerEmail;
        this.ownerName = ownerName;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        Set<UserHibernateModel> userModel = new HashSet<UserHibernateModel>();
        for (User user:sharedList){
            userModel.add(new UserHibernateModel(user));
        }
        this.sharedList = userModel;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<UserHibernateModel> getSharedList() {
        return sharedList;
    }

    public void setSharedList(Set<UserHibernateModel> sharedList) {
        this.sharedList = sharedList;
    }


    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        AvailabilityHibernateModel availabilityHibernateModel = (AvailabilityHibernateModel) obj;

        if (! this.title.equals(availabilityHibernateModel.getTitle())) return false;
        if (! this.ownerEmail.equals(availabilityHibernateModel.getOwnerEmail())) return false;
        if (! this.ownerName.equals(availabilityHibernateModel.getOwnerName())) return false;
        if (! this.startDate.equals(availabilityHibernateModel.getStartDate())) return false;
        if (! this.endDate.equals(availabilityHibernateModel.getEndDate())) return false;
        if (! this.status.equals(availabilityHibernateModel.getStatus())) return false;

        for (UserHibernateModel model:this.sharedList){
            if (!findElementInSet(availabilityHibernateModel.getSharedList(), model)) return false;
        }
        if (this.sharedList.size() != availabilityHibernateModel.getSharedList().size()) return false;

        return id == availabilityHibernateModel.getId();
    }

    public boolean findElementInSet(Set<UserHibernateModel> set, UserHibernateModel userHibernateModel){
        Iterator<UserHibernateModel> iterator = set.iterator();
        while(iterator.hasNext()) {
            UserHibernateModel setElement = iterator.next();
            if(setElement.equals(userHibernateModel)) {
                return true;
            }
        }
        return false;
    }

    public int hashcode(){
        int hash = 7;
        hash = 31 * hash + id + title.hashCode() + ownerEmail.hashCode() + ownerName.hashCode() +
                startDate.hashCode() + endDate.hashCode() + status.hashCode() + sharedList.hashCode();
        return hash;
    }

    public String toString(){
        return title + " " + ownerEmail + " " + ownerName + " " + startDate + " "
                + endDate + " " + status + " " + sharedList;
    }

}
