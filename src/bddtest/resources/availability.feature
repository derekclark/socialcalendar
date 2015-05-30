Feature: Availability Page

  Scenario: When the availability page is requested it is shown
    Given I am a registered user
    And I have logged in with valid credentials
    When I select the availability page
    Then the page is shown OK
    And the "availabilityCreate" view is rendered
    Then the section should be "availability"

  Scenario: The availability page shows my friends
    Given I am a registered user
    And I have logged in with valid credentials
    And Lisa is a user
    And Ron is a user
    And I have Ron as a "ACCEPTED" friend
    And I have Lisa as a "ACCEPTED" friend
    When I select the availability page
    Then Ron and Lisa are shown in my friend list
