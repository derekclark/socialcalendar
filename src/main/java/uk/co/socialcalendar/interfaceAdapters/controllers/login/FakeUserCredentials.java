package uk.co.socialcalendar.interfaceAdapters.controllers.login;

public class FakeUserCredentials {

    String userId, password;

    public FakeUserCredentials() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
