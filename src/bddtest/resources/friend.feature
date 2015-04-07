Feature: Friend Page

  Scenario: When the friend page is requested it is shown
    Given I am a registered user
    Given I have friends setup
    Given I have logged in with valid credentials
    When I select the friend page
    Then the friend page is shown
    Then friend view is returned
    Then the section should be friends

  Scenario: The friend page shows my friends
    Given I am a registered user
    Given I have logged in with valid credentials
    Given I have friends Ron and Lisa
    When I select the friend page
    Then Ron and Lisa are shown in my friend list

  Scenario: The friend page shows my friend requests
    Given I am a registered user
    Given I have logged in with valid credentials
    Given I have a friend request from Jeremy
    When I select the friend page
    Then Jeremy is shown as a friend request



