package uk.co.socialcalendar.interfaceAdapters.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.frameworksAndDrivers.InMemoryFriendDAO;
import uk.co.socialcalendar.frameworksAndDrivers.InMemoryUserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class FakeLoginController {
    InMemoryUserDAO userDAO;
    InMemoryFriendDAO friendDAO;

    public void setUserDAO(InMemoryUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setFriendDAO(InMemoryFriendDAO friendDAO) {
        this.friendDAO = friendDAO;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView loginPage(Model m,
                                   HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String loggedInUser = (String) session.getAttribute("USER_NAME");

        session.setAttribute("USER_ID", "userEmail1");
        session.setAttribute("IS_AUTHENTICATED", true);
        session.setAttribute("OAUTH_TOKEN", "token");
        session.setAttribute("USER_FACEBOOK_ID", "100008173740345");

        ModelAndView mav = new ModelAndView("login");
        mav.addObject("username","derek clark");
        userDAO.populate();
        friendDAO.populate();

        return mav;
    }

}
