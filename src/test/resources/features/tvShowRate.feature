Feature: Rate TV shows
  Background:
    Given user authenticates and generates a session

  Scenario: Rate a TV show
    Given the tv show doesn't have a rate from the test account
    When the show The Simpsons is rated with "9.5"
    Then the show has now a new rate

  Scenario: Delete a TV show rate
    Given the show The Simpsons has a rate from the test account
    When the tv show rate is deleted
    Then the show has no rate from the test account now

  Scenario: Rate an already rated TV show
    Given the show The Simpsons has a rate from the test account
    When the show The Simpsons is rated with "9.5"
    Then the tv show rate is updated

  Scenario: Delete a non-existing rate
    Given the tv show doesn't have a rate from the test account
    When the tv show rate is deleted
    Then an error is received
