package uk.co.socialcalendar.stepDefs;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.co.socialcalendar.interfaceAdapters.models.friend.FriendModel;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class HelperAsserts {
    public HelperAsserts() {
    }

    static void assertPropertyIsTrue(ResultActions results, String attribute, String propertyName, String expectedValue) throws Exception {
        results.andExpect(MockMvcResultMatchers.model().attribute(attribute,
                        Matchers.hasItem(
                                Matchers.allOf(
                                        hasProperty(propertyName, is(expectedValue)
                                        )
                                )
                        )
                )
        );
    }

    static void assertFriendModelEquals(ResultActions results, String property, Object expectedValue) throws Exception {
        results.andExpect(MockMvcResultMatchers.model().attribute("friendList",
                        hasItem(Matchers.<FriendModel>
                                        anyOf(
                                        hasProperty(property, is(expectedValue)
                                        )
                                )
                        )
                )
        );
    }

}