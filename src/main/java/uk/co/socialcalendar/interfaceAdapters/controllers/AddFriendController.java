package uk.co.socialcalendar.interfaceAdapters.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.useCases.FriendFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddFriendController {
    FriendFacade friendFacade;

    public void setFriendFacade(FriendFacade friendFacade) {
        this.friendFacade = friendFacade;
    }

    @RequestMapping(value = "addFriend", method = RequestMethod.POST)
    public ModelAndView addFriend(Model m,
                                   HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String loggedInUser = (String) session.getAttribute("USER_NAME");
        ModelAndView mav = new ModelAndView("friend");
        mav.addObject("section","friends");
        mav.addObject("friend",new Friend());
        return mav;
    }

}
