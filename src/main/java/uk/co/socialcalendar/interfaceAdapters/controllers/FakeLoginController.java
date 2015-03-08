package uk.co.socialcalendar.interfaceAdapters.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.entities.Friend;
import uk.co.socialcalendar.entities.FriendStatus;
import uk.co.socialcalendar.entities.User;
import uk.co.socialcalendar.useCases.FriendDAO;
import uk.co.socialcalendar.useCases.UserDAO;

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
    public static final String EMAIL1 = "userEmail1";
    public static final String EMAIL2 = "userEmail2";
    public static final String EMAIL3 = "userEmail3";
    public static final String EMAIL4 = "userEmail4";
    public static final String EMAIL5 = "userEmail5";
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView loginPage(Model m,
                                   HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String loggedInUser = (String) session.getAttribute("USER_NAME");

        session.setAttribute("USER_ID", EMAIL1);
        session.setAttribute("IS_AUTHENTICATED", true);
        session.setAttribute("OAUTH_TOKEN", "token");
        session.setAttribute("USER_FACEBOOK_ID", FACEBOOK_ID1);

        ModelAndView mav = new ModelAndView("login");
        mav.addObject("username", NAME1);
        populateUsers();
        populateFriends();

        return mav;
    }

    public void populateUsers(){
        writeUser(new User(EMAIL1, NAME1, FACEBOOK_ID1));
        writeUser(new User(EMAIL2, NAME2, FACEBOOK_ID2));
        writeUser(new User(EMAIL3, NAME3, FACEBOOK_ID3));
        writeUser(new User(EMAIL4, NAME4, FACEBOOK_ID4));
        writeUser(new User(EMAIL5, NAME5, FACEBOOK_ID5));
        System.out.println("populated 5 users");
    }

    public void writeUser(User user){
        if (userDAO.read(user.getEmail()) == null){
            System.out.println("creating user=" + user.getEmail());
            userDAO.save(user);
        }
    }
    public void populateFriends(){
        friendDAO.save(createFriend(new Friend(EMAIL1, EMAIL3, FriendStatus.ACCEPTED), 1));
        friendDAO.save(createFriend(new Friend(EMAIL4, EMAIL2, FriendStatus.ACCEPTED), 2));
        friendDAO.save(createFriend(new Friend(EMAIL4, EMAIL1, FriendStatus.ACCEPTED), 3));
        friendDAO.save(createFriend(new Friend(EMAIL5, EMAIL1, FriendStatus.PENDING), 4));
        System.out.println("populated 4 friends");
    }

    private Friend createFriend(Friend friend, int friendId){
        friend.setFriendId(friendId);
        return friend;
    }

}
