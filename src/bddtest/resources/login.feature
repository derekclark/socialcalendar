Feature: Login

  Scenario: Login with valid credentials
    Given I have logged in with valid credentials
    Then I should be authenticated

  Scenario: Login with invalid credentials

    Given I have logged in with invalid credentials
    Then I should not be authenticated

  Scenario: access friend page when I am not authenticated
    When I select the friend page without going authenticating
    Then I should be redirected to the login page