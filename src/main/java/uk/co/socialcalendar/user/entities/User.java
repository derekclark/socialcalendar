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

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        User user = (User) obj;

        if (! this.email.equals(user.getEmail())) return false;
        if (! this.name.equals(user.getName())) return false;
        if (! this.facebookId.equals(user.getFacebookId())) return false;

        return true;
    }

    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + email.hashCode() + name.hashCode() + facebookId.hashCode();
        return hash;
    }

    public String toString(){
        return this.email + " " + this.name + " " + this.facebookId;
    }


}
