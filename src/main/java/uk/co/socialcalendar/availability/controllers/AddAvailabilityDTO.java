package uk.co.socialcalendar.availability.controllers;

import java.util.List;


public class AddAvailabilityDTO {

    String title, startDate, endDate;
    List<String> selectedFriends;

    public AddAvailabilityDTO(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getSelectedFriends() {
        return selectedFriends;
    }

    public void setSelectedFriends(List<String> selectedFriends) {
        this.selectedFriends = selectedFriends;
    }

}
