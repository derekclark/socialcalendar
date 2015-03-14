Feature: Get Info on webservice

Scenario: When the friend page is requested it is shown
Given The friend webpage is available
When a request is made
Then page is shown
Then friend view is returned

Given The I have friends Ron and Lisa
When a request is made
Then ron and lisa are shown in my friend list


