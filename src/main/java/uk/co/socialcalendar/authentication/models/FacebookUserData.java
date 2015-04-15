package uk.co.socialcalendar.authentication.models;

public class FacebookUserData {
    private String id;
    private String first_name;
    private String gender;
    private String last_name;
    private String link;
    private String locale;
    private String name;
    private int timezone;
    private String updated_time;
    private boolean verified;
    private String email;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return first_name;
    }
    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getLastName() {
        return last_name;
    }
    public void setLastName(String lastName) {
        this.last_name = lastName;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTimezone() {
        return timezone;
    }
    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }
    public String getUpdatedTime() {
        return updated_time;
    }
    public void setUpdatedTime(String updatedTime) {
        this.updated_time = updatedTime;
    }
    public boolean isVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

