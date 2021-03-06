package uk.co.socialcalendar.authentication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.friend.useCases.FriendDAO;
import uk.co.socialcalendar.user.persistence.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class FakeLoginController {
    public static final String FACEBOOK_ID1 = "100008173740345";
    public static final String FACEBOOK_ID2 = "1000081732345";
    public static final String FACEBOOK_ID3 = "100040345";
    public static final String FACEBOOK_ID4 = "1008173740345";
    public static final String FACEBOOK_ID5 = "10000740345";
    public static final String EMAIL1 = "derekclark14@googlemail.com";
    public static final String EMAIL2 = "userEmail2";
    public static final String EMAIL3 = "userEmail3";
    public static final String EMAIL4 = "userEmail4";
    public static final String EMAIL5 = "userEmail15";
    public static final String NAME1 = "derek clark";
    public static final String NAME2 = "name2";
    public static final String NAME3 = "name3";
    public static final String NAME4 = "name4";
    public static final String NAME5 = "name5";

    UserDAO userDAO;
    FriendDAO friendDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setFriendDAO(FriendDAO friendDAO) {
        this.friendDAO = friendDAO;
    }

    @RequestMapping(value = "/fakelogin", method = RequestMethod.GET)
    public ModelAndView loginPage(Model m,
                                   HttpServletRequest request, HttpServletResponse response) {
        System.out.println("in fakelogin controller");

        ModelAndView mav = new ModelAndView("fakeLogin");
        return mav;
    }

    @RequestMapping(value = "/fakelogin", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute FakeUserCredentials fakeUserCredentials,
                              HttpServletRequest request, HttpServletResponse response){
        System.out.println("test user name=" + fakeUserCredentials.getUserId());

        if (validUserCredentials(fakeUserCredentials)){
            HttpSession session = request.getSession();
            session.setAttribute("USER_ID", fakeUserCredentials.getUserId());
            session.setAttribute("IS_AUTHENTICATED", true);
            session.setAttribute("OAUTH_TOKEN", "token");
            session.setAttribute("MY_FACEBOOK_ID", FACEBOOK_ID1);
            session.setAttribute("USER_NAME", "My name");

            String loggedInUser = (String) session.getAttribute("USER_ID");
            System.out.println("fake login id = " + loggedInUser);
            System.out.println("in fake login , get attribute="+session.getAttribute("USER_ID"));
        }else {
            ModelAndView mav = new ModelAndView("login");
            return mav;
        };

        ModelAndView mav = new ModelAndView("homePage");
//        mav.addObject("username", NAME1);
//        mav.addObject("USER_ID", fakeUserCredentials.getUserId());
        return mav;
    }

    private boolean validUserCredentials(FakeUserCredentials fakeUserCredentials) {
        if (fakeUserCredentials.getUserId().equals("me") && fakeUserCredentials.getPassword().equals("pass1")){
            System.out.println("valid user");
            return true;
        } else{
            System.out.println("invalid user");
        }
        return false;
    }


}
