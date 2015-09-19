package uk.co.socialcalendar.authentication;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionAttributes {

    public String getLoggedInUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("USER_ID");
    }

    public boolean isAuthenticated(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (boolean) session.getAttribute("IS_AUTHENTICATED");
    }

    public String getFacebookId(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (String) session.getAttribute("FACEBOOK_ID");
    }

    public String getUserName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("USER_NAME");

    }
}
