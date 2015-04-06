package uk.co.socialcalendar.stepDefs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.socialcalendar.frameworksAndDrivers.persistence.TestDatabaseActions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DatabaseUrl {
    TestDatabaseActions databaseActions;

    public void setDatabaseActions(TestDatabaseActions databaseActions) {
        this.databaseActions = databaseActions;
    }



    @RequestMapping(value = "/clearDatabase", method = RequestMethod.GET)
    public ModelAndView clearDatabase(Model m,
                                  HttpServletRequest request, HttpServletResponse response) {
        databaseActions.clear();
        ModelAndView mav = new ModelAndView("fakeLogin");
        return mav;
    }

}