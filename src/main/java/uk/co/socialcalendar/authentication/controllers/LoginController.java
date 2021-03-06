package uk.co.socialcalendar.authentication.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(Model model, HttpServletRequest mockHttpServletRequest, HttpServletResponse mockHttpServletResponse) {
        LOG.info("in login controller");
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }
}
