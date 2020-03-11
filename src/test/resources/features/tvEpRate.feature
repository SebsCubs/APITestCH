Feature: Rate TV episodes
  Background:
    Given user authenticates and generates a session

  Scenario: Rate a TV episode
    Given the first tv episode of The Simpsons doesn't have a rate from the test account
    When the episode of The Simpsons is rated with "9.5"
    Then the episode has now a new rate

  Scenario: Delete a TV episode rate
    Given the first tv episode of The Simpsons doesn't have a rate from the test account
    When the tv episode rate is deleted
    Then the episode has no rate from the test account now

  Scenario: Rate an already rated TV episode
    Given the first episode of The Simpsons has a rate from the test account
    When the episode of The Simpsons is rated with "9.5"
    Then the tv episode rate is updated

  Scenario: Delete a non-existing rate
    Given the first tv episode of The Simpsons doesn't have a rate from the test account
    When the tv episode rate is deleted
    Then an error is received stating the rate wasn't found