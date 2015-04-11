Feature: Friend Page

  Scenario: When the friend page is requested it is shown
    Given I am a registered user
    And I have logged in with valid credentials
    When I select the friend page
    Then the page is shown OK
    And the "friend" view is rendered
    Then the section should be "friends"

  Scenario: The friend page shows my friends
    Given I am a registered user
    And I have logged in with valid credentials
    And Lisa is a user
    And Ron is a user
    And I have Ron as a "ACCEPTED" friend
    And I have Lisa as a "ACCEPTED" friend
    When I select the friend page
    Then Ron and Lisa are shown in my friend list

  Scenario: The friend page shows my friend requests
    Given I am a registered user
    And I have logged in with valid credentials
    And Jeremy is a user
    And I have a friend request from Jeremy
    When I select the friend page
    Then Jeremy is shown as a friend request

  Scenario: I can accept a friend request
    Given I am a registered user
    And I have logged in with valid credentials
    And I have a friend request from Jeremy
    And Lisa is a user
    And Ron is a user
    And Jeremy is a user
    And I have Ron as a "ACCEPTED" friend
    And I have Lisa as a "ACCEPTED" friend
    When I accept a friend request from Jeremy
    Then a message is shown that you have accepted jeremy as a friend
    When I select the friend page
    Then Ron and Lisa and Jeremy are shown in my friend list

  Scenario: I can decline a friend request
    Given I am a registered user
    And I have logged in with valid credentials
    And Lisa is a user
    And Ron is a user
    And Jeremy is a user
    And I have Ron as a "ACCEPTED" friend
    And I have Lisa as a "ACCEPTED" friend
    And Jeremy has made a friend request on me
    When I decline a friend request from Jeremy
    Then a message is shown that you have declined jeremy as a friend
    When I select the friend page
    Then Ron and Lisa are shown in my friend list
    And Jeremy is not shown as a pending friend request

  Scenario: I can make a friend request 
    Given I am a registered user 
    And I have logged in with valid credentials
    And Lisa is a user
    And Ron is a user
    And Jeremy is a user
    And I have Ron as a "ACCEPTED" friend
    And I have Lisa as a "ACCEPTED" friend
    When I make a friend request on Jeremy 
    Then a message is shown "You have sent a friend request to Jeremy" 
    When I select the friend page 
    Then Ron and Lisa are shown in my friend list 
    And Jeremy is shown as a pending friend request
