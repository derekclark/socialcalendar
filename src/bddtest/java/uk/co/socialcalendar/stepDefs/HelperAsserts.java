package uk.co.socialcalendar.stepDefs;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class HelperAsserts {
    public HelperAsserts() {
    }

    static void assertPropertyIsTrue(ResultActions results, String attribute, String propertyName, String expectedValue) throws Exception {
        results.andExpect(MockMvcResultMatchers.model().attribute(attribute,
                        Matchers.hasItem(
                                Matchers.allOf(
                                        Matchers.hasProperty(propertyName, Matchers.is(expectedValue)
                                        )
                                )
                        )
                )
        );
    }
}