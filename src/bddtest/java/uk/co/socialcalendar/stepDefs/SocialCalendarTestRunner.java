package uk.co.socialcalendar.stepDefs;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

public class SocialCalendarTestRunner {
    @RunWith(Cucumber.class)
    @CucumberOptions(features = "src/bddtest/resources")
    public class InvoiceEntityServiceRunner {

    }
}

