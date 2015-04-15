package uk.co.socialcalendar.user.entities;

public class User {
    private String email;
    private String name;
    private String facebookId;

    public User(){
    }

    public User(String email, String name, String facebookId){
        this.email = email;
        this.name = name;
        this.facebookId = facebookId;
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
