package uk.co.socialcalendar.availability.entities;

import org.joda.time.LocalDateTime;

public class Availability {

    private int id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String ownerEmail;
    private String ownerName;
    private String status;

    public Availability(String ownerEmail, String ownerName, String title, LocalDateTime startDate, LocalDateTime endDate, String status) {
        this.title = title;
        this.ownerEmail = ownerEmail;
        this.ownerName = ownerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Availability(){

    }

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        Availability availability = (Availability) obj;

        if (allNull(this) && allNull(availability)) return true;
        if (! this.ownerEmail.equals(availability.getOwnerEmail())) return false;
        if (! this.ownerName.equals(availability.getOwnerName())) return false;
        if (! this.title.equals(availability.getTitle())) return false;
        if (! this.startDate.equals(availability.getStartDate())) return false;
        if (! this.endDate.equals(availability.getEndDate())) return false;
        if (! this.status.equals(availability.getStatus())) return false;

        return id == availability.getId();
    }

    private boolean allNull(Availability availability){
        if (isNull (availability.getOwnerEmail())
        && (isNull (availability.getEndDate()))
        && (availability.getId() == 0)
        && (isNull(availability.getOwnerName()))
        && (isNull (availability.getStartDate()))
        && (isNull (availability.getStatus()))
        && (isNull(availability.getTitle())))
            return true;
        return false;
    }

    private boolean isNull(Object object){
        return (object == null);
    }

    public int hashcode(){
        int hash = 7;
        hash = 31 * hash + id + ownerEmail.hashCode() + ownerName.hashCode()
                + title.hashCode() + startDate.hashCode() + endDate.hashCode() + status.hashCode();
        return hash;
    }

    public String toString(){
        return this.getId() + " " + this.getOwnerName() + " " + this.getOwnerEmail() + " " + this.getTitle() + " "
                + this.getStartDate() + " " + this.getEndDate() + " " + this.getStatus();
    }

}
