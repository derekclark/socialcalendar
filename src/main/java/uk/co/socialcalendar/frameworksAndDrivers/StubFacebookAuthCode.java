package uk.co.socialcalendar.frameworksAndDrivers;

public class StubFacebookAuthCode implements FacebookAuthCode{
    private boolean wasCalled;

    public boolean wasCalled() {
        return wasCalled;
    }

    @Override
    public String getcode() {
        wasCalled = true;
        return "authUrl";
    }
}
