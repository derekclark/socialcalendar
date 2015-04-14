package uk.co.socialcalendar.interfaceAdapters.utilities;


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

}
