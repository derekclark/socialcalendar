//package UtilityTests;
//
//import org.scribe.model.Token;
//import uk.co.socialcalendar.frameworksAndDrivers.FacebookResponse;
//import uk.co.socialcalendar.interfaceAdapters.models.FacebookUserData;
//
//import javax.servlet.http.HttpServletResponse;
//
//public class StubFacebookResponse implements FacebookResponse{
//
//    private boolean wasCalled;
//    FacebookUserData fb;
//
//    public StubFacebookResponse(){
//        fb = new FacebookUserData();
//    }
//    public boolean wasCalled() {
//        return wasCalled;
//    }
//
//    @Override
//    public FacebookUserData getResponse(Token accessToken, String fbResource, HttpServletResponse response) {
//        fb.setEmail("userEmail");
//        fb.setName("userName");
//        fb.setId("facebookId");
//        wasCalled = true;
//        return fb;
//    }
//
//    public String getEmail(){
//        return fb.getEmail();
//    }
//
//    public String getName(){
//        return fb.getName();
//    }
//
//    public String getFacebookId(){
//        return fb.getId();
//    }
//}
