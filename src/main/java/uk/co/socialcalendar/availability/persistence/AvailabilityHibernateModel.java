package uk.co.socialcalendar.availability.persistence;

import org.joda.time.DateTime;
import uk.co.socialcalendar.availability.entities.Availability;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class AvailabilityHibernateModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private int id;

    @Column(name="TITLE")
    private String title;

    @Column(name="START_DATE")
    private DateTime startDate;

    @Column(name="END_DATE")
    private DateTime endDate;

    @Column(name="OWNER_EMAIL")
    private String ownerEmail;

    @Column(name="OWNER_NAME")
    private String ownerName;

    @Column(name="STATUS")
    private String status;

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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
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
}
