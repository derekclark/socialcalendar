Feature: Login

  Scenario: Login with valid credentials
    Given I have logged in with valid credentials
    Then I should be authenticated
    And the "homePage" view is rendered

  Scenario: Login with invalid credentials
    Given I have logged in with invalid credentials
    Then I should not be authenticated
    And the "login" view is rendered

  Scenario: Access friend page when I am not authenticated
    When I select the friend page without authenticating
    Then the "login" view is rendered