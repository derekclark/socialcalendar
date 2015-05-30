Feature: Availability Page

  Scenario: When the availability page is requested it is shown
    Given I am a registered user
    And I have logged in with valid credentials
    When I select the availability page
    Then the page is shown OK
    And the "availabilityCreate" view is rendered
    Then the section should be "availability"
